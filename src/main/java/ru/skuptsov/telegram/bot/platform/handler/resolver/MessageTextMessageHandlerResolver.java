package ru.skuptsov.telegram.bot.platform.handler.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.MessageTextMessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.util.List;
import java.util.Set;

import static ru.skuptsov.telegram.bot.platform.handler.UpdateFunctions.MESSAGE_TEXT_FROM_UPDATE_EVENT;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
@Component
public class MessageTextMessageHandlerResolver extends AbstractMessageContentMessageHandlerResolver {

    @Autowired(required = false)
    public MessageTextMessageHandlerResolver(List<MessageTextMessageHandler> messageTextEventProcessors) {
        messageTextEventProcessors.stream().forEach(this::add);
    }

    public void add(MessageTextMessageHandler messageTextEventProcessors) {
        Set<String> commandTextList = messageTextEventProcessors.getMessageText();
        for (String data : commandTextList) {
            add(data, messageTextEventProcessors);
        }
    }

    public MessageTextMessageHandlerResolver() {
    }

    @Override
    protected Object getMessageContent(UpdateEvent updateEvent) {
        return MESSAGE_TEXT_FROM_UPDATE_EVENT.apply(updateEvent);
    }
}
