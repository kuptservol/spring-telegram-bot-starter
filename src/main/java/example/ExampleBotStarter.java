package example;

import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.config.BotPlatformStarter;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import static ru.skuptsov.telegram.bot.platform.client.command.MessageResponse.sendMessage;

@MessageHandler
public class ExampleBotStarter {

    public static void main(String[] args) {
        BotPlatformStarter.start(ExampleBotStarter.class, "{token}");
    }

    @MessageMapping(text = "hi")
    public MessageResponse sayGoodMorning(UpdateEvent updateEvent) {
        return sendMessage("Good morning! Happy to see you!", updateEvent);
    }
}