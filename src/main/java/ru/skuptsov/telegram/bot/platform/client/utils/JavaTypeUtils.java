package ru.skuptsov.telegram.bot.platform.client.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 21/01/2016
 */
public class JavaTypeUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static JavaType listTypeOf(Class<?> parameterClass) {
        return getTypeFactory().constructCollectionType(List.class, parameterClass);
    }

    public static JavaType simpleTypeOf(Class<?> parameterClass) {
        return getTypeFactory().constructType(parameterClass);
    }

    public static JavaType mapTypeOf(Class<?> keyClass, Class<?> valueClass) {
        return getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
    }

    private static TypeFactory getTypeFactory() {
        return mapper.getTypeFactory();
    }
}
