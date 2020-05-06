package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreen_Controller implements Initializable {

    @FXML
    private VBox welcomeVbox;

    @FXML
    void btnStartBuilding(ActionEvent event) {
        try {
            App.setRoot("SetFuel");
        } catch (LoadException e){
            System.err.println(e.getMessage() + "Error in file, please contact superUSer to restore the system");
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnLoadCars(ActionEvent event) {
        try {
            App.setRoot("UserCarView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logoutBtn() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Platform.runLater(() -> {
            Stage stage = (Stage) welcomeVbox.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }
}