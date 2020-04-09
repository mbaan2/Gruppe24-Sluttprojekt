package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.*;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuinaryController{

    @FXML
    private Button btnSaveCar;

    @FXML
    private Button btnNameCar;

    @FXML
    private TextField lblCarName;

    @FXML
    private Label lblCarComponents;

    @FXML
    void btnSaveCar(ActionEvent event) {
        lblCarName.setVisible(true);
        btnNameCar.setVisible(true);
        btnSaveCar.setDisable(true);

        Path filsti = Paths.get("cars.jobj");
        try {
            FileSaverJobj.SaveCarCategoryArray(filsti, NewCar.getCarInBuilding());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Path path = Paths.get("cars.jobj");
        NewCar.setCarInBuilding(FileOpenerJobj.openFileArray(path));
    }

    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = NewCar.builtCarUserView();
        int totalCost = NewCar.totalCost();
        lblCarComponents.setText(ut + "\n" + "Totalcost of this car is: " +totalCost);
        btnSaveCar.setVisible(true);
    }

    @FXML
    void btnNameCar(ActionEvent event) {
        NewCar car = new NewCar("user", lblCarName.getText(), NewCar.totalCost(), NewCar.getCarInBuilding());
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
        //need to add something to clean add-ons. How???
        App.setRoot("01-primary");
    }
}