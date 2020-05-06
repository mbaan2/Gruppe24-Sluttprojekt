package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    @FXML
    private Tooltip superuserInfo;

    HashMap<String, String> userBase;

    @FXML
    void forgotPassword(){
        try {
            App.setRoot("RetrievePassword");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void loginBtn(ActionEvent event) {
        passwordError.setText("");
        usernameError.setText("");

        userBase = FileOpenerJobj.openFileHashMap();

        if (usernameTxt.getText().equals("admin") && passwordTxt.getText().equals("admin")) {
            try {
                App.setRoot("SuperUser");
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }
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
    void signUp() {
        try{
            App.setRoot("Signup");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void login() {
        try{
            App.setRoot("WelcomeScreen");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setDefaultButton(true);
        superuserInfo.setShowDelay(Duration.millis(100.0));
        superuserInfo.setHideDelay(Duration.millis(200.0));

        //Using Platform's runLater() method to let the page load before changing the width so that we get no nullpointerexceptions.
        //Code based on a comment from https://stackoverflow.com/a/59880899
        Platform.runLater(() -> {
            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }
}
