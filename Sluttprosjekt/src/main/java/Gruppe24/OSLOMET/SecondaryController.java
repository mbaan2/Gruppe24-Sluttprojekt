package Gruppe24.OSLOMET;

import java.io.IOException;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarBuild;
import Gruppe24.OSLOMET.Car.Carparts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    void btnLargeWheels(ActionEvent event) {
        Carparts largeWheels = new Carparts("Large Wheels", 5000);
        CarBuild.add(largeWheels);
        System.out.println("added");
    }

    @FXML
    void btnMediumWheels(ActionEvent event) {
        Carparts mediumWheels = new Carparts("Medium Wheels", 2500);
        CarBuild.add(mediumWheels);
    }

    @FXML
    void btnSmallWheels(ActionEvent event) {
        Carparts smallWheels = new Carparts("Small Wheels", 0);
        CarBuild.add(smallWheels);
    }

    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("03-tertiary");
    }
}