package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Login_Controller implements Initializable {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    private Button loginBtn;

    HashMap<String, String> userBase;

    @FXML
    void forgotPassword() throws IOException {
        App.setRoot("userRegister");
    }

    @FXML
    void loginBtn(ActionEvent event) throws IOException {
        passwordError.setText("");
        usernameError.setText("");

        userBase = FileOpenerJobj.openFileHashMap();

        if (usernameTxt.getText().equals("admin") && passwordTxt.getText().equals("admin")) {
            App.setRoot("superUser");
        } else if(userBase.containsKey(usernameTxt.getText())){
            if(userBase.get(usernameTxt.getText()).equals(passwordTxt.getText())){
                App.car.setUser(usernameTxt.getText());
                login();
            }
        }

        //Feedback to the user
        if (usernameTxt.getText().isEmpty()) {
                usernameError.setText("Enter a username");
        } else if(!userBase.containsKey(usernameTxt.getText())) {
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
        App.setRoot("WelcomeScreen");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setDefaultButton(true);

        //Using Platform's runLater() method to let the page load before changing the width so that we get no nullpointerexceptions.
        //Code based on a comment from https://stackoverflow.com/a/59880899
        Platform.runLater(() -> {
            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }
}