package ru.skuptsov.telegram.bot.platform.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
@Configuration
@Getter
public class WorkerConfiguration {

    @Value("${updates.worker.task.thread.size:4}")
    private Integer threadCount;

    @Value("${updates.worker.task.thread.shutdown.timeout:5}")
    private long shutdownTimeout;

    public TimeUnit getShutdownTimeoutTimeUnit() {
        return TimeUnit.SECONDS;
    }
}
