package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
import Gruppe24.OSLOMET.UserLogin.WriteUserJobj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
    private TextField answerTxt;

    @FXML
    private Label usernameError;

    @FXML
    private Label passwordError;

    @FXML
    private Label locationError;

    @FXML
    private Label answerError;

    @FXML
    private Label genderError;

    @FXML
    private ChoiceBox<String> choiceBox;

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

        if(checkFemale.isSelected()) {
            gender = "Female";
        }
        if(checkMale.isSelected()) {
            gender = "Male";
        }
        if(checkOther.isSelected()) {
            gender = "Other";
        }

        if(!username.isEmpty() && !password.isEmpty() && !location.isEmpty() && !gender.isEmpty() &&!answer.isEmpty()) {
            User newUser = new User(username, password, location, gender, answer);

            
            try (FileInputStream in = new FileInputStream("users.ser");
                 ObjectInputStream oin = new ObjectInputStream(in)) {
                userBase = (HashMap) oin.readObject();
                in.close();
                oin.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }



            //Writing the hashmap to a jobj file for login
            userBase.put(newUser.getUsername(), newUser.getPassword());
            Path filsti = Paths.get("users.ser");
            WriteUserJobj.SaveUser(filsti, userBase);

            //Writing the list to a txt file for the userregister
            userList.add(newUser);
            String str = FormatUser.formatUsers(userList);
            Path path = Paths.get("user.txt");
            File selectedFile = new File(String.valueOf(path));

            try {
                WriteUser.writeString(selectedFile, str);
                App.setRoot("login");
            } catch (Exception e) {
                System.err.println("Failed to write file");
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
    void loginBtn2(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChkBoxItems();
    }
}
