package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.objects.Message;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 02/06/2016
 */
@Getter
@ToString
public class EditMessageReplyMarkupCommand extends AbstractApiCommand<Message> {
    private final Logger log = LoggerFactory.getLogger(EditMessageReplyMarkupCommand.class);

    private final EditMessageReplyMarkup editMessageReplyMarkup;

    @Builder
    public EditMessageReplyMarkupCommand(Consumer<Message> callback, EditMessageReplyMarkup editMessageReplyMarkup) {
        super(callback);
        this.editMessageReplyMarkup = editMessageReplyMarkup;
    }

    @Override
    public Future<Message> execute(TelegramBotApi telegramBotApi) {
        return telegramBotApi.editMessageReplyMarkup(editMessageReplyMarkup);
    }
}
