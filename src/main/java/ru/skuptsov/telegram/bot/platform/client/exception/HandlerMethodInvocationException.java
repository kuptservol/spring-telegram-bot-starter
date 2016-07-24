package ru.skuptsov.telegram.bot.platform.client.exception;

import org.springframework.core.NestedRuntimeException;

import java.lang.reflect.Method;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
public class HandlerMethodInvocationException extends NestedRuntimeException {

    public HandlerMethodInvocationException(Method handlerMethod, Throwable cause) {
        super("Failed to invoke handler method [" + handlerMethod + "]", cause);
    }

}
