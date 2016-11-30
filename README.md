# spring-telegram-bot-starter

[![Release](https://jitpack.io/v/kuptservol/spring-telegram-bot-starter.svg)](https://jitpack.io/#kuptservol/spring-telegram-bot-starter)

Library for fast java telegram bot development powered by spring-boot

# Starting example
- Import library from https://jitpack.io/#kuptservol/spring-telegram-bot-starter/v1.0
- Create configuration file to hold telegram client token. The only needed at start in configuration is key "telegram.client.token".
Create folder config, create file application.yml in it - add line 
```
telegram.client.token: {set your client token received from https://telegram.me/botfather}
```
For more about spring boot external config options : 
http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files.
- Create your first bot as spring bean
```
package example.bot;

@MessageHandler
public class SimpleHelloBot {

    @MessageMapping(text = "Hi")
    public MessageResponse sayHi(UpdateEvent updateEvent) {
        return sendMessage("Hi there!", updateEvent)
                .setCallback((Consumer<Message>) message -> System.out.println("Message sent"));
    }
}
```
- Start your bot with configuration that knows about SimpleHelloBot bean
```
@Configuration
@ComponentScan(value = "example.bot")
public class ExampleBotStarter {
    public static void main(String[] args) {
        BotPlatformStarter.start(ExampleBotStarter.class, args);
    }
}
```
The service is now long pooling for new messages in chat and routes it to message handlers according your's @MessageHandler configurations.

Full example code you can find here https://github.com/kuptservol/telegram-spring-bot-example
# Message handling options
## Annotation based

In order to handle messages you need to mark bean as **@MessageHandler** - this will indicate to system to look for message mappings @MessageMapping. Message handler method, annotated as **@MessageMapping** should receive as method parameter **ru.skuptsov.telegram.bot.platform.model.UpdateEvent** and return **ru.skuptsov.telegram.bot.platform.client.command.MessageResponse**
```
@MessageMapping(text = "Hi")
    public MessageResponse sayHi(UpdateEvent updateEvent){
        return sendMessage("Hi there!", updateEvent)
        }
```

**UpdateEvent** has original Update object received from Telegram.

**MessageResponse** is a response command wrapper that holds an answer to chat event and sent to telegram asynchronously, cause in most cases you don't need to wait for response message actually have been send increasing thoughput.
But if you want to make some action after message was delivered - you can set some callback action.
```
@MessageMapping(text = "Hi")
    public MessageResponse sayHi(UpdateEvent updateEvent){
        return sendMessage("Hi there!", updateEvent)
                .setCallback((Consumer<Message>) message -> System.out.println("Message sent"));
    }
```

In @MessageMapping you can handle messages to react on
- certain set of texts in message
- message text regexp match
- certain set of callback query data from users input

Annotation based example could be found here https://github.com/kuptservol/telegram-spring-bot-example/blob/master/src/main/java/skuptsov/example/bot/impl/SimpleHelloBot.java

## Interface-type based

If you message mapping data is dynamic and you cannot use it in annotations you can implement one of interfaces 
- MessageTextMessageHandler
- ConditionMessageHandler
- RegexpMessageTextHandler
- CallbackQueryDataMessageHandler
with spring bean in beans scope.

```
@Component
public class SimpleGoodbyeBot implements MessageTextMessageHandler {

    @Override
    public Set<String> getMessageText() {
        return of("Bye", "GoodBye");
    }

    @Override
    public MessageResponse handle(UpdateEvent updateEvent) {
        return sendMessage("Goodbye! See you soon!", updateEvent);
    }
}
```

Typed based example could be found here https://github.com/kuptservol/telegram-spring-bot-example/blob/master/src/main/java/skuptsov/example/bot/impl/SimpleGoodbyeBot.java
## Use api directly

In some situations you want to use api directly to get some additional information - you can inject **ru.skuptsov.telegram.bot.platform.client.TelegramBotApi** bean directly and call api methods.

```
@MessageHandler
public class SimpleCallApiBot {

    @Autowired
    private TelegramBotApi telegramBotApi;

    @MessageMapping(text = "/me")
    public MessageResponse getMe(UpdateEvent updateEvent) {
        User me = telegramBotApi.getMe().get();

        return sendMessage("Me is : " + me.getFirstName() + " " + me.getLastName(), updateEvent);
    }
}
```
Direct api call example could be found here https://github.com/kuptservol/telegram-spring-bot-example/blob/master/src/main/java/skuptsov/example/bot/impl/SimpleCallApiBot.java

# Extenalization points
- Default message handler

If you want to specify default message handling when no one suitable mapper found for current message you can create bean that implements **ru.skuptsov.telegram.bot.platform.handler.DefaultMessageHandler**. By default it writes info message to logs.

- Next message id store

Telegram uses incrementing ids in messages and every pooling should request message with id > last received. By default application starts with id = 0, telegram sends only new messages and app holds it in app memory, after app restart old messages are not received. To handle this differently implement bean with interface **ru.skuptsov.telegram.bot.platform.client.NextOffsetStrategy**

- Updates queue 

By default new unprocessed messages are stored locally in BlockingQueue and processed with thread pool reactivelly in order to prevent resource contention under high load. To change this behaviour, to perisist messages for example implement bean with interface **ru.skuptsov.telegram.bot.platform.worker.saver.UpdatesWorkerRepository**

# Configurations 

Configuration options can be found in ru.skuptsov.telegram.bot.platform.config package classes with fields, annotated as @Value. You can modify default configurations setting it in application.yml.

# Monitoring

Platform exposes application metrics via jmx.metrics:

- **bot.api.client.process.error** number of errors in processing
- **bot.api.client.message.received** number of message received overall
- **bot.message.processing.{$processocClassName.methodName}.exception** number of errors in client message processor
- **bot.message.processing.{$processocClassName.methodName}.execution.time** execution time histogram in client message processor
- **bot.message.processing.{$processocClassName.methodName}.success** number of successfuly processed in client message processor
- **bot.message.processor.not.found** number of messages that haven't been processed due to lack of processor routing
- **bot.updates.worker.repository.get** message repository get execution time
- **bot.updates.worker.repository.save** message repository save execution time

