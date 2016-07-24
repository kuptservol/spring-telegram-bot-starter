package ru.skuptsov.telegram.bot.platform.client.command;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.skuptsov.telegram.bot.platform.client.command.impl.SendMessageCommand;
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

    public static MessageResponse text(@NotNull String messageText, @NotNull UpdateEvent updateEvent) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(updateEvent.getUpdate().getMessage().getChatId().toString());
        sendMessage.setText(messageText);

        return new MessageResponse(SendMessageCommand.builder()
                .sendMessage(sendMessage)
                .build());
    }
}
