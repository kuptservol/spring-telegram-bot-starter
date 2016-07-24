package ru.skuptsov.telegram.bot.platform.handler;

import org.telegram.telegrambots.api.objects.Message;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

/**
 * @author Sergey Kuptsov
 * @since 04/07/2016
 */
public interface UpdateFunctions {

    Function<UpdateEvent, String> MESSAGE_TEXT_FROM_UPDATE_EVENT =
            updateEvent ->
                    ofNullable(updateEvent.getUpdate().getMessage())
                            .map(Message::getText)
                            .orElse("");
}



