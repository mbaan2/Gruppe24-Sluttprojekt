package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class UserRegister_Controller implements Initializable {


    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField answerTxt;

    @FXML
    private Label answerLbl;

    @FXML
    private Button passwordBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Label usernameError;

    @FXML
    private Label answerError;

    @FXML
    private Label choiceBoxError;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableView<User> tableView;

    EditUserRegisterTV newUserTable = new EditUserRegisterTV();

    ObservableList<String> checkBoxList = FXCollections.observableArrayList();


    @FXML
    void nextButton(ActionEvent event) throws IOException {
        Path path = Paths.get(StandardPaths.usersTXTPath);

        long count = Files.lines(path).count();

        for (int i = 0; i < count; i++) {
            String line = Files.readAllLines(path).get(i);
            if (!line.trim().equals("")) {
                String[] user = line.split(";");

                String username = user[0];
                String password = user[1];
                String location = user[2];
                String gender = user[3];
                String secretQ = user[4];
                String questionAnswer = user[5];

                if(username.trim().equals(usernameTxt.getText())) {
                    usernameError.setText("");
                    newUserTable.resetTableView();
                    choiceBox.setVisible(true);
                    answerLbl.setVisible(true);
                    answerTxt.setVisible(true);
                    passwordBtn.setVisible(true);
                    choiceBox.setValue(secretQ);
                    newUserTable.setNotVisible(tableView);
                    nextBtn.setVisible(false);
                }
            }
        }
    }

    @FXML
    void retrievePwBtn(ActionEvent event) throws IOException {

        Path path = Paths.get(StandardPaths.usersTXTPath);

        long count = Files.lines(path).count();

        for (int i = 0; i < count; i++) {
            String line = Files.readAllLines(path).get(i);
            if (!line.trim().equals("")) {
                String[] user = line.split(";");

                String username = user[0];
                String password = user[1];
                String location = user[2];
                String gender = user[3];
                String secretQ = user[4];
                String questionAnswer = user[5];

                if(choiceBox.getValue().equals(secretQ)) {
                    choiceBoxError.setText("");
                    User newUser = new User(username, password, location, gender, secretQ, questionAnswer);
                    if (username.trim().equals(usernameTxt.getText()) && questionAnswer.trim().equals(answerTxt.getText())) {
                        choiceBoxError.setText("");
                        usernameError.setText("");
                        answerError.setText("");

                        // Adding values to the tableview
                        newUserTable.addElement(newUser);
                        newUserTable.setVisible(tableView);

                        nextBtn.setVisible(true);
                        setNotVisible();
                        return;
                    }
                    if(answerTxt.getText().isEmpty()) {
                        answerError.setText("Enter an answer!");
                    } else if(!questionAnswer.trim().equals(answerTxt.getText())) {
                        answerError.setText("Wrong answer!");
                    }
                } else {
                    choiceBoxError.setText("Wrong secret question!");
                }
            }
        }
    }

    @FXML
    void loginBtn() throws IOException {
        App.setRoot("login");
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

    private void setNotVisible() {
        answerLbl.setVisible(false);
        answerTxt.setVisible(false);
        choiceBox.setVisible(false);
        passwordBtn.setVisible(false);
        answerTxt.setText("");
        usernameTxt.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChkBoxItems();
        setNotVisible();
        newUserTable.attachTableView(tableView);
        tableView.setVisible(false);
    }
}