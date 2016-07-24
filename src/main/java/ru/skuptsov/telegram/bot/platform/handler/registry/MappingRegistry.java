package ru.skuptsov.telegram.bot.platform.handler.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import ru.skuptsov.telegram.bot.platform.client.command.MessageResponse;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;
import ru.skuptsov.telegram.bot.platform.handler.registry.proxy.CallbackQueryDataMessageProxy;
import ru.skuptsov.telegram.bot.platform.handler.registry.proxy.HandlerMethod;
import ru.skuptsov.telegram.bot.platform.handler.registry.proxy.MessageTextMessageHandlerProxy;
import ru.skuptsov.telegram.bot.platform.handler.registry.proxy.RegexpMessageTextHandlerProxy;
import ru.skuptsov.telegram.bot.platform.handler.resolver.CallbackQueryDataMessageHandlerResolver;
import ru.skuptsov.telegram.bot.platform.handler.resolver.ConditionEventMessageHandlerResolver;
import ru.skuptsov.telegram.bot.platform.handler.resolver.MessageTextMessageHandlerResolver;
import ru.skuptsov.telegram.bot.platform.handler.resolver.RegexpMessageTextHandlerResolver;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableSet.copyOf;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Sergey Kuptsov
 * @since 23/07/2016
 */
@Component
public class MappingRegistry implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(MappingRegistry.class);

    @Autowired
    private MessageTextMessageHandlerResolver messageTextMessageHandlerResolver;

    @Autowired
    private CallbackQueryDataMessageHandlerResolver callbackQueryDataMessageHandlerResolver;

    @Autowired
    private ConditionEventMessageHandlerResolver conditionEventMessageHandlerResolver;

    @Autowired
    private RegexpMessageTextHandlerResolver regexpMessageTextHandlerResolver;

    private ApplicationContext applicationContext;

    //todo: add injecting api command sender
    public void register(Object handler, Method method) {
        log.trace("Registering handler [{}] with method [{}] started", handler, method);

        HandlerMethod handlerMethod = createHandlerMethod(handler, method);
        checkParameters(handlerMethod);

        initResolverProxy(handlerMethod, method.getAnnotation(MessageMapping.class));

        log.trace("Registering handler [{}] with method [{}] finished", handler, method);
    }

    private void initResolverProxy(HandlerMethod handlerMethod, MessageMapping messageMapping) {
        boolean foundResolver = false;
        if (!isEmpty(messageMapping.regexp())) {
            foundResolver = true;
            regexpMessageTextHandlerResolver.add(
                    new RegexpMessageTextHandlerProxy(handlerMethod, messageMapping.regexp())
            );
        }

        if (messageMapping.callback().length != 0) {
            foundResolver = true;
            callbackQueryDataMessageHandlerResolver.add(
                    new CallbackQueryDataMessageProxy(handlerMethod, copyOf(messageMapping.callback())));
        }

        if (messageMapping.text().length != 0) {
            foundResolver = true;
            messageTextMessageHandlerResolver.add(
                    new MessageTextMessageHandlerProxy(handlerMethod, copyOf(messageMapping.text()))
            );
        }

        //todo: message filter
//        if(!isEmpty(messageMapping.filter()){
//            conditionEventMessageHandlerResolver.add(
//                    new ConditionalEventMessageHandlerProxy()
//            );
//        }

        checkArgument(foundResolver, "No message mapping information found in handler[" + handlerMethod + "]");
    }

    private void checkParameters(HandlerMethod handlerMethod) {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        checkNotNull(methodParameters);
        checkArgument(methodParameters.length == 1,
                "Method parameters must be only one in method [" + handlerMethod + "]  found [" + methodParameters.length + "]");
        MethodParameter methodParameter = methodParameters[0];
        checkArgument(UpdateEvent.class.isAssignableFrom(methodParameter.getParameterType()),
                "Method parameter must be UpdateEvent.class, or subclass, received [" + methodParameter.getDeclaringClass() + "]");

        //todo: add void method
        MethodParameter returnType = handlerMethod.getReturnType();
        checkArgument(MessageResponse.class.isAssignableFrom(returnType.getParameterType()),
                "Declared method [" + handlerMethod + "] must return instance or subclass of MessageResponse.class");
    }

    private HandlerMethod createHandlerMethod(Object handler, Method method) {
        HandlerMethod handlerMethod;
        if (handler instanceof String) {
            String beanName = (String) handler;
            handlerMethod = new HandlerMethod(beanName,
                    getApplicationContext().getAutowireCapableBeanFactory(), method).createWithResolvedBean();
        } else {
            handlerMethod = new HandlerMethod(handler, method);
        }
        return handlerMethod;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
