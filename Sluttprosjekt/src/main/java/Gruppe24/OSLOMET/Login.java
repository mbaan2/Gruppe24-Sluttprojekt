package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.UserLogin.OpenUserJobj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Login implements Initializable {

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    HashMap<String, String> userList = null;

    @FXML
    void forgotPassword() throws IOException {
        App.setRoot("userRegister");
    }

    @FXML
    void loginBtn(ActionEvent event) throws IOException {

        passwordError.setText("");
        usernameError.setText("");

        if (usernameTxt.getText().equals("admin") && passwordTxt.getText().equals("admin")) {
            App.setRoot("adminPage");
        } else {
            Path path = Paths.get(".\\users.jobj");
            userList = OpenUserJobj.userList(path);
            System.out.println(userList);

            Set set = userList.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                if (mentry.getKey().equals(usernameTxt.getText()) && mentry.getValue().equals(passwordTxt.getText())) {
                    login();
                }
            }
        }


            /*if (userList.containsKey(usernameTxt.getText()) && userList.containsValue(passwordTxt.getText())) {
                login();
            }

             */

            if (usernameTxt.getText().isEmpty()) {
                usernameError.setText("Enter a username");
            }
            if (!userList.containsKey(usernameTxt.getText())) {
                usernameError.setText("Wrong username");
            }
            if (passwordTxt.getText().isEmpty()) {
                passwordError.setText("Enter a password");
            } else if (!userList.containsValue(passwordTxt.getText())) {
                passwordError.setText("Wrong password");
            }
        }







    @FXML
    void signUp() throws IOException {
        App.setRoot("signup");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void login() throws IOException {
        App.setRoot("01-primary");
    }
}
