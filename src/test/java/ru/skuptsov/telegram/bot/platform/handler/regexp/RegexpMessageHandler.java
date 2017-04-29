package ru.skuptsov.telegram.bot.platform.handler.regexp;

import ru.skuptsov.telegram.bot.platform.client.command.Reply;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

@MessageHandler
public class RegexpMessageHandler {

    public static final String MESSAGE_TEXT_PATTERN_REGEXP = ".*(hi|hello).*";
    public static final String MESSAGE_ANSWER = "Hi there";

    @MessageMapping(regexp = MESSAGE_TEXT_PATTERN_REGEXP)
    public Reply handle(UpdateEvent updateEvent) {
        return Reply.withMessage(MESSAGE_ANSWER, updateEvent);
    }
}