package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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

        String path= "cars.jobj";
        try {
            FileSaverJobj.addingOnlyOneCarObject(path, App.car);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = App.car.toString();
        int totalCost = App.car.getCost();
        lblCarComponents.setText(ut + "Totalcost of this car is: " + totalCost + "kr");
        btnSaveCar.setVisible(true);
    }

    @FXML
    void btnNameCar(ActionEvent event) {
        FileSaverTxt fs = new FileSaverTxt();
        try{
            fs.saveTxtFile(App.car);
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