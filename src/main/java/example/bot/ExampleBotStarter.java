package example.bot;

import ru.skuptsov.telegram.bot.platform.config.BotPlatformStarter;

/**
 * @author Sergey Kuptsov
 * @since 04/07/2016
 */
public class ExampleBotStarter {
    public static void main(String[] args) {
        BotPlatformStarter.start(SimpleBotConfig.class, args);
    }
}
