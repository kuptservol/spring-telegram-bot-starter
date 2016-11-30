package ru.skuptsov.telegram.bot.platform.handler.callback;

import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import static ru.skuptsov.telegram.bot.platform.client.command.MessageResponse.sendMessage;

@MessageHandler
public class MessageCallbackProcessor {

    public static final String MESSAGE_SELECTED = "1";
    public static final String MESSAGE_ANSWER = "OK";

    @MessageMapping(callback = MESSAGE_SELECTED)
    public MessageResponse handle(UpdateEvent updateEvent) {
        return sendMessage(MESSAGE_ANSWER, updateEvent);
    }
}