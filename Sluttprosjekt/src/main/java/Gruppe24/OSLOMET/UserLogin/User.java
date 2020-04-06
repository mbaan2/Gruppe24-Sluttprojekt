package Gruppe24.OSLOMET.UserLogin;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class User implements Serializable {
    private SimpleStringProperty username;
    private SimpleStringProperty password;


    public User(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);

    }

    public String getUsername() {
        return username.getValue();
    }
    public String getPassword() {
        return password.getValue();
    }

    private void setUsername(String username) {
        this.username.set(username);
    }
    private void setPassword(String password) {
        this.password.set(password);
    }
}
