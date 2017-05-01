package ru.skuptsov.telegram.bot.platform.handler.message;

import ru.skuptsov.telegram.bot.platform.client.command.Reply;
import ru.skuptsov.telegram.bot.platform.client.command.ReplyTo;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

@MessageHandler
public class MessageTextHandler {

    public static final String MESSAGE_TEXT = "Bye";
    public static final String MESSAGE_ANSWER = "Goodbye";

    @MessageMapping(text = MESSAGE_TEXT)
    public Reply handle(UpdateEvent updateEvent) {
        return ReplyTo.to(updateEvent).withMessage(MESSAGE_ANSWER);
    }
}