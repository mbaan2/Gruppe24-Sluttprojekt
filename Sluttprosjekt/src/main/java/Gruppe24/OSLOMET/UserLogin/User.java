package Gruppe24.OSLOMET.UserLogin;

import java.io.Serializable;

public class User implements Serializable {
    final static long serialVersionUID = 1;

    private String username;
    private String password;
    private String location;
    private String gender;
    private String secretQ;
    private String secretQAnswer;

    public User(String username, String password, String location, String gender, String secretQ, String secretQuestion) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.gender = gender;
        this.secretQ = secretQ;
        this.secretQAnswer = secretQuestion;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getLocation() {
        return location;
    }
    public String getGender() {
        return gender;
    }
    public String getSecretQ() {
        return secretQ;
    }
    public String getSecretQAnswer() {
        return secretQAnswer;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setSecretQ(String secretQ) {
        this.secretQ = secretQ;
    }
    public void setSecretQAnswer(String secretQAnswer) {
        this.secretQAnswer = secretQAnswer;
    }
}

