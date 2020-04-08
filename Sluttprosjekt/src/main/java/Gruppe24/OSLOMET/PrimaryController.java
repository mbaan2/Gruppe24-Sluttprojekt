package Gruppe24.OSLOMET;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PrimaryController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtGasCar.setText("Gasoline Car \n Cost: 20.000");
        txtDieselCar.setText("Diesel Car \n Cost: 17.500");
        txtElectricCar.setText("Electric Car \n Cost: 30.000");
    }

    @FXML
    private Button txtGasCar;

    @FXML
    private Button txtDieselCar;

    @FXML
    private Button txtElectricCar;

    @FXML
    void btnDieselCar(ActionEvent event) throws IOException {
        NewCar.set(0, new Carparts("Gasoline Car", 20_000));
        System.out.println("Done");
        App.setRoot("02-secondary");

    }

    @FXML
    void btnElectricCar(ActionEvent event) throws IOException {
        NewCar.set(0, new Carparts("Diesel Car", 17_500));
        System.out.println("Done");
        App.setRoot("02-secondary");

    }

    @FXML
    void btnGasCar(ActionEvent event) throws IOException {
        NewCar.set(0, new Carparts("Electric Car", 30_000));
        System.out.println("Done");
        App.setRoot("02-secondary");

    }

    @FXML
    void btnLoadCars(ActionEvent event) throws IOException{
        App.setRoot("loadedCars");
    }

    @FXML
    void logoutBtn() throws IOException {
        App.setRoot("login");
    }
}
