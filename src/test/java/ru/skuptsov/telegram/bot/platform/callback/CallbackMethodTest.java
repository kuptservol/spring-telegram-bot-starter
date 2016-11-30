package ru.skuptsov.telegram.bot.platform.callback;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import org.joda.time.DateTime;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.skuptsov.telegram.bot.platform.CommonTestConfiguration;
import ru.skuptsov.telegram.bot.platform.client.TelegramBotApi;
import ru.skuptsov.telegram.bot.platform.common.BaseTestMatcher;
import ru.skuptsov.telegram.bot.platform.config.BotPlatformConfiguration;
import ru.skuptsov.telegram.bot.platform.handler.regexp.RegexpMessageHandler;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvent;
import ru.skuptsov.telegram.bot.platform.model.UpdateEvents;
import ru.skuptsov.telegram.bot.platform.repository.UpdatesRepository;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.joda.time.DateTimeZone.UTC;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.skuptsov.telegram.bot.platform.CommonTestConfiguration.CHAT_ID;
import static ru.skuptsov.telegram.bot.platform.handler.regexp.RegexpMessageHandler.MESSAGE_ANSWER;

/**
 * @author Sergey Kuptsov
 * @since 27/11/2016
 */
@ActiveProfiles({"test"})
@ContextConfiguration(
        classes = {
                BotPlatformConfiguration.class,
                CommonTestConfiguration.class,
                RegexpMessageHandlerWithCallback.class,
        },
        loader = SpringockitoAnnotatedContextLoader.class
)
public class CallbackMethodTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @ReplaceWithMock
    private UpdatesRepository updatesRepository;

    @Autowired
    private EventBus eventBus;

    @Autowired
    @Qualifier("commandSenderBotApi")
    @ReplaceWithMock
    private TelegramBotApi telegramBotApi;

    @Autowired
    @ReplaceWithMock
    private CallbackMethod callbackMethod;

    @BeforeMethod
    public void before() {
        Mockito.reset(telegramBotApi);
    }

    @Test
    public void sendTextCallbackExecuted() throws InterruptedException, ExecutionException, TimeoutException {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        when(message.getText()).thenReturn("hi, there!");
        when(message.getChatId()).thenReturn(CHAT_ID);
        when(update.getMessage()).thenReturn(message);

        UpdateEvent updateEvent = new UpdateEvent(update, DateTime.now(UTC));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(CHAT_ID.toString());
        sendMessage.setText(MESSAGE_ANSWER);


        Future<Message> future = Mockito.mock(Future.class);
        Message returnMessage = Mockito.mock(Message.class);
        when(returnMessage.getChatId()).thenReturn(CHAT_ID);

        when(future.get()).thenReturn(returnMessage);
        when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(returnMessage);

        when(telegramBotApi.sendMessageAsync(argThat(new SendMessageObjectMatcher(sendMessage))))
                .thenReturn(future);

        eventBus.post(UpdateEvents.builder()
                .updateEventList(ImmutableList.of(updateEvent))
                .build());

        //await termination
        Thread.sleep(1000);

        verify(callbackMethod).run(argThat(new MessageObjectMatcher(returnMessage)));
    }

    private static class MessageObjectMatcher extends BaseTestMatcher<Message> {

        public MessageObjectMatcher(Message object) {
            super(object);
        }

        @Override
        protected boolean isEqual(Message that) {
            return Objects.equals(that.getChatId(), expected.getChatId());
        }
    }

    private static class SendMessageObjectMatcher extends BaseTestMatcher<SendMessage> {

        public SendMessageObjectMatcher(SendMessage object) {
            super(object);
        }

        @Override
        protected boolean isEqual(SendMessage that) {
            return Objects.equals(that.getPath(), expected.getPath()) &&
                    Objects.equals(that.getText(), expected.getText()) &&
                    Objects.equals(that.getChatId(), expected.getChatId());
        }
    }
}
