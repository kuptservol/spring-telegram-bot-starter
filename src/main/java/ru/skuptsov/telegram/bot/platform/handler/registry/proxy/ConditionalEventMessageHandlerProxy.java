package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.ConditionMessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageFilter;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class ConditionalEventMessageHandlerProxy extends BaseMessageHandlerProxy implements ConditionMessageHandler {

    private final MessageFilter messageFilter;

    public ConditionalEventMessageHandlerProxy(HandlerMethod handlerMethod, @NotNull MessageFilter messageFilter) {
        super(handlerMethod);
        Preconditions.checkNotNull(messageFilter, "Message filter cannot be null");
        this.messageFilter = messageFilter;
    }

    @Override
    public boolean isSuitableForProcessingEvent(@NotNull UpdateEvent updateEvent) {
        return messageFilter.test(updateEvent);
    }
}
