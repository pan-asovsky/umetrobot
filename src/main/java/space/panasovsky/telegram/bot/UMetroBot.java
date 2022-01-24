package space.panasovsky.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import space.panasovsky.telegram.bot.command.HelpCommand;
import space.panasovsky.telegram.bot.command.StartCommand;
import space.panasovsky.telegram.bot.command.TimerCommand;
import space.panasovsky.telegram.bot.util.Timer;


public class UMetroBot extends TelegramLongPollingCommandBot implements Sender {

    private static final Logger LOG = LoggerFactory.getLogger(UMetroBot.class);
    private final String TOKEN;
    private final String USERNAME;

    public UMetroBot(String username, String token) {

        super();
        this.TOKEN = token;
        this.USERNAME = username;

        register(new StartCommand("start", "Начнём!"));
        register(new HelpCommand("help", "Помощь"));
        register(new TimerCommand("timer", "Таймер"));
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        String username;
        Long chatID;

        if (update.getMessage() == null ) {
            LOG.error("Message is null!");
            return;
        }
        if (update.getMessage().getChatId() == null) {
            LOG.error("ChatID is null!");
            return;
        } else {
            chatID = update.getMessage().getChatId();
        }
        if (update.getMessage().getChat().getUserName() == null) {
            LOG.error("Username is null!");
            username = update.getMessage().getChat().getFirstName() + update.getMessage().getChat().getLastName();
        } else {
            username = update.getMessage().getChat().getUserName();
        }

        try {
            LOG.debug("ChatID: {}, username: {}", chatID, username);
        } catch (NullPointerException e) {
            LOG.error("NPE", e);
        }

        if (update.hasCallbackQuery()) {

            final Timer t = new Timer();
            switch (update.getCallbackQuery().getData()) {
                case "start" -> setRequest(chatID, username, t.start());
                case "pause" -> setRequest(chatID, username, t.pause());
                case "stop" -> setRequest(chatID, username, t.stop());
            }
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

    private void setRequest(Long chatID, String username, String text) {

        SendMessage request = new SendMessage();
        request.setText(text);
        request.setChatId(chatID.toString());
        try {
            execute(request);
        } catch (TelegramApiException tae) {
            tae.printStackTrace();
        }
    }
}