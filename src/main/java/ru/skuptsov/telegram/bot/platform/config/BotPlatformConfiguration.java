package ru.skuptsov.telegram.bot.platform.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Sergey Kuptsov
 * @since 21/05/2016
 */
@Configuration
@Import(value = {
        UpdatesRepositoryConfiguration.class,
        TelegramBotClientConfiguration.class,
        UpdatesWorkerRepositoryConfiguration.class,
        WorkerConfiguration.class,
        ApiCommandSenderConfiguration.class
})
@ComponentScan(value = "ru.skuptsov.telegram.bot.platform")
public class BotPlatformConfiguration {
}
