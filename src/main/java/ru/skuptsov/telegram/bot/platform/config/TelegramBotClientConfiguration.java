package ru.skuptsov.telegram.bot.platform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotHttpClient;
import ru.skuptsov.telegram.bot.platform.client.impl.TelegramBotHttpClientImpl;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
@Configuration
public class TelegramBotClientConfiguration {

    @Value("${telegram.client.token}")
    private String clientToken;

    @Value("${telegram.client.baseUrl:https://api.telegram.org}")
    private String baseUrl;

    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public TelegramBotHttpClient createTelegramBotClient(AsyncHttpClient asyncHttpClient) {
        return new TelegramBotHttpClientImpl(
                getObjectMapper(),
                asyncHttpClient,
                clientToken,
                baseUrl);
    }
}
