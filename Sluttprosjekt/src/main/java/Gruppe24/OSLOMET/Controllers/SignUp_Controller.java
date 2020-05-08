package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.ImportUser;
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
    private RadioButton checkMale, checkFemale, checkOther;

    @FXML
    private TextField signupLocation, answerTxt, signupUsername, signupPassword;

    @FXML
    private Label usernameError, passwordError, locationError, secretQError, answerError, genderError, signupLbl;

    @FXML
    private ChoiceBox<String> choiceBox;

    final ToggleGroup toggleGroup = new ToggleGroup();
    ObservableList<String> checkBoxList = FXCollections.observableArrayList();
    public ObservableList<User> userList = FXCollections.observableArrayList();
    public HashMap<String, String> userBase = new HashMap<>();

    @FXML
    void signUp(ActionEvent event) throws IOException, InvalidNameException {
        passwordError.setText("");
        usernameError.setText("");
        locationError.setText("");
        secretQError.setText("");
        answerError.setText("");
        genderError.setText("");


        String username = "";
        if (ValidName.usernameTest(signupUsername.getText())){
            username = signupUsername.getText();
        }
        String password = signupPassword.getText();
        String location = "";
        if (ValidName.locationTest(signupLocation.getText())){
            location = signupLocation.getText();
        }
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

            userList = ImportUser.readUser(StandardPaths.usersTXTPath);
            userBase = FileOpenerJobj.openFileHashMap();

            //Writing only the username and the password to a hashmap for logging in unless it already exists in the register.
            if(!userBase.containsKey(username)) {
                try {
                    userBase.put(newUser.getUsername(), newUser.getPassword());
                    FileSaverJobj.SaveUser(userBase);
                    signupLbl.setText("User created!");
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("File not found");
                    alert.setContentText(e.getMessage());
                }
            } else {
                usernameError.setText("Username already exists!");
            }

            //Writing the list to a txt file for the user register unless it already exist in the register.
            if (userList.stream().noneMatch(user -> user.getUsername().equals(newUser.getUsername()))) {
                try {
                    userList.add(newUser);
                    String str = FormatUser.formatUsers(userList);
                    Path path = Paths.get(StandardPaths.usersTXTPath);
                    File selectedFile = new File(String.valueOf(path));
                    WriteUser.writeString(selectedFile, str);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(e.getMessage());
                    alert.setHeaderText("File not found");
                }
            }
        } else {
            if(signupUsername.getText().isEmpty()) {
                usernameError.setText("Enter a username!");
            } else if(!ValidName.usernameTest(signupUsername.getText())) {
                usernameError.setText("Enter a valid username!");
            }
            if(password.isEmpty()) {
                passwordError.setText("Enter a password!");
            }
            if(signupLocation.getText().isEmpty()) {
                locationError.setText("Enter a location!");
            } else if(!ValidName.locationTest(signupLocation.getText())) {
                locationError.setText("Enter a valid location!");
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

        checkBoxList.add(checkBoxQuestion);
        checkBoxList.addAll(FileOpenerJobj.openSecretQList());
        choiceBox.getItems().addAll(checkBoxList);
        choiceBox.setValue(checkBoxQuestion);
    }

    @FXML
    void loginBtn(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
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

