package Gruppe24.OSLOMET.UserLogin;

public class ParseUser {
    public static User parseUser(String user) {
            User newUser = new User("test", "test", "test", "test");
            String[] userData = user.split(";", 4);
            if(userData.length == 4) {
                String username = newUser.getUsername();
                String password = newUser.getPassword();
                String location = newUser.getLocation();
                String gender = newUser.getGender();

                return new User(username, password, location, gender);
            }
            return newUser;
    }
}
