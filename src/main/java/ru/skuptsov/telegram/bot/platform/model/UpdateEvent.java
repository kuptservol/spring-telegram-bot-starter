package ru.skuptsov.telegram.bot.platform.model;

import lombok.*;
import org.joda.time.DateTime;
import org.telegram.telegrambots.api.objects.Update;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEvent {
    public final static UpdateEvent EMPTY = new UpdateEvent();

    /**
     * Original update receive from telegram
     */
    private Update update;
    /**
     * Received time
     */
    private DateTime received;
}
