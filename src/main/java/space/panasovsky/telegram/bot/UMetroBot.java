package space.panasovsky.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

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

        final String username;
        final Long chatID;

        if (!update.hasCallbackQuery()) {

            if (update.getMessage() == null) {
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
                username = update.getMessage().getChat().getFirstName() + update.getMessage().getChat().getLastName();
            } else {
                username = update.getMessage().getChat().getUserName();
            }

        } else {

            final CallbackQuery callback = update.getCallbackQuery();

            if (callback.getMessage() == null) {
                LOG.error("Callback message is null!");
                return;
            }
            if (callback.getMessage().getChatId() == null) {
                LOG.error("Callback chatID is null!");
                return;
            } else {
                chatID = callback.getMessage().getChatId();
            }
            if (callback.getMessage().getChat().getUserName() == null) {
                username = callback.getMessage().getChat().getFirstName() + callback.getMessage().getChat().getLastName();
            } else {
                username = callback.getMessage().getChat().getUserName();
            }

            final AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(callback.getId());
            final Timer t = new Timer();

            switch (update.getCallbackQuery().getData()) {
                case "start" -> send(chatID, t.timeHandler("start"));
                case "pause" -> send(chatID, t.timeHandler("pause"));
                case "stop" -> send(chatID, t.timeHandler("stop"));
            }

            try {
                execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        try {
            LOG.debug("ChatID: {}, username: {}", chatID, username);
        } catch (NullPointerException e) {
            LOG.error("NPE", e);
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

    private void send(long chatID, String var) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(var);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}