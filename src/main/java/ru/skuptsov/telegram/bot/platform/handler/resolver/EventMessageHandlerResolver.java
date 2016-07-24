package ru.skuptsov.telegram.bot.platform.handler.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.annotation.PostConstruct;

/**
 * @author Sergey Kuptsov
 * @since 31/05/2016
 */
@Component
public class EventMessageHandlerResolver implements MessageHandlerResolver {
    private final Logger log = LoggerFactory.getLogger(EventMessageHandlerResolver.class);

    @Autowired
    private MessageTextMessageHandlerResolver messageTextMessageHandlerResolver;

    @Autowired
    private CallbackQueryDataMessageHandlerResolver callbackQueryDataMessageHandlerResolver;

    @Autowired
    private ConditionEventMessageHandlerResolver conditionEventMessageHandlerResolver;

    @Autowired
    private RegexpMessageTextHandlerResolver regexpMessageTextHandlerResolver;

    @Autowired
    private DefaultEventMessageHandlerResolver defaultEventProcessor;

    @Override
    public MessageHandler resolve(UpdateEvent updateEvent) {
        log.debug("Resolving processor for event {}", updateEvent);

        return messageTextMessageHandlerResolver.resolve(updateEvent);
    }

    @Override
    public MessageHandlerResolver setNext(MessageHandlerResolver messageHandlerResolver) {
        return null;
    }

    @PostConstruct
    public void init() {
        //todo: add custom sorting?
        messageTextMessageHandlerResolver
                .setNext(callbackQueryDataMessageHandlerResolver)
                .setNext(regexpMessageTextHandlerResolver)
                .setNext(conditionEventMessageHandlerResolver)
                .setNext(defaultEventProcessor);
    }
}
