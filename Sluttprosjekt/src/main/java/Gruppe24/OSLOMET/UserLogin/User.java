package Gruppe24.OSLOMET.UserLogin;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class User implements Serializable {
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty location;
    private SimpleStringProperty gender;
    private SimpleStringProperty secretquestion;

    public User(String username, String password, String location, String gender, String secretQuestion) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.location = new SimpleStringProperty(location);
        this.gender = new SimpleStringProperty(gender);
        this.secretquestion = new SimpleStringProperty(secretQuestion);
    }

    public String getUsername() {
        return username.getValue();
    }
    public String getPassword() {
        return password.getValue();
    }
    public String getLocation() {
        return location.getValue();
    }
    public String getGender() {
        return gender.getValue();
    }
    public String getSecretquestion() {
        return secretquestion.getValue();
    }

    private void setUsername(String username) {
        this.username.set(username);
    }
    private void setPassword(String password) {
        this.password.set(password);
    }
    private void setLocation(String location) {
        this.location.set(location);
    }
    private void setGender(String gender) {
        this.gender.set(gender);
    }
    private void setSecretquestion(String secretquestion) {
        this.secretquestion.set(secretquestion);
    }
}
