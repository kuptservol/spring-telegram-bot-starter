package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import ru.skuptsov.telegram.bot.platform.model.api.methods.send.SendPhoto;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class SendPhotoCommand extends ApiMessageCommand<SendPhoto> {
    public SendPhotoCommand(SendPhoto command) {
        super(command);
    }
}
