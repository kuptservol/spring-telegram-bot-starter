package ru.skuptsov.telegram.bot.platform.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Kuptsov
 * @since 27/05/2016
 */
@Configuration
@Getter
public class NextOffSetStrategyConfiguration {

    @Value("${telegram.nextOffsetFileResource:classpath:nextOffsetFile}")
    private String nextOffsetFileResource;

    @Value("${telegram.nextOffsetFileSyncPeriodSec:0}")
    private Integer nextOffsetFileSyncPeriodSec;
}
