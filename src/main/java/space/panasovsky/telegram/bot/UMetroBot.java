package space.panasovsky.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import space.panasovsky.telegram.bot.command.HelpCommand;
import space.panasovsky.telegram.bot.command.StartCommand;
import space.panasovsky.telegram.bot.data.Users;


public class UMetroBot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LoggerFactory.getLogger(UMetroBot.class);
    private final String TOKEN;
    private final String USERNAME;


    public UMetroBot(final String username, final String token) {

        super();
        this.TOKEN = token;
        this.USERNAME = username;

        register(new StartCommand("start", "Начнём!"));
        register(new HelpCommand("help", "Помощь"));
    }

    @Override
    public void processNonCommandUpdate(final Update update) {

        if (!isCorrectUpdate(update)) return;
        handleUpdate(update);
    }

    private boolean isCorrectUpdate(final Update update) {
        return isCallbackQuery(update)
                ? isCorrectCallbackUpdate(update.getCallbackQuery())
                : isCorrectNoCallbackUpdate(update);
    }

    private boolean isCorrectNoCallbackUpdate(final Update update) {

        try {
            update.getMessage();
        } catch (NullPointerException e) {
            LOG.error("Update doesn't contain a Message.");
            return false;
        }
        try {
            update.getMessage().getChat();
        } catch (NullPointerException e) {
            LOG.error("Message doesn't contain a Chat.");
            return false;
        }
        try {
            update.getMessage().getChatId();
        } catch (NullPointerException e) {
            LOG.error("Message doesn't contain a ChatID.");
            return false;
        }
        LOG.debug("Chat: {}, ChatID: {}", update.getMessage().getChat(), update.getMessage().getChatId());
        return true;
    }

    private boolean isCorrectCallbackUpdate(final CallbackQuery callback) {

        try {
            callback.getMessage();
        } catch (NullPointerException e) {
            LOG.error("CallbackQuery doesn't contain a Message.");
            return false;
        }
        try {
            callback.getMessage().getChat();
        } catch (NullPointerException e) {
            LOG.error("CallbackQuery doesn't contain a Chat.");
            return false;
        }
        try {
            callback.getId();
        } catch (NullPointerException e) {
            LOG.error("CallbackQuery doesn't contain a ID.");
            return false;
        }
        try {
            callback.getData();
        } catch (NullPointerException e) {
            LOG.error("CallbackQuery doesn't contain a Data.");
            return false;
        }
        LOG.debug("Chat: {}, ChatID: {}", callback.getMessage().getChat(), callback.getMessage().getChatId());
        return true;
    }


    private boolean isAuthorization(final Update update) {
        return update.getMessage().getContact() != null;
    }

    private boolean isCallbackQuery(final Update update) {
        return update.hasCallbackQuery();
    }


    private void handleUpdate(final Update update) {

        if (isCallbackQuery(update)) handleCallbackQuery(update.getCallbackQuery());
        else handleNoCallbackQuery(update);
    }

    private void handleCallbackQuery(final CallbackQuery callback) {

        final String callbackID = callback.getId();
        final AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackID);

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            LOG.error("Error at execute Answer.");
        }

    }

    private void handleNoCallbackQuery(final Update update) {

        final Users users = new Users();
        final String chatID = String.valueOf(update.getMessage().getChatId());

        if (isAuthorization(update)) processAuthorization(update.getMessage(), chatID, users);
        else {
            if (users.checkAuthorization(chatID)) LOG.info("ChatID: {}", chatID);
            else send(chatID, "Вам необходимо авторизироваться. \n/start");
        }
    }

    private void processAuthorization(final Message message, final String chatID, final Users users) {

        final String phoneNumber = getPhoneNumber(message);
        if (!users.writeUserToDatabase(chatID, phoneNumber)) {
            LOG.error("Error at writeUserToDatabase. Phone: {}", phoneNumber);
        }
        send(chatID, announceSuccessfulAuthorization(message));
    }


    private void send(final String chatID, final String msg) {

        final SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(msg);
        message.setReplyMarkup(new ReplyKeyboardRemove(true));

        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error("Error at execute Message");
        }
    }

    private String announceSuccessfulAuthorization(final Message message) {

        final String username = getUsername(message);
        final String phoneNumber = getPhoneNumber(message);
        return username + ", авторизация прошла успешно! Ваш номер телефона: " + phoneNumber;

    }

    private String getUsername(final Message message) {

        if (message.getContact() != null) {
            return message.getContact().getFirstName() + " " + message.getContact().getLastName();
        }
        if (message.getChat().getUserName() == null) {
            return message.getChat().getFirstName() + " " + message.getChat().getLastName();
        }
        return message.getChat().getUserName();
    }

    private String getPhoneNumber(final Message message) {
        return message.getContact().getPhoneNumber();
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