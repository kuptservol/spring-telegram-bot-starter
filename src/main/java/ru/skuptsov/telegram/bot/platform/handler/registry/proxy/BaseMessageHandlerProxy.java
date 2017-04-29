package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import ru.skuptsov.telegram.bot.platform.client.command.Reply;
import ru.skuptsov.telegram.bot.platform.client.exception.HandlerMethodInvocationException;
import ru.skuptsov.telegram.bot.platform.client.exception.TelegramBotApiException;
import ru.skuptsov.telegram.bot.platform.handler.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.registry.MessageHandlerBeanPostProcessor;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;
import ru.skuptsov.telegram.bot.platform.service.MetricsService;

import java.lang.reflect.Method;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
public abstract class BaseMessageHandlerProxy implements MessageHandler {
    private static final Logger log = LoggerFactory.getLogger(MessageHandlerBeanPostProcessor.class);

    private final HandlerMethod handlerMethod;

    private final MetricsService metricsService;

    public BaseMessageHandlerProxy(HandlerMethod handlerMethod, MetricsService metricsService) {
        this.handlerMethod = handlerMethod;
        this.metricsService = metricsService;
    }

    @Override
    public Reply handle(UpdateEvent updateEvent) {
        log.trace("Invoking handler [{}] with update event [{}]", this.getClass(), updateEvent);
        Method handlerMethodToInvoke = handlerMethod.getBridgedMethod();

        try {
            ReflectionUtils.makeAccessible(handlerMethodToInvoke);
            Object[] args = {updateEvent};

            long start = System.currentTimeMillis();

            Reply reply = (Reply) handlerMethodToInvoke.invoke(handlerMethod.getBean(), args);

            metricsService.onMessageProcessingComplete(handlerMethod, System.currentTimeMillis() - start);
            return reply;
        } catch (IllegalStateException ex) {
            throw new HandlerMethodInvocationException(handlerMethodToInvoke, ex);
        } catch (Exception ex) {
            metricsService.onMessageProcessingError(handlerMethod);
            throw new TelegramBotApiException(ex);
        }
    }
}
