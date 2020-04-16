package Gruppe24.OSLOMET.UserLogin;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class User implements Serializable {
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty location;
    private SimpleStringProperty gender;
    private SimpleStringProperty secretQ;
    private SimpleStringProperty secretQAnswer;

    public User(String username, String password, String location, String gender, String secretQ, String secretQuestion) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.location = new SimpleStringProperty(location);
        this.gender = new SimpleStringProperty(gender);
        this.secretQ = new SimpleStringProperty(secretQ);
        this.secretQAnswer = new SimpleStringProperty(secretQuestion);
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
    public String getSecretQ() {
        return secretQ.getValue();
    }
    public String getSecretQAnswer() {
        return secretQAnswer.getValue();
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
    private void setSecretQ(String secretQ) {
        this.secretQ.set(secretQ);
    }
    private void setSecretQAnswer(String secretQAnswer) {
        this.secretQAnswer.set(secretQAnswer);
    }
}
