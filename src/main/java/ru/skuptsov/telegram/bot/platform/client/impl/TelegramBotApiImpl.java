package ru.skuptsov.telegram.bot.platform.client.impl;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.*;
import org.telegram.telegrambots.api.methods.groupadministration.*;
import org.telegram.telegrambots.api.objects.*;
import ru.skuptsov.telegram.bot.platform.client.NextOffsetStrategy;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotHttpClient;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Comparator.comparingInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.telegram.telegrambots.api.methods.GetFile.FILEID_FIELD;
import static org.telegram.telegrambots.api.methods.send.SendSticker.CHATID_FIELD;
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

            updates.stream()
                    .map(Update::getUpdateId)
                    .max(comparingInt(Integer::intValue).reversed())
                    .ifPresent(value ->
                            nextOffsetStrategy.saveLastOffset(value));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Can't get updates with exception {}", e);
        }

        return updates;
    }

    @Override
    public <T extends BotApiMethod<Message>> Future<Message> sendMessageAsync(@NotNull T message) {
        return client.executePost(
                message.getPath(),
                message,
                simpleTypeOf(Message.class)
        );
    }

    @Override
    public <T extends BotApiMethod<Message>> Optional<Message> sendMessageSync(@NotNull T message) {
        Future<Message> messageFuture = client.executePost(
                message.getPath(),
                message,
                simpleTypeOf(Message.class)
        );

        return executeSync(messageFuture, message.getPath());
    }

    @Override
    public Optional<User> getMe() {
        Future<User> user = client.executeGet(
                GetMe.PATH,
                null,
                simpleTypeOf(User.class)
        );

        return executeSync(user, GetMe.PATH);
    }

    @Override
    public Optional<File> getFile(@NotNull String file_id) {
        Future<File> fileFuture = client.executeGet(
                GetFile.PATH,
                of(FILEID_FIELD, file_id),
                simpleTypeOf(File.class)
        );

        return executeSync(fileFuture, GetFile.PATH);
    }

    @Override
    public Optional<UserProfilePhotos> getUserProfilePhotos(@NotNull GetUserProfilePhotos getUserProfilePhotos) {
        Future<UserProfilePhotos> userProfilePhotosFuture = client.executePost(
                GetUserProfilePhotos.PATH,
                getUserProfilePhotos,
                simpleTypeOf(UserProfilePhotos.class)
        );

        return executeSync(userProfilePhotosFuture, GetFile.PATH);
    }

    @Override
    public Future<Boolean> answerInlineQuery(@NotNull AnswerInlineQuery answerInlineQuery) {
        return client.executePost(
                AnswerInlineQuery.PATH,
                answerInlineQuery,
                simpleTypeOf(Boolean.class)
        );
    }

    @Override
    public Future<Boolean> answerCallbackQuery(@NotNull AnswerCallbackQuery answerCallbackQuery) {
        return client.executePost(
                AnswerCallbackQuery.PATH,
                answerCallbackQuery,
                simpleTypeOf(Boolean.class)
        );
    }

    @Override
    public Optional<Chat> getChat(@NotNull String chatId) {
        Future<Chat> chatFuture = client.executeGet(
                GetChat.PATH,
                of(CHATID_FIELD, chatId),
                simpleTypeOf(Chat.class)
        );

        return executeSync(chatFuture, GetChat.PATH);
    }

    @Override
    public Optional<ArrayList<ChatMember>> getChatAdministrators(@NotNull String chatId) {
        Future<ArrayList<ChatMember>> chatFuture = client.executeGet(
                GetChatAdministrators.PATH,
                of(CHATID_FIELD, chatId),
                listTypeOf(ChatMember.class)
        );

        return executeSync(chatFuture, GetChatAdministrators.PATH);
    }

    @Override
    public Optional<Boolean> leaveChat(@NotNull String chatId) {
        Future<Boolean> leaveChatFuture = client.executeGet(
                LeaveChat.PATH,
                of(CHATID_FIELD, chatId),
                simpleTypeOf(Boolean.class)
        );

        return executeSync(leaveChatFuture, LeaveChat.PATH);
    }

    @Override
    public Optional<Boolean> unbanChatMember(@NotNull String chatId, @NotNull Integer userId) {
        Future<Boolean> unbanChatMemberFuture = client.executeGet(
                UnbanChatMember.PATH,
                of(CHATID_FIELD, chatId, "user_id", userId.toString()),
                simpleTypeOf(Boolean.class)
        );

        return executeSync(unbanChatMemberFuture, UnbanChatMember.PATH);
    }

    @Override
    public Optional<Boolean> kickChatMember(@NotNull String chatId, @NotNull Integer userId) {
        Future<Boolean> kickChatMemberFuture = client.executeGet(
                KickChatMember.PATH,
                of(CHATID_FIELD, chatId, "user_id", userId.toString()),
                simpleTypeOf(Boolean.class)
        );

        return executeSync(kickChatMemberFuture, KickChatMember.PATH);
    }

    @Override
    public Optional<Integer> getChatMemberCount(@NotNull String chatId) {
        Future<Integer> getChatMembersFuture = client.executeGet(
                GetChatMemberCount.PATH,
                of(CHATID_FIELD, chatId),
                simpleTypeOf(Boolean.class)
        );

        return executeSync(getChatMembersFuture, GetChatMemberCount.PATH);
    }

    private <T> Optional<T> executeSync(Future<T> futureResult, String method) {
        try {
            return of(futureResult.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occured while executing method [{}], cause: ", method, e);
        }

        return empty();
    }
}
