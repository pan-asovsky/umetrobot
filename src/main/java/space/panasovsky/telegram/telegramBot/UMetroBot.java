package space.panasovsky.telegram.telegramBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import space.panasovsky.telegram.telegramBot.command.HelpCommand;
import space.panasovsky.telegram.telegramBot.command.StartCommand;
import space.panasovsky.telegram.telegramBot.command.TimerCommand;


public class UMetroBot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LoggerFactory.getLogger(UMetroBot.class);
    private final String TOKEN;
    private final String USERNAME;

    public UMetroBot(String username, String token) {

        super();
        this.TOKEN = token;
        this.USERNAME = username;

        register(new StartCommand("start", "Начнём!"));
        register(new HelpCommand("help", "Помощь"));
        register(new TimerCommand("timer", "Запуск таймера"));
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        LOG.debug("ChatID: {}, username: {}", update.getMessage().getChatId(),
                update.getMessage().getChat().getUserName());

        if (update.getMessage() == null ) {
            LOG.error("Message is null!");
            return;
        }
        if (update.getMessage().getChatId() == null) {
            LOG.error("ChatID is null!");
            return;
        }
        if (update.getMessage().getChat().getUserName() == null) {
            LOG.error("Username is null!");
        }
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