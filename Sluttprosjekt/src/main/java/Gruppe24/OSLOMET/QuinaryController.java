package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.CarCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuinaryController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createRadioButton();
    }

    final ToggleGroup group = new ToggleGroup();

    List<RadioButton> buttonList = new ArrayList<>();
    public void createRadioButton(){
        /*
        String sortCar = "";
        sortCar = "Police car";
        RadioButton policeCar = new RadioButton(sortCar);
        policeCar.setToggleGroup(group);
        buttonList.add(policeCar);
        sortCar = "Race car";
        RadioButton raceCar = new RadioButton(sortCar);
        raceCar.setToggleGroup(group);
        buttonList.add(raceCar);
        sortCar = "GrandMa car";
        RadioButton grandMaCar = new RadioButton(sortCar);
        grandMaCar.setToggleGroup(group);
        buttonList.add(grandMaCar);
        hboxxRadioButtons.getChildren().clear();
        hboxxRadioButtons.getChildren().addAll(buttonList);
        buttonList.get(0).setSelected(true);

         */
    }

    @FXML
    private HBox hboxxRadioButtons;

    @FXML
    private Label lblCarComponents;

    @FXML
    private Button quinaryButton;

    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = BuildingNewCar.buildCar();
        int totalCost = BuildingNewCar.totalCost();
        lblCarComponents.setText(ut + "\n" + "TotalCost of this car is: " +totalCost);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("01-primary");
    }
}