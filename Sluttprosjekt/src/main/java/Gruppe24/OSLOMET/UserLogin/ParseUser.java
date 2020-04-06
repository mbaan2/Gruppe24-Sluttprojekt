package Gruppe24.OSLOMET.UserLogin;

public class ParseUser {
    public static User parseUser(String user) {
            User newUser = new User("test", "test", "test", "test", "test");
            String[] userData = user.split(";", 5);
            if(userData.length == 5) {
                String username = newUser.getUsername();
                String password = newUser.getPassword();
                String location = newUser.getLocation();
                String gender = newUser.getGender();
                String secretquestion = newUser.getSecretquestion();

                return new User(username, password, location, gender, secretquestion);
            }
            return newUser;
    }
}
