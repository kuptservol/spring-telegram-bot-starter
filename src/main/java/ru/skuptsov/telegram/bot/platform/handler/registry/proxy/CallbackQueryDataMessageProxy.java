package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.CallbackQueryDataMessageHandler;
import ru.skuptsov.telegram.bot.platform.service.MetricsService;

import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
public class CallbackQueryDataMessageProxy extends BaseMessageHandlerProxy implements CallbackQueryDataMessageHandler {

    private final Set<String> callbackQueryData;

    public CallbackQueryDataMessageProxy(MetricsService metricsService, HandlerMethod handlerMethod, Set<String> callbackQueryData) {
        super(handlerMethod, metricsService);
        this.callbackQueryData = callbackQueryData;
    }

    @Override
    public Set<String> getCallbackQueryData() {
        return callbackQueryData;
    }
}
