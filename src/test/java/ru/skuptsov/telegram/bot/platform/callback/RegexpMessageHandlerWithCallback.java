package ru.skuptsov.telegram.bot.platform.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Message;
import ru.skuptsov.telegram.bot.platform.client.command.Reply;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.util.function.Consumer;

@MessageHandler
public class RegexpMessageHandlerWithCallback {

    public static final String MESSAGE_TEXT_PATTERN_REGEXP = ".*(hi|hello).*";
    public static final String MESSAGE_ANSWER = "Hi there";
    @Autowired
    private CallbackMethod callbackMethod;

    @MessageMapping(regexp = MESSAGE_TEXT_PATTERN_REGEXP)
    public Reply handle(UpdateEvent updateEvent) {
        return Reply.withMessage(MESSAGE_ANSWER, updateEvent)
                .setCallback((Consumer<Message>) message -> callbackMethod.run(message));
    }
}