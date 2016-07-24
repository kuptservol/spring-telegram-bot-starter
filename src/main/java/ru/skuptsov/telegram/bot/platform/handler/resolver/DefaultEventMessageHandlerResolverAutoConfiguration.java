package ru.skuptsov.telegram.bot.platform.handler.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.DefaultMessageHandler;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
@Configuration
@ConditionalOnMissingBean(DefaultMessageHandler.class)
public class DefaultEventMessageHandlerResolverAutoConfiguration {
    private Logger log = LoggerFactory.getLogger(DefaultMessageHandler.class);

    @Bean
    public DefaultMessageHandler defaultMessageHandler() {
        return updateEvent -> {
            log.info("No suitable processor found for event {}", updateEvent);
            return MessageResponse.EMPTY;
        };
    }
}
