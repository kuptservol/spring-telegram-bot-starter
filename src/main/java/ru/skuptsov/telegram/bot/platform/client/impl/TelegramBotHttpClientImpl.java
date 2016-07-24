package ru.skuptsov.telegram.bot.platform.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotHttpClient;
import ru.skuptsov.telegram.bot.platform.client.exception.TelegramBotApiException;
import ru.skuptsov.telegram.bot.platform.model.ExecutionResult;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

import static java.util.Optional.ofNullable;

/**
 * @author Sergey Kuptsov
 * @since 22/05/2016
 */
public class TelegramBotHttpClientImpl implements TelegramBotHttpClient {
    private static final Logger log = LoggerFactory.getLogger(TelegramBotHttpClientImpl.class);
    private final static String BOT_PREFIX = "/bot";

    private final ObjectMapper jsonMapper;
    private final AsyncHttpClient httpClient;
    private final String apiToken;
    private final String baseUrl;
    private final String apiUrl;

    public TelegramBotHttpClientImpl(
            ObjectMapper jsonMapper,
            AsyncHttpClient httpClient,
            String apiToken,
            String baseUrl) {
        this.jsonMapper = jsonMapper;
        this.httpClient = httpClient;
        this.apiToken = apiToken;
        this.baseUrl = baseUrl;
        this.apiUrl = formBaseApiUrl(apiToken, baseUrl);
    }

    @Override
    public <T> Future<T> executeGet(@NotNull String method,
                                    @Nullable Map<String, String> params,
                                    @NotNull JavaType returnType) throws TelegramBotApiException {

        AsyncHttpClient.BoundRequestBuilder requestBuilder =
                httpClient.prepareGet(formUrl(method));

        setParams(params, requestBuilder);

        return execute(requestBuilder.build(), returnType);
    }

    @Override
    public <T, V> Future<T> executePost(
            @NotNull String method,
            @Nullable V requestObject,
            @NotNull JavaType returnType) throws TelegramBotApiException {

        AsyncHttpClient.BoundRequestBuilder requestBuilder;

        requestBuilder = httpClient.preparePost(formUrl(method));
        requestBuilder.setHeader("Content-Type", "application/json");

        setBody(requestObject, requestBuilder);

        return execute(requestBuilder.build(), returnType);
    }

    private <T> void setBody(@Nullable T requestObject, AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        if (requestObject != null) {
            try {
                requestBuilder.setBody(jsonMapper.writeValueAsBytes(requestObject));
            } catch (JsonProcessingException e) {
                throw new TelegramBotApiException("Error while serializing request object " + requestObject, e);
            }
        }
    }

    private String formBaseApiUrl(String apiToken, String baseUrl) {
        return baseUrl + BOT_PREFIX + apiToken;
    }

    private String formUrl(@NotNull String method) {
        return apiUrl + "/" + method;
    }

    private void setParams(@Nullable Map<String, String> params, AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        ofNullable(params)
                .ifPresent(parameters ->
                        parameters.entrySet().stream()
                                .forEach((param -> requestBuilder.addQueryParam(param.getKey(), param.getValue()))));
    }

    private <T> Future<T> execute(Request httpRequest, JavaType returnType)
            throws TelegramBotApiException {

        log.debug("Executing request : {}", httpRequest);

        return httpClient.executeRequest(httpRequest, createCompletionHandler(returnType));
    }

    private <T> AsyncCompletionHandler<T> createCompletionHandler(JavaType returnType) {
        return new AsyncCompletionHandler<T>() {
            @Override
            public T onCompleted(Response httpResponse) throws Exception {
                log.debug("Got response : {}", httpResponse);

                try {
                    log.debug("Got response : {}", httpResponse.getResponseBody());
                } catch (IOException e) {
                    log.error("Error while getting response body", e);
                }

                checkHttpStatuses(httpResponse);

                ExecutionResult<T> executionResult;
                try {
                    executionResult = jsonMapper.readValue(
                            httpResponse.getResponseBodyAsBytes(),
                            jsonMapper.getTypeFactory().constructParametrizedType(
                                    ExecutionResult.class,
                                    ExecutionResult.class,
                                    returnType));
                } catch (IOException e) {
                    throw new TelegramBotApiException("Error while deserializing request object from response", e);
                }

                if (executionResult == null) {
                    throw new TelegramBotApiException("Response has empty result with return value");
                }

                log.debug("Got execution result : {}", executionResult);

                checkExecutionResult(executionResult);

                return executionResult.getResult();
            }

            @Override
            public void onThrowable(Throwable t) {
                log.error("Got exception while sending message {}", t);
            }
        };
    }

    private <T> void checkExecutionResult(ExecutionResult<T> executionResult) {
        if (!executionResult.isOk()) {
            throw new TelegramBotApiException("Request not succeeded with code " + executionResult.getError_code());
        }
    }

    private void checkHttpStatuses(Response httpResponse) throws TelegramBotApiException {
        if (httpResponse.getStatusCode() != HttpStatus.SC_OK) {

            throw new TelegramBotApiException("Error while requesting url: " + httpResponse.getUri() +
                    " response code: " + httpResponse.getStatusCode() + " reason: " + httpResponse.getStatusText());
        }
    }
}
