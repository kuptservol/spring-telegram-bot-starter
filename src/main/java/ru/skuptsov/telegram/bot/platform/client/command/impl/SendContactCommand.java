package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.api.methods.send.SendContact;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class SendContactCommand extends ApiMessageCommand<SendContact> {
    public SendContactCommand(SendContact command) {
        super(command);
    }
}
