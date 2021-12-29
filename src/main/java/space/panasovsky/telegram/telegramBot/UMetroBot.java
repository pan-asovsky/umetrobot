package space.panasovsky.telegram.telegramBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class UMetroBot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LoggerFactory.getLogger(UMetroBot.class);
    private final String TOKEN;
    private final String USERNAME;

    public UMetroBot(String username, String token) {

        this.TOKEN = token;
        this.USERNAME = username;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        final Message message = update.getMessage();
        final Long chatID = message.getChatId();
        final String username = message.getChat().getUserName();
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

}
