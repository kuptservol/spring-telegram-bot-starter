package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.api.methods.ForwardMessage;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class ForwardMessageCommand extends ApiMessageCommand<ForwardMessage> {
    public ForwardMessageCommand(ForwardMessage command) {
        super(command);
    }
}
