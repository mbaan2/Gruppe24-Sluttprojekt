package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
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
    private TextField txtCarName;

    @FXML
    private Button btnBuildCar;

    @FXML
    private Button btnSaveCarToText;

    @FXML
    private Label lblCarComponents;

    @FXML
    private Label carNameLbl;

    @FXML
    private Label summaryLbl;

    List<NewCar> carList = new ArrayList<>();

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
        txtCarName.setVisible(true);
    }

    @FXML
    void btnNameCar(ActionEvent event) {
        // We need a function here that test if the name already exist
        // If we have time it would be could to add a random name generator
        String nameCar = txtCarName.getText();
        if (!nameCar.equals("")) {
            App.car.setName(txtCarName.getText());
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
                summaryLbl.setText("Car is added to the list. You named it " + App.car.getName());
                btnSaveCarToText.setVisible(true);
            } catch (IOException e) {
                summaryLbl.setText(e.getMessage());
            }
        } else{
            summaryLbl.setText("Please give the car a name");
        }

    }

    @FXML
    void btnSaveCarToText(ActionEvent event) {
        txtCarName.setText("");
        
        FileSaverTxt fs = new FileSaverTxt();
        try{
            fs.saveTxtFile(App.car);
            btnSaveCarToText.setVisible(false);
        }catch (IOException e){
            summaryLbl.setText("You did not select a valid file.");
        }
    }


    @FXML
    void btnToFuel(ActionEvent event) {
        txtCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(259.0);
        btnBuildCar.setDisable(false);
        btnSaveCarToText.setVisible(false);
        try{
            App.setRoot("SetFuel");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void backBtn() {
        txtCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(259.0);
        btnBuildCar.setDisable(false);
        btnSaveCarToText.setVisible(false);
        try{
            App.setRoot("SetAddons");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }


    //EDIT SO THAT USER CAN GO THROUGH THE WHOLE PROCESS, BUT ALREADY WITH INPUT DATA THAT HAD PREVIOUSLY BEEN INSERTED
    @FXML
    void btnToWelcomScreen(ActionEvent event) {
        txtCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        btnSaveCarToText.setVisible(false);
        try{
            App.setRoot("WelcomeScreen");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }

    }

    @FXML
    void logoutBtn(ActionEvent event){
        try{
            App.setRoot("login");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }

    public void setCars() {
        NewCar car1 = new NewCar();
        NewCar car2 = new NewCar();
        NewCar car3 = new NewCar();
        NewCar car4 = new NewCar();

        car1.setUser("123");
        car1.setName("GreatCar");
        car1.setFuel(new Carparts("Diesel", 20000));
        car1.setWheels(new Carparts("Medium wheels", 2500));
        car1.setColor(new Carparts("Blue", 2500));
        CarCategory addons = new CarCategory("Addons");
        addons.add(new Carparts("Spoiler", 4000));
        addons.add(new Carparts("Subwoofer", 7500));
        car1.setAddons(addons);

        car2.setUser("123");
        car2.setName("BestCar");
        car2.setFuel(new Carparts("Gasoline", 15000));
        car2.setWheels(new Carparts("Big wheels", 5000));
        car2.setColor(new Carparts("Black", 0));
        CarCategory addons2 = new CarCategory("Addons");
        addons2.add(new Carparts("Spoiler", 4000));
        addons2.add(new Carparts("Subwoofer", 7500));
        addons2.add(new Carparts("GPS", 5000));
        car2.setAddons(addons2);

        car3.setUser("234");
        car3.setName("ZoomZoom");
        car3.setFuel(new Carparts("Electric", 35000));
        car3.setWheels(new Carparts("Medium wheels", 2500));
        car3.setColor(new Carparts("Red", 5000));
        CarCategory addons3 = new CarCategory("Addons");
        addons3.add(new Carparts("Spoiler", 4000));
        addons3.add(new Carparts("GPS", 5000));
        car3.setAddons(addons3);

        car4.setUser("Aaa");
        car4.setName("ChooChoo");
        car4.setFuel(new Carparts("Electric", 35000));
        car4.setWheels(new Carparts("Small wheels", 2500));
        car4.setColor(new Carparts("Yellow", 2500));
        CarCategory addons4 = new CarCategory("Addons");
        addons4.add(new Carparts("Subwoofer", 7500));
        car4.setAddons(addons4);

        carList.add(car1);
        carList.add(car2);
        carList.add(car3);
        carList.add(car4);
    }

    public void createFile() {
        setCars();
        try {
            FileSaverJobj.SavingCarArray(StandardPaths.carsPath, carList);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
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