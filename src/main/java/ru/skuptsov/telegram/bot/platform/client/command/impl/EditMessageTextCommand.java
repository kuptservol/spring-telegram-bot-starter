package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class EditMessageTextCommand extends ApiMessageCommand<EditMessageText> {
    public EditMessageTextCommand(EditMessageText message) {
        super(message);
    }
}
