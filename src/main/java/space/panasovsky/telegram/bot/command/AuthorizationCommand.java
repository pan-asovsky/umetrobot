package space.panasovsky.telegram.bot.command;

import space.panasovsky.telegram.bot.handler.CommandHandler;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthorizationCommand extends CommandHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCommand.class);

    public AuthorizationCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(final AbsSender sender, final User user, final Chat chat, final String[] strings) {

        LOG.debug("Authorization. User: {}, Chat: {}", user.getId(), chat.getId());
        sendResponse(sender, chat.getId(), "Для продолжения необходимо авторизироваться. " +
                "Разрешите боту доступ к вашему номеру телефона.", getAuthorizationButton());
    }

    private ReplyKeyboardMarkup getAuthorizationButton() {

        final ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();

        final KeyboardButton auth = new KeyboardButton("Авторизироваться");
        auth.setRequestContact(true);

        final List<KeyboardButton> buttonsRow = new ArrayList<>(3);
        buttonsRow.add(auth);

        final KeyboardRow keyboardRow = new KeyboardRow(buttonsRow);
        final List<KeyboardRow> rowList = new ArrayList<>(1);
        rowList.add(keyboardRow);
        replyMarkup.setOneTimeKeyboard(true);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setKeyboard(rowList);

        return replyMarkup;
    }

}