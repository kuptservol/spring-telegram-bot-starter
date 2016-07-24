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
public final class NonBlockingUpdatesSaver implements UpdatesSaver {
    private final Logger log = LoggerFactory.getLogger(NonBlockingUpdatesSaver.class);

    private final BlockingQueue<UpdateEvent> updatesQueue;

    NonBlockingUpdatesSaver(BlockingQueue<UpdateEvent> updatesQueue) {
        this.updatesQueue = updatesQueue;
    }

    @Override
    public void save(@NotNull UpdateEvents updateEvents) {
        updateEvents.getUpdateEventList().stream()
                .filter(Objects::nonNull)
                .forEach(updateEvent -> {
                    boolean offered = updatesQueue.offer(updateEvent);
                    if (!offered) {
                        log.warn("UpdateEvent [{}] hasn't been saved, Queue is full", updateEvent);
                    } else {
                        log.trace("Saved update event [{}]", updateEvent);
                    }
                });
    }
}
