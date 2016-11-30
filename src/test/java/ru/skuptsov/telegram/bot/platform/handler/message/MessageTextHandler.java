package ru.skuptsov.telegram.bot.platform.handler.message;

import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import static ru.skuptsov.telegram.bot.platform.client.command.MessageResponse.sendMessage;

@MessageHandler
public class MessageTextHandler {

    public static final String MESSAGE_TEXT = "Bye";
    public static final String MESSAGE_ANSWER = "Goodbye";

    @MessageMapping(text = MESSAGE_TEXT)
    public MessageResponse handle(UpdateEvent updateEvent) {
        return sendMessage(MESSAGE_ANSWER, updateEvent);
    }
}