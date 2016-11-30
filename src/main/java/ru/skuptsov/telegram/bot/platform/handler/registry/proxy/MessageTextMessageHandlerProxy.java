package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.MessageTextMessageHandler;

import java.util.Set;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class MessageTextMessageHandlerProxy extends BaseMessageHandlerProxy implements MessageTextMessageHandler {

    private final Set<String> messageTexts;

    public MessageTextMessageHandlerProxy(HandlerMethod handlerMethod, Set<String> messageTexts) {
        super(handlerMethod);
        this.messageTexts = messageTexts;
    }

    @Override
    public Set<String> getMessageText() {
        return messageTexts;
    }
}
