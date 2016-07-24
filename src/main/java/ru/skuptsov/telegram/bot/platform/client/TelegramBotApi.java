package ru.skuptsov.telegram.bot.platform.client;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface TelegramBotApi {

    List<Update> getNextUpdates(Integer poolingLimit, Integer poolingTimeout);

    Future<Message> sendMessage(SendMessage sendMessage);

    Future<Message> editMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup);

    Future<Message> editMessageText(EditMessageText editMessageText);
}
