package ru.skuptsov.telegram.bot.platform.handler.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.DefaultMessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
@Component
public class DefaultEventMessageHandlerResolver extends AbstractMessageHandlerResolver {
    private final Logger log = LoggerFactory.getLogger(DefaultEventMessageHandlerResolver.class);

    private final DefaultMessageHandler defaultEventProcessor;

    @Autowired
    public DefaultEventMessageHandlerResolver(@NotNull DefaultMessageHandler defaultEventProcessor) {
        this.defaultEventProcessor = defaultEventProcessor;
    }

    @Override
    protected MessageHandler resolveProcessor(UpdateEvent updateEvent) {
        return defaultEventProcessor;
    }
}
