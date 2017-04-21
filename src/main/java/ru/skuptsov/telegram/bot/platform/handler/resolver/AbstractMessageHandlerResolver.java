package ru.skuptsov.telegram.bot.platform.handler.resolver;

import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public abstract class AbstractMessageHandlerResolver implements MessageHandlerResolver {

    private MessageHandlerResolver nextAbstractMessageHandlerResolver;

    @Override
    public MessageHandler resolve(@NotNull UpdateEvent updateEvent) {
        MessageHandler messageHandler = resolveProcessor(updateEvent);
        if (messageHandler == MessageHandler.EMPTY && nextAbstractMessageHandlerResolver != null) {
            return nextAbstractMessageHandlerResolver.resolve(updateEvent);
        }

        return messageHandler;
    }

    @Override
    public MessageHandlerResolver setNext(@NotNull MessageHandlerResolver messageHandlerResolver) {
        this.nextAbstractMessageHandlerResolver = messageHandlerResolver;

        return messageHandlerResolver;
    }

    protected abstract MessageHandler resolveProcessor(UpdateEvent updateEvent);
}
