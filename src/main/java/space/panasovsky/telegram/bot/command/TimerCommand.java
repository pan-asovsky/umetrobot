package space.panasovsky.telegram.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;


public class TimerCommand extends CommandHandler  {

    private static final Logger LOG = LoggerFactory.getLogger(TimerCommand.class);

    public TimerCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        LOG.info("Вызван метод команды /timer");

        String username = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s%s", user.getLastName(), user.getFirstName());

        sendResponse(absSender, chat.getId(), this.getCommandIdentifier(), username,
                "А тут будет запускаться таймер!", createInlineButtons());
    }

    @Deprecated
    private ReplyKeyboardMarkup createSimpleButtons() {

        final ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();

        final KeyboardButton startTimer = new KeyboardButton("Запустить таймер");
        final KeyboardButton pauseTimer = new KeyboardButton("Платформа");
        final KeyboardButton stopTimer = new KeyboardButton("Остановка таймера");

        final List<KeyboardButton> buttonsRow = new ArrayList<>(3);
        buttonsRow.add(startTimer);
        buttonsRow.add(pauseTimer);
        buttonsRow.add(stopTimer);

        final KeyboardRow keyboardRow = new KeyboardRow(buttonsRow);
        final List<KeyboardRow> rowList = new ArrayList<>(1);
        rowList.add(keyboardRow);
        replyMarkup.setOneTimeKeyboard(true);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setKeyboard(rowList);

        return replyMarkup;
    }

    private InlineKeyboardMarkup createInlineButtons() {

        final InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();

        final InlineKeyboardButton startTimer = new InlineKeyboardButton("\u23FA");
        startTimer.setCallbackData("start");

        final InlineKeyboardButton pauseTimer = new InlineKeyboardButton("\u23F8");
        pauseTimer.setCallbackData("platform");

        final InlineKeyboardButton stopTimer = new InlineKeyboardButton("\u23F9");
        stopTimer.setCallbackData("stop");

        final List<InlineKeyboardButton> buttonsRow = new ArrayList<>(3);
        buttonsRow.add(startTimer);
        buttonsRow.add(pauseTimer);
        buttonsRow.add(stopTimer);

        final List<List<InlineKeyboardButton>> rowList = new ArrayList<>(1);
        rowList.add(buttonsRow);
        inlineMarkup.setKeyboard(rowList);

        return inlineMarkup;
    }

}