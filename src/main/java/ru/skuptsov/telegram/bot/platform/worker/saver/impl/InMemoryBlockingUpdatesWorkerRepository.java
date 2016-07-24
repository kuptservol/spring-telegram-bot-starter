package ru.skuptsov.telegram.bot.platform.worker.saver.impl;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.skuptsov.telegram.bot.platform.config.UpdatesWorkerRepositoryConfiguration;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvents;
import ru.skuptsov.telegram.bot.platform.worker.saver.UpdatesSaver;
import ru.skuptsov.telegram.bot.platform.worker.saver.UpdatesWorkerRepository;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static ru.skuptsov.telegram.bot.platform.model.UpdateEvent.EMPTY;

/**
 * @author Sergey Kuptsov
 * @since 30/05/2016
 */
@Repository
public class InMemoryBlockingUpdatesWorkerRepository implements UpdatesWorkerRepository {
    private final Logger log = LoggerFactory.getLogger(InMemoryBlockingUpdatesWorkerRepository.class);

    @Autowired
    private UpdatesWorkerRepositoryConfiguration updatesWorkerRepositoryConfiguration;

    private BlockingQueue<UpdateEvent> updatesQueue;

    private UpdatesSaver updatesSaver;

    @Override
    @Timed(name = "updates.worker.repository.save")
    public void save(@NotNull UpdateEvents updateEvents) {
        updatesSaver.save(updateEvents);
    }

    @Override
    @Timed(name = "updates.worker.repository.get")
    public UpdateEvent get() {
        UpdateEvent updateEvent;
        try {
            updateEvent = updatesQueue.take();
        } catch (InterruptedException e) {
            log.debug("Can't take message from queue", e);
            updateEvent = EMPTY;
        }

        log.trace("Returned event {}", updateEvent);
        return updateEvent;
    }

    @PostConstruct
    public void init() {
        updatesQueue = new ArrayBlockingQueue<>(updatesWorkerRepositoryConfiguration.getQueueSize());
        if (updatesWorkerRepositoryConfiguration.getBlock()) {
            updatesSaver = new BlockingUpdatesSaver(updatesQueue);
        } else {
            updatesSaver = new NonBlockingUpdatesSaver(updatesQueue);
        }
    }
}
