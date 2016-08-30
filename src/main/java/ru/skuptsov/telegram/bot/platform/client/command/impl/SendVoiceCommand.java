package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import ru.skuptsov.telegram.bot.platform.model.api.methods.send.SendVoice;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class SendVoiceCommand extends ApiMessageCommand<SendVoice> {
    public SendVoiceCommand(SendVoice command) {
        super(command);
    }
}
