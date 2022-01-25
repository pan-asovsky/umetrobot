package space.panasovsky.telegram.bot.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import space.panasovsky.telegram.bot.Sender;


abstract class CommandHandler extends BotCommand implements Sender {

    public CommandHandler(String command, String description) {
        super(command, description);
    }

}