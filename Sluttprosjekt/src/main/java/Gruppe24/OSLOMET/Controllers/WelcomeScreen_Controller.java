package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreen_Controller implements Initializable {

    @FXML
    private VBox welcomeVbox;

    @FXML
    private ComboBox<String> comboAllCars;

    @FXML
    private Label lblFeedbackChangeCar;

    @FXML
    private Label lblErrorWelcomeScreen;

    @FXML
    void btnLoadCar(ActionEvent event) {

    }

    @FXML
    void btnStartBuilding(ActionEvent event) {
        try {
            App.setRoot("SetFuel");
        } catch (LoadException e){
            System.err.println(e.getMessage() + "Error in file, please contact superUser to restore the system");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void btnToTableView(ActionEvent event) {
        try {
            App.setRoot("UserCarView");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void logoutBtn() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
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