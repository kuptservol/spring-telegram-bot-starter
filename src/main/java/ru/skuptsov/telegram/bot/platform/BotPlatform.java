package ru.skuptsov.telegram.bot.platform;

import org.springframework.boot.SpringApplication;
import ru.skuptsov.telegram.bot.platform.config.BotPlatformConfiguration;

/**
 * @author Sergey Kuptsov
 * @since 21/05/2016
 */
public class BotPlatform {
    public static void main(String[] args) {
        SpringApplication.run(BotPlatformConfiguration.class, args);
    }
}
