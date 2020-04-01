package Gruppe24.OSLOMET;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    void forgotPassword() throws IOException {
        App.setRoot("userRegister");
    }

    @FXML
    void loginBtn(ActionEvent event) throws IOException {

        passwordError.setText("");
        usernameError.setText("");

        if(usernameTxt.getText().equals("admin") && passwordTxt.getText().equals("admin")) {
            App.setRoot("adminPage");
        } else {
            Path path = Paths.get(".\\user.txt");
            long count = Files.lines(path).count();

            for(int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);

                if(!line.trim().equals("")) {
                    String[] user = line.split(";");
                    String username = user[0];
                    String password = user[1];

                    if(username.trim().equals(usernameTxt.getText()) && !usernameTxt.getText().isEmpty()) {
                        if(password.trim().equals(passwordTxt.getText()) && !passwordTxt.getText().isEmpty()) {
                            login();
                        }
                    }

                    if(usernameTxt.getText().isEmpty()) {
                        usernameError.setText("Enter a username");
                    }
                    if(passwordTxt.getText().isEmpty()) {
                        passwordError.setText("Enter a password");
                    } else if(!password.trim().equals(passwordTxt.getText())) {
                        passwordError.setText("Wrong password");
                    }
                }
            }
        }


    }

    @FXML
    void signUp() throws IOException {
        App.setRoot("signup");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void login() throws IOException {
        App.setRoot("01-primary");
    }
}
