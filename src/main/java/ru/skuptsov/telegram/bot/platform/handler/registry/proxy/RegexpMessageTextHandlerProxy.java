package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import ru.skuptsov.telegram.bot.platform.handler.RegexpMessageTextHandler;
import ru.skuptsov.telegram.bot.platform.service.MetricsService;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
public class RegexpMessageTextHandlerProxy extends BaseMessageHandlerProxy implements RegexpMessageTextHandler {

    private final String messageRegexp;

    public RegexpMessageTextHandlerProxy(HandlerMethod handlerMethod, String messageRegexp, MetricsService metricsService) {
        super(handlerMethod, metricsService);
        this.messageRegexp = messageRegexp;
    }

    @Override
    public String getMessageRegexp() {
        return messageRegexp;
    }
}
