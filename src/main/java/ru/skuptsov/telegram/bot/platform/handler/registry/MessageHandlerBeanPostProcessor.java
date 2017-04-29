package ru.skuptsov.telegram.bot.platform.handler.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageHandler;
import ru.skuptsov.telegram.bot.platform.handler.annotation.MessageMapping;

import java.lang.reflect.Method;
import java.util.Set;

import static org.springframework.beans.factory.BeanFactoryUtils.beanNamesForTypeIncludingAncestors;
import static org.springframework.core.MethodIntrospector.selectMethods;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @author Sergey Kuptsov
 * @since 20/07/2016
 */
@Component
public class MessageHandlerBeanPostProcessor implements InitializingBean, Ordered, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(MessageHandlerBeanPostProcessor.class);
    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";

    private ApplicationContext applicationContext = null;

    @Autowired
    private MappingRegistry mappingRegistry;

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethods();
    }

    protected void initHandlerMethods() {
        if (log.isDebugEnabled()) {
            log.debug("Looking for request mappings in application context: " + getApplicationContext());
        }
        String[] beanNames = beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class);

        for (String beanName : beanNames) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
                Class<?> beanType = null;
                try {
                    beanType = getApplicationContext().getType(beanName);
                } catch (Throwable ex) {
                    // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                    if (log.isDebugEnabled()) {
                        log.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
                    }
                }
                if (beanType != null && isHandler(beanType)) {
                    detectHandlerMethods(beanName);
                }
            }
        }
    }

    protected void detectHandlerMethods(final Object handler) {
        Class<?> handlerType = (handler instanceof String ?
                getApplicationContext().getType((String) handler) : handler.getClass());
        final Class<?> userType = ClassUtils.getUserClass(handlerType);

        Set<Method> methods = selectMethods(userType, new ReflectionUtils.MethodFilter() {
            @Override
            public boolean matches(Method method) {
                return findAnnotation(method, MessageMapping.class) != null;
            }
        });

        if (log.isDebugEnabled()) {
            log.debug(methods.size() + " request handler methods found on " + userType + ": " + methods);
        }
        for (Method method : methods) {
            registerHandlerMethod(handler, method);
        }
    }

    protected void registerHandlerMethod(Object handler, Method method) {
        mappingRegistry.register(handler, method);
    }

    protected boolean isHandler(Class<?> beanType) {
        return findAnnotation(beanType, MessageHandler.class) != null;
    }

    private ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
