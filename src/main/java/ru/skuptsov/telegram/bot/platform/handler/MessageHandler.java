package ru.skuptsov.telegram.bot.platform.handler;

import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface MessageHandler {
    MessageHandler EMPTY = (updateEvent) -> MessageResponse.EMPTY;

    MessageResponse handle(UpdateEvent updateEvent);
}
