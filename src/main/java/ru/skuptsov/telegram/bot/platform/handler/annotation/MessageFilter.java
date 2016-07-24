package ru.skuptsov.telegram.bot.platform.handler.annotation;

import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;

import java.util.function.Predicate;

/**
 * @author Sergey Kuptsov
 * @since 06/06/2016
 */
public interface MessageFilter extends Predicate<UpdateEvent> {
}
