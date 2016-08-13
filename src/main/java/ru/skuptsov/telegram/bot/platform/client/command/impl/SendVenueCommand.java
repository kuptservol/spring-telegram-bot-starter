package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.api.methods.send.SendVenue;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class SendVenueCommand extends ApiMessageCommand<SendVenue> {
    public SendVenueCommand(SendVenue command) {
        super(command);
    }
}
