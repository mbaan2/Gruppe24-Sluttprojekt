package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Summary_Controller {

    @FXML
    private Button btnSaveCar;

    @FXML
    private Button btnNameCar;

    @FXML
    private TextField lblCarName;

    @FXML
    private Button btnBuildCar;

    @FXML
    private Button btnSaveCarToText;

    @FXML
    private Label lblCarComponents;

    @FXML
    private Button quinaryButton;


    @FXML
    void btnBuildCar(ActionEvent event) {
        String ut = App.car.toString();
        int totalCost = App.car.getCost();
        lblCarComponents.setText(ut + "Totalcost of this car is: " + totalCost + "kr");

        btnBuildCar.setDisable(true);
        btnBuildCar.setLayoutX(226.0);
        btnNameCar.setVisible(true);
        lblCarName.setVisible(true);

    }

    @FXML
    void btnNameCar(ActionEvent event) {
        // We need a function here that test if the name already exist
        // If we have time it would be could to add a random name generator
        String nameCar = lblCarName.getText();
        if (!nameCar.equals("")) {//&& nameCar.exist();{
            App.car.setName(lblCarName.getText());
            btnNameCar.setDisable(true);
            btnSaveCarToText.setVisible(true);

            //Code has to be removed when program is done!
            Carparts fuel = new Carparts("Diesel", 1000);
            Carparts wheels = new Carparts("Big wheels", 1000);
            Carparts color = new Carparts("Red", 1000);


            Carparts gps = new Carparts("GPS", 1000);
            Carparts spoiler = new Carparts("Spoiler", 1000);
            CarCategory add_ones = new CarCategory("Add Ones");
            add_ones.add(gps);
            add_ones.add(spoiler);


            NewCar Car1 = new NewCar();
            Car1.setUser("123");
            Car1.setFuel(fuel);
            Car1.setWheels(wheels);
            Car1.setColor(color);
            Car1.setAddons(add_ones);

            List<NewCar> list = new ArrayList<>();
            list.add(Car1);
            list.add(Car1);


            // Used for testing
            //ArrayList<NewCar> list2 = new ArrayList<>();

            try {
                //TO SAVE THE INITIAL LIST
                FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
                FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                //list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println("Car is added to the list.");

            /*
            Used for testing
            for(int i = 0; i<list2.size(); i++){
                System.out.println(list2.get(i).getUser());
                System.out.println(list2.get(i).getAddons().getElement(0).getName());
            }

             */
        } else{
            System.err.println("Please give the car a name");
            // System.err.println("Name is already taken");
        }

    }

    @FXML
    void btnSaveCarToText(ActionEvent event) {
        FileSaverTxt fs = new FileSaverTxt();
        try{
            fs.saveTxtFile(App.car);
        }catch (IOException e){
            e.printStackTrace();
        }
        btnNameCar.setDisable(false);
    }


    @FXML
    void switchToPrimary(ActionEvent event) throws IOException {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        App.setRoot("SetFuel");
    }


    //EDIT SO THAT USER CAN GO THROUGH THE WHOLE PROCESS, BUT ALREADY WITH INPUT DATA THAT HAD PREVIOUSLY BEEN INSERTED
    @FXML
    void switchToNullary(ActionEvent event) throws IOException {
        lblCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        App.setRoot("WelcomeScreen");
    }

    @FXML
    void logoutBtn(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}