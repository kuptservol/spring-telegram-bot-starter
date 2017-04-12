package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import ru.skuptsov.telegram.bot.platform.handler.MessageTextMessageHandler;
import ru.skuptsov.telegram.bot.platform.service.MetricsService;

import java.util.Set;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
public class MessageTextMessageHandlerProxy extends BaseMessageHandlerProxy implements MessageTextMessageHandler {

    private final Set<String> messageTexts;

    public MessageTextMessageHandlerProxy(HandlerMethod handlerMethod, Set<String> messageTexts, MetricsService metricsService) {
        super(handlerMethod, metricsService);
        this.messageTexts = messageTexts;
    }

    @Override
    public Set<String> getMessageText() {
        return messageTexts;
    }
}
