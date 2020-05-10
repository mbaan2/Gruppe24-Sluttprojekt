package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuperUser_Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) superUserPane.getScene().getWindow();
                stage.setWidth(500);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                superUserLbl.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
    }

    @FXML
    private AnchorPane superUserPane;

    @FXML
    private Label superUserLbl;

    @FXML
    void switchToFileRestoration() {
        try {
            App.setRoot("SuperUserFileRestoration");
        } catch (IOException e) {
            superUserLbl.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e) {
            superUserLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void switchToCarpartView() {
        try {
            App.setRoot("SuperUserCarparts");
        } catch (IOException e) {
            superUserLbl.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e) {
            superUserLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void btnBackToLogin() {
        try {
            App.setRoot("Login");
        } catch (IOException e){
            superUserLbl.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e){
            superUserLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void switchToCarView() {
        try {
            App.setRoot("SuperUserCarView");
        } catch (IOException e){
            superUserLbl.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e){
            superUserLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void switchToUserView() {
        try {
            App.setRoot("Userlist");
        } catch (IOException e) {
            superUserLbl.setText("An error has occurred, please contact your developer.");
        } catch (IllegalStateException e){
            superUserLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }
}
