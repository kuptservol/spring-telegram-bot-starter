package ru.skuptsov.telegram.bot.platform.worker.saver.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvents;
import ru.skuptsov.telegram.bot.platform.worker.saver.UpdatesSaver;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * @author Sergey Kuptsov
 * @since 30/05/2016
 */
public class BlockingUpdatesSaver implements UpdatesSaver {
    private final Logger log = LoggerFactory.getLogger(BlockingUpdatesSaver.class);

    private final BlockingQueue<UpdateEvent> updatesQueue;

    BlockingUpdatesSaver(BlockingQueue<UpdateEvent> updatesQueue) {
        this.updatesQueue = updatesQueue;
    }

    @Override
    public void save(@NotNull UpdateEvents updateEvents) {
        updateEvents.getUpdateEventList().stream()
                .filter(Objects::nonNull)
                .forEach(updateEvent -> {
                    try {
                        updatesQueue.put(updateEvent);
                        log.trace("Saved update event [{}]", updateEvent);
                    } catch (InterruptedException e) {
                        log.error("Couldn't save update events", e);
                    }
                });
    }
}

