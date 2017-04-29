package ru.skuptsov.telegram.bot.platform.handler.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public abstract class AbstractMessageContentMessageHandlerResolver extends AbstractMessageHandlerResolver {
    protected final Map<Object, MessageHandler> messageContentProcessorMap = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(AbstractMessageContentMessageHandlerResolver.class);

    public void add(@NotNull Object object, @NotNull MessageHandler messageHandler) {
        if (isEmpty(object)) {
            log.error("Found empty command in {}", messageHandler);
            throw new IllegalArgumentException("Found empty command");
        }

        if (messageContentProcessorMap.containsKey(object)) {
            log.error("Duplicate command [{}] configuration found in processor {}", object, messageHandler);
            throw new IllegalArgumentException("Duplicate command configuration found in processor");
        }
        messageContentProcessorMap.put(object, messageHandler);
    }

    @Override
    //todo: divide data and method resolver into to different beans
    protected MessageHandler resolveProcessor(UpdateEvent updateEvent) {
        return ofNullable(messageContentProcessorMap.get(getMessageContent(updateEvent)))
                .orElse(MessageHandler.EMPTY);
    }

    protected abstract Object getMessageContent(UpdateEvent updateEvent);
}
