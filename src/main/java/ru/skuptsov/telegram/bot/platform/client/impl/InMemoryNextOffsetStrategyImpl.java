package ru.skuptsov.telegram.bot.platform.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import ru.skuptsov.telegram.bot.platform.client.NextOffsetStrategy;

import javax.validation.constraints.NotNull;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public class InMemoryNextOffsetStrategyImpl implements NextOffsetStrategy {
    private final Logger log = LoggerFactory.getLogger(IdentityNamingStrategy.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Integer getNextOffset() {
        return counter.get();
    }

    @Override
    public void saveLastOffset(@NotNull Integer lasOffset) {
        counter.set(lasOffset + 1);
    }
}
