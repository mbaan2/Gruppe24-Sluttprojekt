package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.UserLogin.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class SignUp_Controller implements Initializable {
    final ToggleGroup toggleGroup = new ToggleGroup();
    ObservableList<String> checkBoxList = FXCollections.observableArrayList();
    List<User> userList;
    HashMap<String, String> userBase = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setDisable(false);
        addChkBoxItems();
        checkOther.setToggleGroup(toggleGroup);
        checkFemale.setToggleGroup(toggleGroup);
        checkMale.setToggleGroup(toggleGroup);

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) signupPane.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                signupLblError.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
    }

    @FXML
    private AnchorPane signupPane;

    @FXML
    private RadioButton checkMale, checkFemale, checkOther;

    @FXML
    private TextField signupLocation, answerTxt, signupUsername, signupPassword;

    @FXML
    private Label usernameError, passwordError, locationError, secretQError, answerError, genderError, signupLbl, signupLblError;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    void signUp(ActionEvent event) {
        try {
            userList = FileOpenerJobj.openUserList();
            try {
                userBase = FileOpenerJobj.openFileHashMap();
                passwordError.setText("");
                usernameError.setText("");
                locationError.setText("");
                secretQError.setText("");
                answerError.setText("");
                genderError.setText("");

                String username = "";
                if(!signupUsername.getText().isEmpty()) {
                    try {
                        ValidName.usernameTest(signupUsername.getText());
                        username = signupUsername.getText();
                    } catch (InvalidNameException e) {
                        usernameError.setText(e.getMessage());
                    }
                }

                String password = signupPassword.getText();

                String location = "";
                if(!signupLocation.getText().isEmpty()) {
                    try {
                        ValidName.locationTest(signupLocation.getText());
                        location = signupLocation.getText();
                    } catch (InvalidNameException e) {
                        locationError.setText(e.getMessage());
                    }
                }

                String gender = "";
                String answer = answerTxt.getText();
                String secretQ = choiceBox.getValue();

                if(checkFemale.isSelected()) {
                    gender = "Female";
                } else if(checkMale.isSelected()) {
                    gender = "Male";
                } else if(checkOther.isSelected()) {
                    gender = "Other";
                }

                if(!username.isEmpty() && !password.isEmpty() && !location.isEmpty() && !gender.isEmpty() &&!answer.isEmpty() && !secretQ.equals("Select a question!")) {
                    User newUser = new User(username, password, location, gender, secretQ, answer);

                    //Writing only the username and the password to a hashmap for logging in unless it already exists in the register.
                    if(!userBase.containsKey(username)) {
                        try {
                            userBase.put(newUser.getUsername(), newUser.getPassword());
                            FileSaverJobj.SaveUser(userBase);
                            signupLbl.setText("User created!");
                        } catch (IOException e) {
                            signupLblError.setText(e.getMessage());
                        }
                    } else {
                        usernameError.setText("Username already exists!");
                    }

                    //Writing the list to a txt file for the user register unless it already exist in the register.
                    if (userList.stream().noneMatch(user -> user.getUsername().equals(newUser.getUsername()))) {
                        try {
                            userList.add(newUser);
                            FileSaverJobj.SaveUserList(userList);
                        } catch (IOException e) {
                            signupLblError.setText(e.getMessage());
                        }
                    }
                } else {
                    if(signupUsername.getText().isEmpty()) {
                        usernameError.setText("Enter a username!");
                    }
                    if(signupPassword.getText().isEmpty()) {
                        passwordError.setText("Enter a password!");
                    }
                    if(signupLocation.getText().isEmpty()) {
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

            } catch (IOException | ClassNotFoundException e){
                signupLbl.setText(e.getLocalizedMessage());
            }
        } catch (IOException | ClassNotFoundException e){
            signupLblError.setText(e.getMessage());
        }
    }

    private void addChkBoxItems() {
        checkBoxList.removeAll();
        String checkBoxQuestion = "Select a question!";

        checkBoxList.add(checkBoxQuestion);
        try {
            checkBoxList.addAll(FileOpenerJobj.openSecretQList());
        } catch (IOException | ClassNotFoundException e) {
            choiceBox.setDisable(true);
            signupLbl.setText(e.getMessage());
        }
        choiceBox.getItems().addAll(checkBoxList);
        choiceBox.setValue(checkBoxQuestion);
    }

    @FXML
    void loginBtn(ActionEvent event) {
        try {
            App.setRoot("login");
        } catch (IOException e){
            signupLblError.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e){
            signupLblError.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }
}

