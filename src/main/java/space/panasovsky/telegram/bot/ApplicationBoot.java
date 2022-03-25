package space.panasovsky.telegram.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;


public class ApplicationBoot {

    private static final Map<String, String> env = System.getenv();

    public static void main(String[] args) {

        try {
            final TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new UMetroBot(env.get("NAME"), env.get("TOKEN")));
        } catch (TelegramApiException tae) {
            tae.printStackTrace();
        }
    }

}