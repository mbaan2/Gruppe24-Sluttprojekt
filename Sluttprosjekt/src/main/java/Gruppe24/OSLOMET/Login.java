package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarUsername;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;

public class Login{

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    HashMap<String, String> userBase = null;

    @FXML
    void forgotPassword() throws IOException {
        App.setRoot("userRegister");
    }

    @FXML
    void loginBtn(ActionEvent event) throws IOException {
        passwordError.setText("");
        usernameError.setText("");

        userBase = FileOpenerJobj.openFileHashMap();

        if (usernameTxt.getText().equals("admins") && passwordTxt.getText().equals("admins")) {
            App.setRoot("adminPage");
        }

        if(userBase.containsKey(usernameTxt.getText())){
            if(userBase.get(usernameTxt.getText()).equals(passwordTxt.getText())){
                CarUsername carUsername = new CarUsername(usernameTxt.getText());
                NewCar.setUsername(carUsername);
                login();
            }
        }


        //Feedback to the user
        if (usernameTxt.getText().isEmpty()) {
                usernameError.setText("Enter a username");
        }
        if (!userBase.containsKey(usernameTxt.getText())) {
                usernameError.setText("Wrong username");
        }
        if (passwordTxt.getText().isEmpty()) {
                passwordError.setText("Enter a password");
        } else if (!userBase.containsValue(passwordTxt.getText())) {
                passwordError.setText("Wrong password");
            }
    }

    @FXML
    void signUp() throws IOException {
        App.setRoot("signup");
    }

    private void login() throws IOException {
        App.setRoot("01-primary");
    }
}
