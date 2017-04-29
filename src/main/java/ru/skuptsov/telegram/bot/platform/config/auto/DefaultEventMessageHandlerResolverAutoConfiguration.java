package ru.skuptsov.telegram.bot.platform.config.auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skuptsov.telegram.bot.platform.client.command.Reply;
import ru.skuptsov.telegram.bot.platform.handler.DefaultMessageHandler;
import ru.skuptsov.telegram.bot.platform.service.MetricsService;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
@Configuration
@ConditionalOnMissingBean(DefaultMessageHandler.class)
public class DefaultEventMessageHandlerResolverAutoConfiguration {
    private Logger log = LoggerFactory.getLogger(DefaultMessageHandler.class);

    @Autowired
    private MetricsService metricsService;

    @Bean
    public DefaultMessageHandler defaultMessageHandler() {
        return updateEvent -> {
            log.info("No suitable processor found for event {}", updateEvent);
            metricsService.onNoMessageProcessorFound();
            return Reply.EMPTY;
        };
    }
}
