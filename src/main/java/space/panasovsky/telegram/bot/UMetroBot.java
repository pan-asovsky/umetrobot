package space.panasovsky.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
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
                LOG.error("Username is null!");
                username = update.getMessage().getChat().getFirstName() + update.getMessage().getChat().getLastName();
            } else {
                username = update.getMessage().getChat().getUserName();
            }

        } else {

            if (update.getCallbackQuery().getMessage().getChat().getUserName() == null) {
                LOG.error("Username is null!");
                username = update.getMessage().getChat().getFirstName() + update.getMessage().getChat().getLastName();
            } else {
                username = update.getCallbackQuery().getMessage().getChat().getUserName();
            }

            chatID = update.getCallbackQuery().getMessage().getChatId();
            final Timer t = new Timer();

            final AnswerCallbackQuery answer = new AnswerCallbackQuery();

            switch (update.getCallbackQuery().getData()) {

                case "start" -> {
                    answer.setCallbackQueryId(update.getCallbackQuery().getId());
                    answer.setText(t.start());
                    setResponse(chatID, answer);
                }
                case "pause" -> {
                    answer.setCallbackQueryId(update.getCallbackQuery().getId());
                    answer.setText(t.pause());
                    setResponse(chatID, answer);
                }
                case "stop" -> {
                    answer.setCallbackQueryId(update.getCallbackQuery().getId());
                    answer.setText(t.stop());
                    setResponse(chatID, answer);
                }
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

    private void setResponse(Long chatID, AnswerCallbackQuery callbackQuery) {

        SendMessage request = new SendMessage();
        request.setText(callbackQuery.getText());
        request.setChatId(chatID.toString());
        try {
            execute(request);
        } catch (TelegramApiException tae) {
            tae.printStackTrace();
        }
    }

}