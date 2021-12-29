package space.panasovsky.telegram.telegramBot.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


abstract class CommandHandler extends BotCommand {

    public CommandHandler(String command, String description) {
        super(command, description);
    }

    void sendResponse(AbsSender sender, Long chatID, String command, String username, String text) {

        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatID.toString());
        message.setText(text);

        try {
            sender.execute(message);
        } catch (TelegramApiException tae) {
            System.out.println(tae.toString());
        }

    }


}