package ru.skuptsov.telegram.bot.platform.handler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sergey Kuptsov
 * @since 19/07/2016
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageMapping {

    String regexp() default "";

    String[] text() default {};

    String[] callback() default {};

    //todo: message filter
//    String filter default "";
}
