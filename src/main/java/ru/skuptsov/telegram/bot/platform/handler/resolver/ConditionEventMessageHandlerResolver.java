package ru.skuptsov.telegram.bot.platform.handler.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.ConditionMessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
//todo: add sorting version?
@Component
public class ConditionEventMessageHandlerResolver extends AbstractMessageHandlerResolver {

    private final List<ConditionMessageHandler> conditionEventProcessors = new ArrayList<>();

    @Autowired(required = false)
    public ConditionEventMessageHandlerResolver(@NotNull List<ConditionMessageHandler> conditionMessageHandlers) {
        checkNotNull(conditionMessageHandlers, "Handlers cannot be null");
        conditionMessageHandlers.forEach(this::add);
    }

    public ConditionEventMessageHandlerResolver() {
    }

    public void add(@NotNull ConditionMessageHandler conditionMessageHandler) {
        checkNotNull(conditionMessageHandler, "Handler cannot be null");
        conditionEventProcessors.add(conditionMessageHandler);
    }

    @Override
    protected MessageHandler resolveProcessor(UpdateEvent updateEvent) {
        return conditionEventProcessors.stream()
                .filter(conditionEventProcessor -> conditionEventProcessor.isSuitableForProcessingEvent(updateEvent))
                .findFirst()
                .map(conditionEventProcessor -> (MessageHandler) conditionEventProcessor)
                .orElse(MessageHandler.EMPTY);
    }
}
