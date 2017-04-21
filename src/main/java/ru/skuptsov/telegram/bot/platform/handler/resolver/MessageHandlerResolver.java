package ru.skuptsov.telegram.bot.platform.handler.resolver;

import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public interface MessageHandlerResolver {
    MessageHandler resolve(@NotNull UpdateEvent updateEvent);

    MessageHandlerResolver setNext(MessageHandlerResolver messageHandlerResolver);
}
