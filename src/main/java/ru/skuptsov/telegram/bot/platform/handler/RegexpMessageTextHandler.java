package ru.skuptsov.telegram.bot.platform.handler;

import javax.validation.constraints.NotNull;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public interface RegexpMessageTextHandler extends MessageHandler {

    @NotNull
    String getMessageRegexp();
}
