package space.panasovsky.telegram.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;


public class ApplicationBoot {

    private static final Map<String, String> env = System.getenv();

    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new UMetroBot(env.get("BOT_NAME"), env.get("BOT_TOKEN")));
        } catch (TelegramApiException tae) {
            tae.printStackTrace();
        }
    }

}