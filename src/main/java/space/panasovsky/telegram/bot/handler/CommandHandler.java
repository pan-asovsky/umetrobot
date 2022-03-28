package space.panasovsky.telegram.bot.handler;

import space.panasovsky.telegram.bot.Sender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;


public abstract class CommandHandler extends BotCommand implements Sender {

    public CommandHandler(String command, String description) {
        super(command, description);
    }

}