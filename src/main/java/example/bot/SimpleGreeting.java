package example.bot;

import org.telegram.telegrambots.api.objects.Message;
import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.util.function.Consumer;

import static ru.skuptsov.telegram.bot.platform.client.command.MessageResponse.sendMessage;

/**
 * @author Sergey Kuptsov
 * @since 04/07/2016
 */
@MessageHandler
public class SimpleGreeting {

    @MessageMapping(text = "Привет")
    public MessageResponse sayHi(UpdateEvent updateEvent) {
        return sendMessage("Привет!", updateEvent)
                .setCallback((Consumer<Message>) message -> System.out.println("Message sent"));
    }

    @MessageMapping(regexp = "(.*пока.*|.*до свиданья.*)")
    public MessageResponse sayGoodbye(UpdateEvent updateEvent) {
        return sendMessage("До новых встреч", updateEvent);
    }
}
