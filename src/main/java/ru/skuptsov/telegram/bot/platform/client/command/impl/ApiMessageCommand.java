package ru.skuptsov.telegram.bot.platform.client.command.impl;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Message;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 13/08/2016
 */
public abstract class ApiMessageCommand<T extends BotApiMethod<Message>> extends AbstractApiCommand<Message> {

    private final T message;

    public ApiMessageCommand(Consumer<Message> callback, T message) {
        super(callback);
        this.message = message;
    }

    public ApiMessageCommand(T message) {
        super(null);
        this.message = message;
    }

    @Override
    public Future<Message> execute(@NotNull TelegramBotApi telegramBotApi) {
        return telegramBotApi.sendMessageAsync(message);
    }

    @Override
    public String toString() {
        return "ApiMessageCommand{" +
                "message=" + message +
                '}';
    }
}
