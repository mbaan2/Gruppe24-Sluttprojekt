package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SignUp_Controller implements Initializable {

    @FXML
    private AnchorPane signupPane;

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
    private TextField answerTxt;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    private Label locationError;

    @FXML
    private Label secretQError;

    @FXML
    private Label answerError;

    @FXML
    private Label genderError;

    @FXML
    private Label signupLbl;

    @FXML
    private ChoiceBox<String> choiceBox;

    final ToggleGroup toggleGroup = new ToggleGroup();
    ObservableList<String> checkBoxList = FXCollections.observableArrayList();
    public ObservableList<User> userList = FXCollections.observableArrayList();
    public HashMap<String, String> userBase = new HashMap<>();

    @FXML
    void signUp(ActionEvent event) throws IOException {
        passwordError.setText("");
        usernameError.setText("");
        locationError.setText("");
        answerError.setText("");
        genderError.setText("");


        String username = signupUsername.getText();
        String password = signupPassword.getText();
        String location = signupLocation.getText();
        String gender = "";
        String answer = answerTxt.getText();
        String secretQ = choiceBox.getValue();

        if(checkFemale.isSelected()) {
            gender = "Female";
        }
        if(checkMale.isSelected()) {
            gender = "Male";
        }
        if(checkOther.isSelected()) {
            gender = "Other";
        }

        if(!username.isEmpty() && !password.isEmpty() && !location.isEmpty() && !gender.isEmpty() &&!answer.isEmpty() && !secretQ.equals("Select a question!")) {
            User newUser = new User(username, password, location, gender, secretQ, answer);

            userBase = FileOpenerJobj.openFileHashMap();

            //Writing only the username and the password to a hashmap for logging in.
            if(!userBase.containsKey(username)) {
                try {
                    userBase.put(newUser.getUsername(), newUser.getPassword());
                    FileSaverJobj.SaveUser(userBase);
                    signupLbl.setText("User created!");
                } catch (FileNotFoundException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("File not found");
                    alert.setContentText(e.getMessage());
                }
            } else {
                    usernameError.setText("Username already exists!");
            }

            //Writing the list to a txt file for the user register
            if(!userBase.containsKey(username)) {
                try {
                    userList.add(newUser);
                    String str = FormatUser.formatUsers(userList);
                    Path path = Paths.get(StandardPaths.usersTXTPath);
                    File selectedFile = new File(String.valueOf(path));
                    WriteUser.writeString(selectedFile, str);
                } catch (FileNotFoundException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(e.getMessage());
                    alert.setHeaderText("File not found");
                }
            } else {
                    usernameError.setText("Username already exists!");
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
            if(answer.isEmpty()) {
                answerError.setText("Enter an answer!");
            }
            if(gender.isEmpty()) {
                genderError.setText("Choose a gender!");
            }
            if(secretQ.equals("Select a question!")) {
                secretQError.setText("Choose a question!");
            }
        }
    }

    private void addChkBoxItems() {
        checkBoxList.removeAll();
        String checkBoxQuestion = "Select a question!";
        String checkBoxQType = "Name of first pet?";
        String checkBoxQType2 = "Mothers maiden name?";
        String checkBoxQType3 = "Whos your daddy?";

        checkBoxList.addAll(checkBoxQuestion, checkBoxQType, checkBoxQType2, checkBoxQType3);
        choiceBox.getItems().addAll(checkBoxList);
        choiceBox.setValue(checkBoxQuestion);
    }

    @FXML
    void loginBtn(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChkBoxItems();
        checkOther.setToggleGroup(toggleGroup);
        checkFemale.setToggleGroup(toggleGroup);
        checkMale.setToggleGroup(toggleGroup);

        Platform.runLater(() -> {
            Stage stage = (Stage) signupPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }
}
