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

                userData[0] = username;
                userData[1] = password;
                userData[2] = location;
                userData[3] = gender;
            }
            return newUser;
    }
}
