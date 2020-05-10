package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WelcomeScreen_Controller implements Initializable {
    ObservableList<String> comboBoxList = FXCollections.observableArrayList();
    List<NewCar> newList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loadCarNames();

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) welcomeVbox.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                lblErrorWelcomeScreen.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
    }

    @FXML
    private VBox welcomeVbox;

    @FXML
    private ComboBox<String> comboAllCars;

    @FXML
    private Label lblFeedbackChangeCar, lblErrorWelcomeScreen;

    @FXML
    private Button btnStartBuilding, btnLoadCar, btnToTableView;

    @FXML
    void btnLoadCar(ActionEvent event) {
        lblFeedbackChangeCar.setText("");
        if(!comboBoxList.isEmpty()){
            for(NewCar newCar : newList){
                if(newCar.getName().equals(comboAllCars.getValue())){
                    App.car.setName(newCar.getName());
                    App.car.setFuel(newCar.getFuel());
                    App.car.setWheels(newCar.getWheels());
                    App.car.setColor(newCar.getColor());
                    App.car.setAddons(newCar.getAddons());
                    break;
                }
            }
            
            try {
                App.setRoot("SetFuel");
            } catch (LoadException e){
                lblErrorWelcomeScreen.setText("Error in the fuel file, please contact superUser to restore the system");
            } catch (IOException e) {
                lblErrorWelcomeScreen.setText("An error has occurred, please contact your developer");
            } catch (IllegalStateException e){
                lblErrorWelcomeScreen.setText("There is an error in loading the next screen, please contact your developer.");
            }
        } else {
            lblFeedbackChangeCar.setText("You havn't made a car yet.");
        }
    }

    @FXML
    void btnStartBuilding(ActionEvent event) {
        try {
            App.setRoot("SetFuel");
        } catch (LoadException e){
            lblErrorWelcomeScreen.setText("Error in the fuel file, please contact superUser to restore the system");
        } catch (IOException e) {
            lblErrorWelcomeScreen.setText("An error has occurred, please contact your developer");
        } catch (IllegalStateException e){
            lblErrorWelcomeScreen.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void btnToTableView(ActionEvent event) {
        try {
            App.setRoot("UserCarView");
        } catch (IOException e) {
            lblErrorWelcomeScreen.setText("An error has occurred, please contact your developer");
        } catch (IllegalStateException e){
            lblErrorWelcomeScreen.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void logoutBtn() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            lblErrorWelcomeScreen.setText("An error has occurred, please contact your developer");
        } catch (IllegalStateException e){
            lblErrorWelcomeScreen.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    public void loadCarNames() {
        try {
            newList = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        } catch (IOException e){
            lblErrorWelcomeScreen.setText("An error has occurred while loading your cars, please contact the superUser to restore the file.");
            btnStartBuilding.setDisable(true);
            btnLoadCar.setDisable(true);
            btnToTableView.setDisable(true);
        }

        for(NewCar newCar : newList){
            if(newCar.getUser().equals(App.car.getUser())){
               comboBoxList.add(newCar.getName());
            }
        }

        if(!comboBoxList.isEmpty()){
            comboAllCars.getItems().addAll(comboBoxList);
            comboAllCars.setValue(comboBoxList.get(0));
        }
    }
}