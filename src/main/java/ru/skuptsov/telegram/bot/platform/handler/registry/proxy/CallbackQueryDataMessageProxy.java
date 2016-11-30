package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.CallbackQueryDataMessageHandler;

import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
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
