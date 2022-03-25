package space.panasovsky.telegram.bot;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;


public interface Sender {

    default void sendResponse(final AbsSender sender, final Long chatID, final String text) {

        final SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatID.toString());
        message.setText(text);

        try {
            sender.execute(message);
        } catch (TelegramApiException tae) {
            System.out.println(tae.getMessage());
        }
    }

    default void sendResponse(final AbsSender sender, final Long chatID, final String text, final ReplyKeyboard keyboard) {

        final SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatID.toString());
        message.setText(text);
        message.setReplyMarkup(keyboard);

        try {
            sender.execute(message);
        } catch (TelegramApiException tae) {
            System.out.println(tae.getMessage());
        }
    }

}