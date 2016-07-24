package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
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
public class EditMessageTextCommand extends AbstractApiCommand<Message> {
    private final Logger log = LoggerFactory.getLogger(EditMessageTextCommand.class);

    private final EditMessageText editMessageText;

    @Builder
    public EditMessageTextCommand(Consumer<Message> callback, EditMessageText editMessageText) {
        super(callback);
        this.editMessageText = editMessageText;
    }

    @Override
    public Future<Message> execute(TelegramBotApi telegramBotApi) {
        return telegramBotApi.editMessageText(editMessageText);
    }
}
