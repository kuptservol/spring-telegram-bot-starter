package ru.skuptsov.telegram.bot.platform.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Message;
import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.util.function.Consumer;

import static ru.skuptsov.telegram.bot.platform.client.command.MessageResponse.sendMessage;

@MessageHandler
public class RegexpMessageHandlerWithCallback {

    @Autowired
    private CallbackMethod callbackMethod;

    public static final String MESSAGE_TEXT_PATTERN_REGEXP = ".*(hi|hello).*";
    public static final String MESSAGE_ANSWER = "Hi there";

    @MessageMapping(regexp = MESSAGE_TEXT_PATTERN_REGEXP)
    public MessageResponse handle(UpdateEvent updateEvent) {
        return sendMessage(MESSAGE_ANSWER, updateEvent)
                .setCallback((Consumer<Message>) message -> callbackMethod.run(message));
    }
}