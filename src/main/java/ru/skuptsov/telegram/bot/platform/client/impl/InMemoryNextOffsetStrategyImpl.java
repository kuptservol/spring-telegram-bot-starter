package ru.skuptsov.telegram.bot.platform.client.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import ru.skuptsov.telegram.bot.platform.client.NextOffsetStrategy;
import ru.skuptsov.telegram.bot.platform.config.NextOffSetStrategyConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public class InMemoryNextOffsetStrategyImpl implements NextOffsetStrategy {
    private final Logger log = LoggerFactory.getLogger(IdentityNamingStrategy.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    private final ScheduledExecutorService persistCounterService = newScheduledThreadPool(1,
            new ThreadFactoryBuilder()
                    .setNameFormat("PersistCounterThread-%d")
                    .setDaemon(true)
                    .build());

    private RandomAccessFile writer;

    private SyncMode syncMode;

    @Autowired
    private NextOffSetStrategyConfiguration nextOffSetStrategyConfiguration;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public Integer getNextOffset() {
        return counter.get();
    }

    @Override
    public void saveLastOffset(@NotNull Integer lasOffset) {
        counter.set(lasOffset + 1);
        if (syncMode == SyncMode.SYNC) {
            writeCurrentOffsetValue();
        }
    }

    @PostConstruct
    public void init() {

        File file;
        Scanner reader = null;
        try {
            file = resourceLoader.getResource(
                    nextOffSetStrategyConfiguration.getNextOffsetFileResource()).getFile();

            reader = new Scanner(new FileReader(file));
            readCurrentOffsetValue(reader);
            writer = new RandomAccessFile(file, "rw");
        } catch (IOException e) {
            log.error(format("Can't open resource {%s}", nextOffSetStrategyConfiguration.getNextOffsetFileResource()));
            throw new IllegalArgumentException(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        initPersistence();
    }

    @PreDestroy
    public void destroy() {
        try {
            persistCounterService.shutdown();
            writeCurrentOffsetValue();
            writer.close();
        } catch (IOException e) {
            log.error("Can't close file");
        }
    }

    private void initPersistence() {
        if (nextOffSetStrategyConfiguration.getNextOffsetFileSyncPeriodSec() > 0) {
            persistCounterService.scheduleAtFixedRate(
                    this::writeCurrentOffsetValue,
                    nextOffSetStrategyConfiguration.getNextOffsetFileSyncPeriodSec(),
                    nextOffSetStrategyConfiguration.getNextOffsetFileSyncPeriodSec(),
                    SECONDS);
            syncMode = SyncMode.ASYNC;
        } else {
            syncMode = SyncMode.SYNC;
        }
    }

    private void readCurrentOffsetValue(Scanner reader) {
        if (reader.hasNextInt()) {
            int currentOffsetValue = reader.nextInt();
            log.debug("Setting current offset to value {}", currentOffsetValue);
            counter.set(currentOffsetValue);
        }
    }

    private void writeCurrentOffsetValue() {
        try {
            writer.seek(0);
            writer.write(valueOf(counter.get()).getBytes());
        } catch (IOException e) {
            log.error("Can't write counter to file");
        }
    }

    private enum SyncMode {
        ASYNC,
        SYNC
    }
}
