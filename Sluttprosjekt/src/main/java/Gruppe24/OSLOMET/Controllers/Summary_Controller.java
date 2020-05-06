package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Summary_Controller implements Initializable {

    @FXML
    private AnchorPane summaryPane;

    @FXML
    private Button btnNameCar;

    @FXML
    private TextField lblCarName;

    @FXML
    private Button btnBuildCar;

    @FXML
    private Label lblCarComponents;

    @FXML
    private Label carNameLbl;

    @FXML
    private Label summaryLbl;

    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = App.car.toString();

        int totalCost = App.car.totalCost();
        App.car.setCost(totalCost);
        lblCarComponents.setText(ut + "Total cost of this car is: " + App.car.getCost() + "kr");

        btnBuildCar.setDisable(true);
        btnBuildCar.setLayoutX(130.0);
        carNameLbl.setVisible(true);
        btnNameCar.setVisible(true);
        lblCarName.setVisible(true);
    }

    @FXML
    void btnNameCar(ActionEvent event) {
        // We need a function here that test if the name already exist
        // If we have time it would be could to add a random name generator
        String nameCar = lblCarName.getText();
        if (!nameCar.equals("")) {
            App.car.setName(lblCarName.getText());
            btnNameCar.setDisable(true);

            List<NewCar> list = new ArrayList<>();
            try{
                list = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            }
            catch (IOException e){
                System.err.println(e.getMessage());
            }

            try {
                //TO SAVE THE INITIAL LIST
                FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
                FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                saveCarToText();
                summaryLbl.setText("Car is added to the list. You named it " + App.car.getName());
            } catch (IOException ex) {
                summaryLbl.setText("You didnt choose a file to save to!");
            }
        } else{
            summaryLbl.setText("Please give the car a name");
        }

    }

    void saveCarToText() {
        lblCarName.setText("");
        
        FileSaverTxt fs = new FileSaverTxt();
        try{
            fs.saveTxtFile(App.car);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    void btnToFuel(ActionEvent event) {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(259.0);
        btnBuildCar.setDisable(false);
        try{
            App.setRoot("SetFuel");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void backBtn() {
        try{
            App.setRoot("SetAddons");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }


    //EDIT SO THAT USER CAN GO THROUGH THE WHOLE PROCESS, BUT ALREADY WITH INPUT DATA THAT HAD PREVIOUSLY BEEN INSERTED
    @FXML
    void btnToWelcomScreen(ActionEvent event) {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        try{
            App.setRoot("WelcomeScreen");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

    @FXML
    void logoutBtn(ActionEvent event){
        try{
            App.setRoot("login");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            Stage stage = (Stage) summaryPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
        carNameLbl.setVisible(false);
    }
}