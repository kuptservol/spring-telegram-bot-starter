package ru.skuptsov.telegram.bot.platform.client.command.impl;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 25/08/2016
 */
public class AnswerCallbackQueryCommand extends AbstractApiCommand<Boolean> {

    private final AnswerCallbackQuery answerCallbackQuery;

    public AnswerCallbackQueryCommand(Consumer<Boolean> callback, AnswerCallbackQuery answerCallbackQuery) {
        super(callback);
        this.answerCallbackQuery = answerCallbackQuery;
    }

    @Override
    public Future<Boolean> execute(TelegramBotApi telegramBotApi) {
        return telegramBotApi.answerCallbackQuery(answerCallbackQuery);
    }
}
