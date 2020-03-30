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
        if(usernameTxt.getText().equals("admin") && passwordTxt.getText().equals("admin")) {
            App.setRoot("adminPage");
        } else {
            Path path = Paths.get("C:\\Users\\oyvin\\IdeaProjects\\SluttprosjektDemo\\standard\\user.txt");

            Long count = Files.lines(path).count();

            for(int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if(!line.trim().equals("")) {
                    String[] user = line.split(";");

                    String username = user[0];
                    String password = user[1];

                    if(username.trim().equals(usernameTxt.getText())) {
                        if(password.trim().equals(passwordTxt.getText())) {
                            login();
                        } else {
                            passwordError.setText("Wrong password");
                        }
                    } else {
                        usernameError.setText("Wrong username");
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
        App.setRoot("primary");
    }
}
