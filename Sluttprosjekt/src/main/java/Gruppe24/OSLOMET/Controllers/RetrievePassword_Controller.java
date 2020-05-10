package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.ExceptionClasses.OpenFileException;
import Gruppe24.OSLOMET.ExceptionClasses.SaveFileException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class RetrievePassword_Controller implements Initializable {
    EditUserRegisterTV newUserTable = new EditUserRegisterTV();
    HashMap<String, String> userBase = new HashMap<>();
    ObservableList<String> checkBoxList = FXCollections.observableArrayList();
    List<User> userList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userBase = FileOpenerJobj.openFileHashMap();
        } catch (IOException | ClassNotFoundException e){
            retrieveLbl.setText(e.getMessage());
        }

        addChkBoxItems();
        setNotVisible();
        newUserTable.attachTableView(tableView);
        tableView.setVisible(false);

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) registerPane.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                retrieveLbl.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
    }

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField usernameTxt, answerTxt;

    @FXML
    private Label usernameError, answerLbl, answerError, choiceBoxError, usernameLbl, retrieveLbl;

    @FXML
    private Button passwordBtn, nextBtn;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableView<User> tableView;

    @FXML
    void nextButton(ActionEvent event) {
        try {
            userList = FileOpenerJobj.openUserList();

            for (User user : userList) {
                String username = user.getUsername();
                String secretQ = user.getSecretQ();

                if (username.trim().equals(usernameTxt.getText())) {
                    usernameError.setText("");
                    answerError.setText("");
                    choiceBoxError.setText("");
                    newUserTable.resetTableView();
                    choiceBox.setVisible(true);
                    answerLbl.setVisible(true);
                    answerTxt.setVisible(true);
                    answerTxt.setText("");
                    passwordBtn.setVisible(true);
                    choiceBox.setValue(secretQ);
                    newUserTable.setNotVisible(tableView);
                    nextBtn.setLayoutY(192);
                    usernameLbl.setLayoutY(139);
                    usernameError.setLayoutY(160);
                    usernameTxt.setLayoutY(156);
                }
                if (usernameTxt.getText().equals("")) {
                    usernameError.setText("Enter a username!");
                } else if (!userBase.containsKey(usernameTxt.getText())) {
                    usernameError.setText("Username doesn't exist!");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            retrieveLbl.setText(e.getMessage());
        }
    }

    @FXML
    void retrievePwBtn(ActionEvent event) {
        try {
            userList = FileOpenerJobj.openUserList();

            for (User user : userList) {
                String username = user.getUsername();
                String password = user.getPassword();
                String location = user.getLocation();
                String gender = user.getGender();
                String secretQ = user.getSecretQ();
                String questionAnswer = user.getSecretQAnswer();

                if (choiceBox.getValue().equals(secretQ)) {
                    choiceBoxError.setText("");
                    User newUser = new User(username, password, location, gender, secretQ, questionAnswer);
                    if (username.trim().equals(usernameTxt.getText()) && questionAnswer.trim().equals(answerTxt.getText())) {
                        choiceBoxError.setText("");
                        answerError.setText("");
                        // Adding values to the tableview
                        newUserTable.addElement(newUser);
                        newUserTable.setVisible(tableView);
                        nextBtn.setLayoutY(268);
                        usernameTxt.setLayoutY(239);
                        usernameLbl.setLayoutY(221);
                        usernameError.setLayoutY(244);
                        setNotVisible();
                        return;
                    }
                } else {
                    choiceBoxError.setText("Wrong secret question!");
                }
                if (answerTxt.getText().isEmpty()) {
                    answerError.setText("Enter an answer!");
                } else if (!questionAnswer.trim().equals(answerTxt.getText())) {
                    answerError.setText("Wrong answer!");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            retrieveLbl.setText(e.getMessage());
        }
    }

    @FXML
    void loginBtn() {
        try {
            App.setRoot("login");
        } catch (IOException e){
            retrieveLbl.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e){
            retrieveLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    private void addChkBoxItems() {
        checkBoxList.removeAll();
        String checkBoxQuestion = "Select a question!";
        checkBoxList.add(checkBoxQuestion);
        try {
            checkBoxList.addAll(FileOpenerJobj.openSecretQList());
            choiceBox.getItems().addAll(checkBoxList);
            choiceBox.setValue(checkBoxQuestion);
        } catch (IOException | ClassNotFoundException e) {
            retrieveLbl.setText(e.getMessage());
        }
    }

    private void setNotVisible() {
        answerLbl.setVisible(false);
        answerTxt.setVisible(false);
        choiceBox.setVisible(false);
        passwordBtn.setVisible(false);
        answerTxt.setText("");
        usernameTxt.setText("");
    }
}
