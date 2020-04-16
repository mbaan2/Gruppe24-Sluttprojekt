package Gruppe24.OSLOMET.UserLogin;

public class ParseUser {
    public static User parseUser(String user) {
        User newUser = new User("test", "test", "test", "test", "test", "test");
        String[] userData = user.split(";", 6);
        if(userData.length == 6) {
            String username = userData[0];
            String password = userData[1];
            String location = userData[2];
            String gender = userData[3];
            String secretQuestion = userData[4];
            String secretQAnswer = userData[5];

            return new User(username, password, location, gender, secretQuestion, secretQAnswer);
        }
        return newUser;
    }
}
