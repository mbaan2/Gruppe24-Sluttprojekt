package Gruppe24.OSLOMET.UserLogin;

public class ParseUser {
    public static User parseUser(String user) {
            User newUser = new User("test", "test");
            String[] userData = user.split(";", 2);
            if(userData.length == 2) {
                String username = newUser.getUsername();
                String password = newUser.getPassword();

                return new User(username, password);
            }
            return newUser;
    }
}
