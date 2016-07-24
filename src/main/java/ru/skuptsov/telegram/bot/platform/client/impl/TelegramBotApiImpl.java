package ru.skuptsov.telegram.bot.platform.client.impl;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.skuptsov.telegram.bot.platform.client.NextOffsetStrategy;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.google.common.collect.ImmutableMap.of;
import static ru.skuptsov.telegram.bot.platform.client.utils.JavaTypeUtils.listTypeOf;
import static ru.skuptsov.telegram.bot.platform.client.utils.JavaTypeUtils.simpleTypeOf;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public class TelegramBotApiImpl implements TelegramBotApi {
    private final Logger log = LoggerFactory.getLogger(TelegramBotApiImpl.class);
    private final TelegramBotHttpClient client;
    @Autowired
    private NextOffsetStrategy nextOffsetStrategy;

    public TelegramBotApiImpl(TelegramBotHttpClient client) {
        this.client = client;
    }

    @Override
    @Timed(name = "bot.api.client.getNextUpdates", absolute = true)
    public List<Update> getNextUpdates(Integer poolingLimit, Integer poolingTimeout) {
        List<Update> updates = new ArrayList<>();

        try {
            Future<List<Update>> futureUpdates = client.executeGet(
                    "getUpdates",
                    of("offset", nextOffsetStrategy.getNextOffset().toString(),
                            "timeout", poolingTimeout.toString(),
                            "limit", poolingLimit.toString()),
                    listTypeOf(Update.class));

            updates = futureUpdates.get();

            nextOffsetStrategy.saveCurrentOffset(updates);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Can't get updates with exception {}", e);
        }

        return updates;
    }

    @Override
    public Future<Message> sendMessage(SendMessage sendMessage) {
        return client.executePost(
                sendMessage.getPath(),
                sendMessage,
                simpleTypeOf(Message.class));
    }

    @Override
    public Future<Message> editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) {
        return client.executePost(
                editMessageReplyMarkup.getPath(),
                editMessageReplyMarkup,
                simpleTypeOf(Message.class));
    }

    @Override
    public Future<Message> editMessageText(EditMessageText editMessageText) {
        return client.executePost(
                editMessageText.getPath(),
                editMessageText,
                simpleTypeOf(Message.class));
    }

}
