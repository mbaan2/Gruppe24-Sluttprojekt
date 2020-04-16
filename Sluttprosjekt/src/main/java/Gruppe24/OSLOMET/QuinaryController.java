package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class QuinaryController {

    @FXML
    private Button btnSaveCar;

    @FXML
    private Button btnNameCar;

    @FXML
    private TextField lblCarName;

    @FXML
    private Button btnBuildCar;

    @FXML
    private Label lblCarComponents;

    @FXML
    private Button quinaryButton;

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

        btnBuildCar.setDisable(true);
        btnBuildCar.setLayoutX(226.0);
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
    void switchToPrimary(ActionEvent event) throws IOException {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        App.setRoot("01-primary");
    }

    //EDIT SO THAT USER CAN GO THROUGH THE WHOLE PROCESS, BUT ALREADY WITH INPUT DATA THAT HAD PREVIOUSLY BEEN INSERTED
    @FXML
    void switchToNullary(ActionEvent event) throws IOException {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        App.setRoot("00-nullary");
    }

    @FXML
    void logoutBtn(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}