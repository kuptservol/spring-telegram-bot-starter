package ru.skuptsov.telegram.bot.platform.client.command.impl;

import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 25/08/2016
 */
public class AnswerInlineQueryCommand extends AbstractApiCommand<Boolean> {

    private final AnswerInlineQuery answerInlineQuery;

    public AnswerInlineQueryCommand(Consumer<Boolean> callback, AnswerInlineQuery answerInlineQuery) {
        super(callback);
        this.answerInlineQuery = answerInlineQuery;
    }

    @Override
    public Future<Boolean> execute(TelegramBotApi telegramBotApi) {
        return telegramBotApi.answerInlineQuery(answerInlineQuery);
    }
}
