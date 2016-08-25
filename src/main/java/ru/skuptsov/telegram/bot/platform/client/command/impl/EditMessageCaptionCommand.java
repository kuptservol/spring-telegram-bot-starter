package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class EditMessageCaptionCommand extends ApiMessageCommand<EditMessageCaption> {
    public EditMessageCaptionCommand(EditMessageCaption command) {
        super(command);
    }
}
