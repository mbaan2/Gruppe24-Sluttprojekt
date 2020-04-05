package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.CarObj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private Button btnSaveCar;

    @FXML
    private Button btnNameCar;

    @FXML
    private TextField lblCarName;

    @FXML
    private HBox hboxxRadioButtons;

    @FXML
    private Label lblCarComponents;

    @FXML
    private Button quinaryButton;

    @FXML
    void btnSaveCar(ActionEvent event) {
        lblCarName.setVisible(true);
        btnNameCar.setVisible(true);
        btnSaveCar.setDisable(true);
    }

    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = BuildingNewCar.buildCar();
        int totalCost = BuildingNewCar.totalCost();
        lblCarComponents.setText(ut + "\n" + "Totalcost of this car is: " +totalCost);
        btnSaveCar.setVisible(true);
    }

    @FXML
    void btnNameCar(ActionEvent event) {
        CarObj car = new CarObj(lblCarName.getText(), BuildingNewCar.totalCost(), CarObj.fetchCarParts());
        FileSaverTxt fs = new FileSaverTxt();
        try{
            fs.saveTxtFile(car);
        }catch (IOException e){
            e.printStackTrace();
        }
        btnSaveCar.setDisable(false);
    }


    @FXML
    private void switchToPrimary() throws IOException {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        App.setRoot("01-primary");
    }
}