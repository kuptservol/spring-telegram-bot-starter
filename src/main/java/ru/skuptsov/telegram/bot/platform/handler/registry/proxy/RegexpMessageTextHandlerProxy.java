package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.RegexpMessageTextHandler;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class RegexpMessageTextHandlerProxy extends BaseMessageHandlerProxy implements RegexpMessageTextHandler {

    private final String messageRegexp;

    public RegexpMessageTextHandlerProxy(HandlerMethod handlerMethod, String messageRegexp) {
        super(handlerMethod);
        this.messageRegexp = messageRegexp;
    }

    @Override
    public String getMessageRegexp() {
        return messageRegexp;
    }
}
