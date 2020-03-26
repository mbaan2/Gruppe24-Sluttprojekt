package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.CarBuild;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class QuinaryController{

    @FXML
    private Label lblCarComponents;

    @FXML
    private Button quinaryButton;

    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = CarBuild.buildCar();
        int totalCost = CarBuild.totalCost();
        lblCarComponents.setText(ut + "\n" + "TotalCost of this car is: " +totalCost);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("01-primary");
    }
}