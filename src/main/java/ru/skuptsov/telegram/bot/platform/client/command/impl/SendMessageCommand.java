package ru.skuptsov.telegram.bot.platform.client.command.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
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
public class SendMessageCommand extends AbstractApiCommand<Message> {
    private final Logger log = LoggerFactory.getLogger(SendMessageCommand.class);

    private final SendMessage sendMessage;

    @Builder
    public SendMessageCommand(Consumer<Message> callback, SendMessage sendMessage) {
        super(callback);
        this.sendMessage = sendMessage;
    }

    @Override
    public Future<Message> execute(TelegramBotApi telegramBotApi) {
        return telegramBotApi.sendMessage(sendMessage);
    }
}
