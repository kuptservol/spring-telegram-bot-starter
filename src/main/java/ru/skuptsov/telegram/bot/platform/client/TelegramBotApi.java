package ru.skuptsov.telegram.bot.platform.client;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.objects.*;
import ru.skuptsov.telegram.bot.platform.client.command.Reply;
import ru.skuptsov.telegram.bot.platform.client.impl.TelegramBotApiImpl;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface TelegramBotApi {

    List<Update> getNextUpdates(Integer poolingLimit, Integer poolingTimeout);

    <T extends BotApiMethod<Message>> TelegramBotApiImpl.CommandExecutionWrapper<Message> sendMessage(@NotNull T message);

    <T> TelegramBotApiImpl.CommandExecutionWrapper<T> reply(@NotNull Reply<T> reply);

    TelegramBotApiImpl.CommandExecutionWrapper<User> getMe();

    TelegramBotApiImpl.CommandExecutionWrapper<File> getFile(@NotNull String file_id);

    TelegramBotApiImpl.CommandExecutionWrapper<UserProfilePhotos> getUserProfilePhotos(@NotNull GetUserProfilePhotos getUserProfilePhotos);

    TelegramBotApiImpl.CommandExecutionWrapper<Boolean> answerInlineQuery(@NotNull AnswerInlineQuery answerInlineQuery);

    TelegramBotApiImpl.CommandExecutionWrapper<Boolean> answerCallbackQuery(@NotNull AnswerCallbackQuery answerCallbackQuery);

    TelegramBotApiImpl.CommandExecutionWrapper<Chat> getChat(@NotNull String chatId);

    TelegramBotApiImpl.CommandExecutionWrapper<ArrayList<ChatMember>> getChatAdministrators(@NotNull String chatId);

    TelegramBotApiImpl.CommandExecutionWrapper<Boolean> leaveChat(@NotNull String chatId);

    TelegramBotApiImpl.CommandExecutionWrapper<Boolean> unbanChatMember(@NotNull String chatId, @NotNull Integer userId);

    TelegramBotApiImpl.CommandExecutionWrapper<Boolean> kickChatMember(@NotNull String chatId, @NotNull Integer userId);

    TelegramBotApiImpl.CommandExecutionWrapper<Integer> getChatMemberCount(@NotNull String chatId);
}
