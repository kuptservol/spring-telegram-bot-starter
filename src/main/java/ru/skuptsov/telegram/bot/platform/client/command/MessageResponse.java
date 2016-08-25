package ru.skuptsov.telegram.bot.platform.client.command;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.send.SendContact;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendVenue;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import ru.skuptsov.telegram.bot.platform.client.command.impl.*;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
public class MessageResponse {
    public final static MessageResponse EMPTY = new MessageResponse(ApiCommand.EMPTY);

    private final ApiCommand apiCommand;

    public MessageResponse(ApiCommand<?> apiCommand) {
        this.apiCommand = apiCommand;
    }

    public ApiCommand getApiCommand() {
        return apiCommand;
    }

    public <T> MessageResponse setCallback(Consumer<T> callback) {
        apiCommand.setCallback(callback);

        return this;
    }

    public static MessageResponse fromCommand(@NotNull ApiCommand<?> apiCommand) {
        return new MessageResponse(apiCommand);
    }

    public static MessageResponse sendMessage(@NotNull String messageText, @NotNull UpdateEvent updateEvent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getChatId(updateEvent));
        sendMessage.setText(messageText);

        return fromCommand(new SendMessageCommand(sendMessage));
    }

    public static MessageResponse sendMessage(@NotNull SendMessage sendMessage) {
        return fromCommand(new SendMessageCommand(sendMessage));
    }

    public static MessageResponse editMessageText(@NotNull EditMessageText editMessageText) {
        return fromCommand(new EditMessageTextCommand(editMessageText));
    }

    public static MessageResponse editMessageReplyMarkup(@NotNull EditMessageReplyMarkup editMessageReplyMarkup) {
        return fromCommand(new EditMessageReplyMarkupCommand(editMessageReplyMarkup));
    }

    public static MessageResponse sendContact(@NotNull SendContact sendContact) {
        return fromCommand(new SendContactCommand(sendContact));
    }

    public static MessageResponse sendLocation(@NotNull SendLocation message) {
        return fromCommand(new SendLocationCommand(message));
    }

    public static MessageResponse answerCallbackQuery(@NotNull AnswerCallbackQuery message) {
        return fromCommand(new AnswerCallbackQueryCommand(null, message));
    }

    public static MessageResponse answerInlineQuery(@NotNull AnswerInlineQuery message) {
        return fromCommand(new AnswerInlineQueryCommand(null, message));
    }

    public static MessageResponse sendVenue(@NotNull SendVenue message) {
        return fromCommand(new SendVenueCommand(message));
    }

    public static MessageResponse editMessageCaption(@NotNull EditMessageCaption message) {
        return fromCommand(new EditMessageCaptionCommand(message));
    }

    public static MessageResponse forwardMessage(@NotNull ForwardMessage message) {
        return fromCommand(new ForwardMessageCommand(message));
    }

    private static String getChatId(@NotNull UpdateEvent updateEvent) {
        return updateEvent.getUpdate().getMessage().getChatId().toString();
    }
}
