package ru.skuptsov.telegram.bot.platform.handler.registry.proxy;

import ru.skuptsov.telegram.bot.platform.handler.RegexpMessageTextHandler;

/**
 * @author Sergey Kuptsov
 * @since 24/07/2016
 */
public class RegexpMessageTextHandlerProxy extends BaseMessageHandlerProxy implements RegexpMessageTextHandler {

    private final String messageRegexp;

    public RegexpMessageTextHandlerProxy(HandlerMethod handlerMethod, String messageRegexp) {
        super(handlerMethod);
        this.messageRegexp = messageRegexp;
    }

    @Override
    public String getMessageRegexp() {
        return messageRegexp;
    }
}
