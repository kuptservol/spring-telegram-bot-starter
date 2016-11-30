package ru.skuptsov.telegram.bot.platform.worker.saver.impl;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BlockingQueueUpdatesWorkerRepository implements UpdatesWorkerRepository {
    private final Logger log = LoggerFactory.getLogger(BlockingQueueUpdatesWorkerRepository.class);

    @Autowired
    private UpdatesWorkerRepositoryConfiguration updatesWorkerRepositoryConfiguration;

    private BlockingQueue<UpdateEvent> updatesQueue;

    private UpdatesSaver updatesSaver;

    @Override
    @Timed(name = "bot.updates.worker.repository.save", absolute = true)
    public void save(@NotNull UpdateEvents updateEvents) {
        updatesSaver.save(updateEvents);
    }

    @Override
    @Timed(name = "bot.updates.worker.repository.get", absolute = true)
    public UpdateEvent get() {
        UpdateEvent updateEvent = updatesSaver.next().orElse(EMPTY);

        log.trace("Returned event {}", updateEvent);
        return updateEvent;
    }

    @PostConstruct
    public void init() {
        updatesQueue = new ArrayBlockingQueue<>(updatesWorkerRepositoryConfiguration.getQueueSize());
        updatesSaver = new BlockingUpdatesSaver(updatesQueue);
    }
}
