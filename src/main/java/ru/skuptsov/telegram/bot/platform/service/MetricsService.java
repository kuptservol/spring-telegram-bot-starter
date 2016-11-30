package ru.skuptsov.telegram.bot.platform.service;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SlidingWindowReservoir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.handler.registry.proxy.HandlerMethod;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

/**
 * @author Sergey Kuptsov
 * @since 26/11/2016
 */
@Component
public class MetricsService {
    private static final String MESSAGE_RECEIVED = "bot.api.client.message.received";
    private static final String MESSAGE_PROCESSING_ERROR = "bot.api.client.process.error";
    private static final String MESSAGE_PROCESSING_METHOD_EXCEPTION = "bot.message.processing.%s.exception";
    private static final String MESSAGE_PROCESSING_METHOD_EXECUTION_TIME = "bot.message.processing.%s.execution.time";
    private static final String MESSAGE_PROCESSING_METHOD_SUCCESS = "bot.message.processing.%s.success";
    private static final String MESSAGE_PROCESSOR_NOT_FOUND_ERROR = "bot.message.processor.not.found";

    @Autowired
    private MetricRegistry metricRegistry;

    public void onMessagesReceived(int messages) {
        metricRegistry.getMeters().get(MESSAGE_RECEIVED).mark(messages);
    }

    public void registerMessageProcessingMethod(HandlerMethod method) {
        metricRegistry.register(format(MESSAGE_PROCESSING_METHOD_EXCEPTION, getMethodName(method)), new Meter());
        metricRegistry.register(format(MESSAGE_PROCESSING_METHOD_EXECUTION_TIME, getMethodName(method)), new Histogram(new SlidingWindowReservoir(128)));
        metricRegistry.register(format(MESSAGE_PROCESSING_METHOD_SUCCESS, getMethodName(method)), new Meter());
    }

    public void onMessageProcessingError(HandlerMethod method) {
        metricRegistry.getMeters().get(format(MESSAGE_PROCESSING_METHOD_EXCEPTION, getMethodName(method))).mark();
    }

    private void onMessageProcessingSuccess(HandlerMethod method) {
        metricRegistry.getMeters().get(format(MESSAGE_PROCESSING_METHOD_SUCCESS, getMethodName(method))).mark();
    }

    public void onMessageProcessingComplete(HandlerMethod method, long millis) {
        onMessageProcessingSuccess(method);
        metricRegistry.getHistograms().get(format(MESSAGE_PROCESSING_METHOD_EXECUTION_TIME, getMethodName(method))).update(millis);
    }

    public void onMessageProcessingError() {
        metricRegistry.getMeters().get(MESSAGE_PROCESSING_ERROR).mark();
    }

    private String getMethodName(HandlerMethod method) {
        return method.getBeanType().getName() + "." + method.getBridgedMethod().getName();
    }

    @PostConstruct
    public void init() {
        metricRegistry.register(MESSAGE_RECEIVED, new Meter());
        metricRegistry.register(MESSAGE_PROCESSING_ERROR, new Meter());
        metricRegistry.register(MESSAGE_PROCESSOR_NOT_FOUND_ERROR, new Meter());
    }

    public void onNoMessageProcessorFound() {
        metricRegistry.getMeters().get(MESSAGE_PROCESSOR_NOT_FOUND_ERROR).mark();
    }
}
