package space.panasovsky.rest.telegramBot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

public class QuintessenceBot {

    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

    public void serve() {

        bot.setUpdatesListener(updates -> {
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(Update update) {

        final Message message = update.message();
        final CallbackQuery callbackQuery = update.callbackQuery();
        final InlineQuery inlineQuery = update.inlineQuery();
        BaseRequest request = null;

        if (inlineQuery != null) {
            InlineQueryResultArticle gomer = getArticle("gom", "Гомер", "Гомер", "0");
            InlineQueryResultArticle fukidid = getArticle("fuk", "Фукидид", "Фукидид", "1");
            InlineQueryResultArticle titLiviy = getArticle("tit", "Тит Ливий", "Тит Ливий", "2");

            request = new AnswerInlineQuery(inlineQuery.id(), gomer, fukidid, titLiviy);
        }
        else if (message != null) {
            final long chatID = message.chat().id();
            request = new SendMessage(chatID, "История любит повторяться. Фукидид.");
        }

        if (request != null) {
            bot.execute(request);
        }
    }

    private InlineQueryResultArticle getArticle(String id, String title, String author, String callbackData) {

        return new InlineQueryResultArticle(id, title, "")
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                new InlineKeyboardButton(author).callbackData(callbackData)
                        )
                );
    }

}
