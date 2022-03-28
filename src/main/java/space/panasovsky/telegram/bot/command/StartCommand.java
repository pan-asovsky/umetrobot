package space.panasovsky.telegram.bot.command;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import space.panasovsky.telegram.bot.handler.CommandHandler;


public class StartCommand extends CommandHandler {

    public StartCommand(String command, String description) {
        super(command, description);
    }

    @Override
    public void execute(final AbsSender sender, final User user, final Chat chat, final String[] strings) {

        final String helpMessage = """
                \u27a1Первым шагом необходимо авторизироваться: команда /auth
                \u2705 При успешной авторизации вам будет открыт доступ к чату с ботом.
                \u27a1Отправляйте сообщение типа "кп 000123456"
                \u27a1В ответ бот пришлёт вам заготовленный ответ, счёт в формате PDF и ссылку на оплату.""";

        sendResponse(sender, chat.getId(), helpMessage);
    }

}