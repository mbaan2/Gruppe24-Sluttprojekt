package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private CheckBox checkMale;

    @FXML
    private CheckBox checkFemale;

    @FXML
    private CheckBox checkOther;

    @FXML
    private TextField signupLocation;

    public ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    void signUp(ActionEvent event) {
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
        } else {
            if(username.isEmpty()) {
                System.out.println("You didnt enter anything.");
            }
            if(password.isEmpty()) {
                // Error labels in these I guess
                System.out.println("You didnt enter anything.");
            }
            if(location.isEmpty()) {
                System.out.println("You didnt enter anything.");
            }
            if(gender.isEmpty()) {
                System.out.println("You didnt choose any gender.");
            }
        }
        String str = FormatUser.formatUsers(userList);

        FileChooser fc = new FileChooser();
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", ".txt"));
        fc.setInitialFileName("user");
        fc.setInitialDirectory(new File(currentDir));

        File selectedFile = fc.showOpenDialog(null);

        try {
            WriteUser.writeString(selectedFile, str);
        } catch (Exception e) {
            System.err.println("Failed to read file");
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
