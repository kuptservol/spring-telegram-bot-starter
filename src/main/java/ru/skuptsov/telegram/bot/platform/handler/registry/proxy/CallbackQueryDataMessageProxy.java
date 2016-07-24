package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import ru.skuptsov.telegram.bot.platform.handler.CallbackQueryDataMessageHandler;

import java.util.Set;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
public class CallbackQueryDataMessageProxy extends BaseMessageHandlerProxy implements CallbackQueryDataMessageHandler {

    private final Set<String> callbackQueryData;

    public CallbackQueryDataMessageProxy(HandlerMethod handlerMethod, Set<String> callbackQueryData) {
        super(handlerMethod);
        this.callbackQueryData = callbackQueryData;
    }

    @Override
    public Set<String> getCallbackQueryData() {
        return callbackQueryData;
    }
}
