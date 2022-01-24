package space.panasovsky.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Sender {

    default void sendResponse(AbsSender sender, Long chatID, String command, String username, String text) {

        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatID.toString());
        message.setText(text);

        try {
            sender.execute(message);
        } catch (TelegramApiException tae) {
            System.out.println(tae.getMessage());
        }
    }

    default void sendResponse(AbsSender sender, Long chatID, String command, String username, String text, InlineKeyboardMarkup markup) {

        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatID.toString());
        message.setReplyMarkup(markup);
        message.setText(text);

        try {
            sender.execute(message);
        } catch (TelegramApiException tae) {
            System.out.println(tae.getMessage());
        }
    }

}