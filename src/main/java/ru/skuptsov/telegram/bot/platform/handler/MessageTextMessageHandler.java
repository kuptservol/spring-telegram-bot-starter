package ru.skuptsov.telegram.bot.platform.handler;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public interface MessageTextMessageHandler extends MessageHandler {

    @NotNull
    Set<String> getMessageText();
}
