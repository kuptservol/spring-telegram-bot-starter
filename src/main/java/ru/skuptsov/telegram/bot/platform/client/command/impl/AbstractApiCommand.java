package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skuptsov.telegram.bot.platform.client.command.ApiCommand;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 31/05/2016
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class AbstractApiCommand<T> implements ApiCommand<T> {
    private final Logger log = LoggerFactory.getLogger(AbstractApiCommand.class);

    private Consumer<T> callback = EMPTY_CALLBACK;

    @Override
    public void callback(Future<T> future) {
        try {
            callback.accept(future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Got execution exception {}", e);
        }
    }
}
