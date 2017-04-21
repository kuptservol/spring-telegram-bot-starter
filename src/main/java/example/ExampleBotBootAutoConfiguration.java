package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import static com.google.common.collect.ImmutableList.of;
import static java.lang.String.format;
import static ru.skuptsov.telegram.bot.platform.client.command.MessageResponse.sendMessage;
import static ru.skuptsov.telegram.bot.platform.config.TelegramBotClientConfiguration.TELEGRAM_CLIENT_TOKEN;

@EnableAutoConfiguration
@MessageHandler
public class ExampleBotBootAutoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(ExampleBotBootAutoConfiguration.class, format("--%s=%s", TELEGRAM_CLIENT_TOKEN, "{token}"));
    }

    @MessageMapping(text = "hi")
    public MessageResponse sayGoodMorning(UpdateEvent updateEvent) {
        return sendMessage("Good morning! Happy to see you!", updateEvent);
    }
}