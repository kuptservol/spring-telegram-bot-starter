package ru.skuptsov.telegram.bot.platform.client;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface TelegramBotApi {

    List<Update> getNextUpdates(Integer poolingLimit, Integer poolingTimeout);

    <R extends BotApiMethod<Message>> Future<Message> executeMessageCommand(@NotNull R command);
}
