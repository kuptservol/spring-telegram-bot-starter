package ru.skuptsov.telegram.bot.platform.model;

import lombok.*;
import org.joda.time.DateTime;
import org.telegram.telegrambots.api.objects.Update;
import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;

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

    private Update update;
    private MessageHandler messageHandler;
    private DateTime received;
}
