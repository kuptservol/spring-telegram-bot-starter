package ru.skuptsov.telegram.bot.platform.client;

import org.telegram.telegrambots.api.objects.Update;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface NextOffsetStrategy {
    Integer getNextOffset();

    void saveCurrentOffset(@NotNull List<Update> updates);
}
