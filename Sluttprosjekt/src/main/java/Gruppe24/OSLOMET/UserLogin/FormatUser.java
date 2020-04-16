package Gruppe24.OSLOMET.UserLogin;

import java.util.List;

public class FormatUser {
    public static String DELIMITER = ";";

    public static String formatUser(User user) {
        return user.getUsername() + DELIMITER + user.getPassword() + DELIMITER + user.getLocation() + DELIMITER + user.getGender() + DELIMITER + user.getSecretQ() + DELIMITER + user.getSecretQAnswer();
    }

    public static String formatUsers(List<User> userList) {
        StringBuffer str = new StringBuffer();
        for(User p : userList) {
            str.append(formatUser(p));
            str.append("\n");
        }
        return str.toString();
    }
}
