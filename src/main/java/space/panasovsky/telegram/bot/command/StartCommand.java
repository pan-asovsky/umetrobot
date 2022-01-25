package space.panasovsky.telegram.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class StartCommand extends CommandHandler {

    private static final Logger LOG = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(AbsSender sender, User user, Chat chat, String[] strings) {

        LOG.info("Вызван метод команды /start");

        String username = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s%s", user.getLastName(), user.getFirstName());

        sendResponse(sender, chat.getId(), this.getCommandIdentifier(), username,
                "Это стартовое сообщение. Для помощи введите /help");
    }

}