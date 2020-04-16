package Gruppe24.OSLOMET;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class PrimaryController implements Initializable {

    private int clicked = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtGasCar.setText("Gasoline Car \n Cost: 20.000 kr");
        txtDieselCar.setText("Diesel Car \n Cost: 17.500 kr");
        txtElectricCar.setText("Electric Car \n Cost: 30.000 kr");
    }

    @FXML
    private Button txtGasCar;

    @FXML
    private Button txtDieselCar;

    @FXML
    private Button txtElectricCar;

    @FXML
    void btnDieselCar(ActionEvent event) throws IOException {
        App.car.setFuel(new Carparts("Gasoline Car", 20_000));
        clicked = 1;
        txtDieselCar.setStyle("-fx-background-color: #def2f1;");
        txtDieselCar.setTextFill(Color.valueOf("#17252a"));
        txtElectricCar.setStyle("-fx-background-color: #17252a;");
        txtElectricCar.setTextFill(Color.valueOf("#def2f1"));
        txtGasCar.setStyle("-fx-background-color: #17252a;");
        txtGasCar.setTextFill(Color.valueOf("#def2f1"));
    }

    @FXML
    void btnElectricCar(ActionEvent event) throws IOException {
        App.car.setFuel(new Carparts("Diesel Car", 17_500));
        clicked = 2;
        txtElectricCar.setStyle("-fx-background-color: #def2f1;");
        txtElectricCar.setTextFill(Color.valueOf("#17252a"));
        txtGasCar.setStyle("-fx-background-color: #17252a;");
        txtGasCar.setTextFill(Color.valueOf("#def2f1"));
        txtDieselCar.setStyle("-fx-background-color: #17252a;");
        txtDieselCar.setTextFill(Color.valueOf("#def2f1"));
    }

    @FXML
    void btnGasCar(ActionEvent event) throws IOException {
        App.car.setFuel(new Carparts("Electric Car", 30_000));
        clicked = 3;
        txtGasCar.setStyle("-fx-background-color: #def2f1;");
        txtGasCar.setTextFill(Color.valueOf("#17252a"));
        txtElectricCar.setStyle("-fx-background-color: #17252a;");
        txtElectricCar.setTextFill(Color.valueOf("#def2f1"));
        txtDieselCar.setStyle("-fx-background-color: #17252a;");
        txtDieselCar.setTextFill(Color.valueOf("#def2f1"));

    }

    @FXML
    void switchToSecondary(ActionEvent event) throws IOException{
        if (clicked != 0){
            App.setRoot("02-secondary");
        } else {
            Alert mustChoose = new Alert(Alert.AlertType.ERROR);
            mustChoose.setHeaderText("Choose fuel!");
            mustChoose.setContentText("You must choose a fuel type to proceed.");
            mustChoose.show();
        }
    }

    @FXML
    void btnLoadCars(ActionEvent event) throws IOException{
        Path path = Paths.get(StandardPaths.carsPath);
        FileOpenerJobj.openFileArray(path);
        App.setRoot("loadedCars");
    }

    @FXML
    void logoutBtn() throws IOException {
        App.setRoot("login");
    }
}
