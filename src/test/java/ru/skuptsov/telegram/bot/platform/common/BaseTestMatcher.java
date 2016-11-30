package ru.skuptsov.telegram.bot.platform.common;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author Sergey Kuptsov
 * @since 04/02/2016
 */
public abstract class BaseTestMatcher<T> extends BaseMatcher<T> {

    protected T expected;

    public BaseTestMatcher(T object) {
        this.expected = object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object o) {

        if (o == expected) {
            return true;
        }

        if (o.getClass() == expected.getClass()) {

            T request = (T) o;

            return isEqual(request);
        }

        return false;

    }

    protected abstract boolean isEqual(T that);

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }
}
