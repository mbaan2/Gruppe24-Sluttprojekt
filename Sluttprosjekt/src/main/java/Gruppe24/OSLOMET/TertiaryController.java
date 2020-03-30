package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TertiaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxData();
    }


    @FXML
    private ChoiceBox<String> cbxColor;

    @FXML
    private Button tertiaryButton;

    ObservableList<String> list = FXCollections.observableArrayList();
    private void loadChoiceBoxData(){
        list.removeAll();
        String red = "red";
        String blue = "blue";
        String black = "black";
        String green = "green";
        String yellow = "yellow";

        list.addAll(red,blue,black,green,yellow);
        cbxColor.getItems().addAll(list);
    }

    @FXML
    private void switchToQuaternary() throws IOException {
        String color = cbxColor.getValue();

        if(color.equals("red")){
            Car red = new Carparts("Color of this car is red", 3000);
            BuildingNewCar.addStatic(red);
            System.out.println("color is red");
        }

        App.setRoot("04-quaternary");
    }
}