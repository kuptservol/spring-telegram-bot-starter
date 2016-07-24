package ru.skuptsov.telegram.bot.platform.client.command;

import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 31/05/2016
 */
public interface ApiCommand<T> {
    ApiCommand EMPTY = telegramBotApi -> {
        CompletableFuture<Object> future = new CompletableFuture<>();
        future.complete(new Object());
        return future;
    };

    Consumer EMPTY_CALLBACK = o -> {
    };

    Future<T> execute(TelegramBotApi telegramBotApi);

    default Consumer<T> getCallback() {
        return EMPTY_CALLBACK;
    }

    default void callback(Future<T> future) {
    }

    default void setCallback(Consumer<T> callback) {
    }
}
