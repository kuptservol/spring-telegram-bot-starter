package ru.skuptsov.telegram.bot.platform.client.exception;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public class TelegramBotApiException extends RuntimeException {
    public TelegramBotApiException() {
    }

    public TelegramBotApiException(String message) {
        super(message);
    }

    public TelegramBotApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramBotApiException(Throwable cause) {
        super(cause);
    }

    public TelegramBotApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
