package space.panasovsky.telegram.bot.data;

import java.util.HashMap;
import java.util.Map;


public class Users {

    private static final Map<String, String> authorizedUsers = new HashMap<>();

    public boolean checkAuthorization(final String chatID) {
        return authorizedUsers.containsKey(chatID);
    }

    public String getUser(final String chatID) {
        return authorizedUsers.getOrDefault(chatID, "This user doesn't exits");
    }

    public boolean writeUserToDatabase(final String chatID, final String phoneNumber) {

        if (!isValid(phoneNumber)) return false;
        if (!authorizedUsers.containsKey(chatID)) authorizedUsers.put(chatID, phoneNumber);
        return true;
    }

    private boolean isValid(final String phoneNumber) {
        return phoneNumber.startsWith("+") && phoneNumber.length() == 12 ||
                phoneNumber.startsWith("7") && phoneNumber.length() == 11;
    }

}