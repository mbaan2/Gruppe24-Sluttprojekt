package Gruppe24.OSLOMET;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class UserRegister{


    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField answerTxt;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label genderLbl;

    @FXML
    void retrievePwBtn(ActionEvent event) throws IOException {
        Path path = Paths.get(".\\user.txt");

        long count = Files.lines(path).count();

        for (int i = 0; i < count; i++) {
            String line = Files.readAllLines(path).get(i);
            if (!line.trim().equals("")) {
                String[] user = line.split(";");

                String username = user[0];
                String password = user[1];
                String location = user[2];
                String gender = user[3];
                String questionAnswer = user[4];

                if (username.trim().equals(usernameTxt.getText()) && questionAnswer.trim().equals(answerTxt.getText())) {
                    usernameLbl.setText(username);
                    passwordLbl.setText(password);
                    locationLbl.setText(location);
                    genderLbl.setText(gender);
                    return;
                }
            }
        }
    }

    @FXML
    void loginBtn() throws IOException {
        App.setRoot("login");
    }
}