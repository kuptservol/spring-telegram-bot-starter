package ru.skuptsov.telegram.bot.platform.config;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.boot.SpringApplication.run;

/**
 * @author Sergey Kuptsov
 * @since 04/07/2016
 */
public class BotPlatformStarter {
    private static final Logger log = LoggerFactory.getLogger(BotPlatformStarter.class);
    private static final Object BOT_PLATFORM_CONFIGURATION_CLASS = BotPlatformConfiguration.class;
    private static final List<Object> configs = newArrayList(BOT_PLATFORM_CONFIGURATION_CLASS);

    public static void start(@NotNull List<Object> configurations, @NotNull String[] args) {
        checkNotNull(configurations, "Configuration list cannot be null");
        checkArgument(!configurations.isEmpty(), "You must specify at least one configuration file");
        configs.addAll(configurations);

        start(args);
    }

    public static void start(@NotNull Object configuration, @NotNull String[] args) {
        Preconditions.checkNotNull(configuration, "Configuration cannot be null");
        configs.add(configuration);

        start(args);
    }

    private static void start(String[] args) {
        log.debug("Starting bot platfrom with configurations [{}]", configs);
        run(copyOf(configs).toArray(), args);
    }
}
