package ru.skuptsov.telegram.bot.platform.handler;

import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public interface ConditionMessageHandler extends MessageHandler {

    boolean isSuitableForProcessingEvent(@NotNull UpdateEvent updateEvent);
}
