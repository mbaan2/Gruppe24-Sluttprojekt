package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.FileTreatment.FileChooserTxt;
import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SignUp implements Initializable {

    @FXML
    private PasswordField signupPassword;

    @FXML
    private TextField signupUsername;

    @FXML
    private RadioButton checkMale;

    @FXML
    private RadioButton checkFemale;

    @FXML
    private RadioButton checkOther;

    @FXML
    private TextField signupLocation;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    private Label locationError;

    @FXML
    private Label genderError;


    public ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    void signUp(ActionEvent event) {
        passwordError.setText("");
        usernameError.setText("");
        locationError.setText("");

        String username = signupUsername.getText();
        String password = signupPassword.getText();
        String location = signupLocation.getText();
        String gender = "";

        if(checkFemale.isSelected()) {
            gender = "Female";
        }
        if(checkMale.isSelected()) {
            gender = "Male";
        }
        if(checkOther.isSelected()) {
            gender = "Other";
        }
        if(!username.isEmpty() && !password.isEmpty() && !location.isEmpty() && !gender.isEmpty()) {
            userList.add(new User(username, password, location, gender));

            String str = FormatUser.formatUsers(userList);

            File selectedFile = FileChooserTxt.fileChooserTxt("user.txt");

            try {
                WriteUser.writeString(selectedFile, str);
            } catch (Exception e) {
                System.err.println("Failed to read file");
            }
        } else {
            if(username.isEmpty()) {
                usernameError.setText("Enter a username!");
            }
            if(password.isEmpty()) {
                passwordError.setText("Enter a password!");
            }
            if(location.isEmpty()) {
                locationError.setText("Enter a location!");
            }
            if(gender.isEmpty()) {
                genderError.setText("Choose a gender!");
            }
        }
    }

    @FXML
    void loginBtn2(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
