package ru.skuptsov.telegram.bot.platform.worker;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.client.command.ApiCommandSender;
import ru.skuptsov.telegram.bot.platform.config.WorkerConfiguration;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvents;
import ru.skuptsov.telegram.bot.platform.worker.saver.UpdatesWorkerRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.MoreExecutors.shutdownAndAwaitTermination;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
@Component
public class UpdatesWorker extends AbstractExecutionThreadService {
    private final static Logger logger = LoggerFactory.getLogger(UpdatesWorker.class);

    @Autowired
    private EventBus eventBus;

    @Autowired
    private UpdatesWorkerRepository updatesWorkerRepository;

    @Autowired
    private WorkerConfiguration workerConfiguration;

    @Autowired
    private WorkerTaskFactory workerTaskFactory;

    @Autowired
    private ApiCommandSender apiCommandSender;

    private ExecutorService updatesWorkerExecutor;

    private ExecutorService apiCommandSenderExecutor;

    @AllowConcurrentEvents
    @Subscribe
    public void handleUpdateEvents(@NotNull UpdateEvents updateEvents) {
        logger.debug("Received event {}", updateEvents);
        updatesWorkerRepository.save(updateEvents);
    }

    @Override
    protected void run() throws Exception {
        while (isRunning()) {
            UpdateEvent updateEvent = updatesWorkerRepository.get();
            CompletableFuture.supplyAsync(
                    () -> workerTaskFactory.create(updateEvent).execute(),
                    updatesWorkerExecutor)
                    .thenAcceptAsync(
                            apiCommand -> apiCommandSender.sendCommand(apiCommand),
                            apiCommandSenderExecutor);
        }
    }

    @Override
    protected void startUp() {
        eventBus.register(this);

        updatesWorkerExecutor = Executors.newFixedThreadPool(
                workerConfiguration.getThreadCount(),
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .setNameFormat("UpdatesWorkerTask-%d")
                        .build());

        apiCommandSenderExecutor = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .setNameFormat("ApiCommandSenderTask-%d")
                        .build());
    }

    @Override
    protected void triggerShutdown() {
        shutdownAndAwaitTermination(
                updatesWorkerExecutor,
                workerConfiguration.getShutdownTimeout(),
                workerConfiguration.getShutdownTimeoutTimeUnit());
        shutdownAndAwaitTermination(apiCommandSenderExecutor, 1, TimeUnit.SECONDS);
    }

    @PostConstruct
    public void init() {
        startAsync()
                .awaitRunning();
    }

    @PreDestroy
    public void destroy() {
        stopAsync()
                .awaitTerminated();
    }
}
