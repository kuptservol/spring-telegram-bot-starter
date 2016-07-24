package ru.skuptsov.telegram.bot.platform.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
@Getter
@Setter
@ToString
public class ExecutionResult<T> {

    private boolean ok;
    private T result;
    private String description;
    private Integer error_code;
}
