package ru.skuptsov.telegram.bot.platform.client;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface NextOffsetStrategy {
    Integer getNextOffset();

    void saveLastOffset(@NotNull Integer lasOffset);
}
