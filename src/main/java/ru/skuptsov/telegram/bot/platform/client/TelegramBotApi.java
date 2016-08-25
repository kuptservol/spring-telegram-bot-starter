package ru.skuptsov.telegram.bot.platform.client;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.api.objects.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public interface TelegramBotApi {

    List<Update> getNextUpdates(Integer poolingLimit, Integer poolingTimeout);

    <R extends BotApiMethod<Message>> Future<Message> sendMessageAsync(@NotNull R command);

    <T extends BotApiMethod<Message>> Optional<Message> sendMessageSync(@NotNull T message);

    Optional<User> getMe() throws ExecutionException, InterruptedException;

    Optional<File> getFile(@NotNull String file_id);

    Optional<UserProfilePhotos> getUserProfilePhotos(@NotNull GetUserProfilePhotos getUserProfilePhotos);

    Future<Boolean> answerInlineQuery(@NotNull AnswerInlineQuery answerInlineQuery);

    Future<Boolean> answerCallbackQuery(@NotNull AnswerCallbackQuery answerCallbackQuery);

    Optional<Chat> getChat(@NotNull String chatId);

    Optional<ArrayList<ChatMember>> getChatAdministrators(@NotNull String chatId);

    Optional<Boolean> leaveChat(@NotNull String chatId);

    Optional<Boolean> unbanChatMember(@NotNull String chatId, @NotNull Integer userId);

    Optional<Boolean> kickChatMember(@NotNull String chatId, @NotNull Integer userId);

    Optional<Integer> getChatMemberCount(@NotNull String chatId);
}
