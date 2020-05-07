package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.Alerts;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.FileTreatment.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Summary_Controller implements Initializable {

    @FXML
    private AnchorPane summaryPane;

    @FXML
    private Button btnNameCar, btnBuildCar, btnSaveCarToText;

    @FXML
    private TextField txtCarName;

    @FXML
    private Label lblCarComponents, carNameLbl, summaryLbl;

    List<NewCar> carList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            Stage stage = (Stage) summaryPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
        carNameLbl.setVisible(false);
    }

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
        // If we have time it would be could to add a random name generator
        boolean uniqueName = ValidName.uniqueCarNameTest(txtCarName.getText(), App.car.getUser());
        boolean validName = ValidName.carNameTest(uniqueName, txtCarName.getText());

        if (txtCarName.getText().equals("")) {
            summaryLbl.setText("Please give the car a name");
        } else if (!uniqueName){
            summaryLbl.setText("");
            //Adds possibility to override
            Alert overrideAlert = new Alert(Alert.AlertType.CONFIRMATION);
            overrideAlert.setTitle("Override alert!");
            overrideAlert.setContentText("You have already saved a car with this name.\nWould you like to override it?");
            txtCarName.setDisable(true);
            ButtonType override = new ButtonType("Override");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            overrideAlert.getButtonTypes().setAll(override, cancel);
            Optional<ButtonType> choice = overrideAlert.showAndWait();

            if (choice.get() == override){
                App.car.setName(txtCarName.getText());
                btnNameCar.setDisable(true);

                List<NewCar> list = new ArrayList<>();
                try{
                    list = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
                    for (int i=0; i<list.size(); i++){
                        if(list.get(i).getUser().equals(App.car.getUser())){
                            if(list.get(i).getName().equals(App.car.getName())){
                                list.remove(i);
                            }
                        }
                    }
                }catch (IOException e){
                    System.err.println(e.getMessage());

                }

                try{
                    //TO SAVE THE INITIAL LIST
                    FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
                    FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                    summaryLbl.setText("Car is added to the list. You named it " + App.car.getName());
                    btnSaveCarToText.setVisible(true);
                }catch (IOException e){
                    summaryLbl.setText(e.getMessage());
                    Alerts.fileLoadAlert("Car list");
                }
                txtCarName.setDisable(false);
            } else {
                txtCarName.setDisable(false);
            }
        } else if (!validName) {
            summaryLbl.setText("Your car's name is invalid. Please try again.");
        } else if(validName){
            App.car.setName(txtCarName.getText());
            btnNameCar.setDisable(true);

            List<NewCar> list = new ArrayList<>();
            try{
                list = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            }catch (IOException e){
                System.err.println(e.getMessage());
            }

            try{
                //TO SAVE THE INITIAL LIST
                FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
                FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                summaryLbl.setText(App.car.getName() + " has been added to the list.");
                btnSaveCarToText.setVisible(true);
            }catch (IOException e){
                summaryLbl.setText(e.getMessage());
                Alerts.fileLoadAlert("Car list");
            }
        } else {
            Alerts.processFailAlert("Car-naming");
        }

    }

    @FXML
    void btnSaveCarToText(ActionEvent event) {
        txtCarName.setText("");
        ObservableList<NewCar> outputList = FXCollections.observableArrayList();
        outputList.add(App.car);
        String username = App.car.getUser();
        Path path = Paths.get(username + "sCars.txt");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save your car to a txt file!");
        alert.setHeaderText("");
        alert.setContentText("Do you want to overwrite or append your current file? Or create a new file?" + "\n\nIf you choose 'New File' your new file will be named " + path.toString());
        ButtonType newFile = new ButtonType("New File");
        ButtonType append = new ButtonType("Append");
        ButtonType overwrite = new ButtonType("Overwrite");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(newFile, append, overwrite, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent()) {
            if (result.get() == append) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.append(str, selectedFile, summaryLbl);
                btnSaveCarToText.setVisible(false);
                summaryLbl.setText("Car appended to file!");
            } else if(result.get() == newFile) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.overwrite(str, selectedFile, summaryLbl);
                btnSaveCarToText.setVisible(false);
                summaryLbl.setText("New file created called " + path.toString() + " " + App.car.getName() + " was added to it.");
            } else if(result.get() == overwrite) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.overwrite(str, selectedFile, summaryLbl);
                btnSaveCarToText.setVisible(false);
                summaryLbl.setText("You overwrote your current file with " + App.car.getName() + ".");
            } else {
                summaryLbl.setText("Process cancelled. File wasnt saved.");
            }
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
            Alerts.screenLoadAlert();
        } catch (IllegalStateException e){
            Alerts.screenLoadAlert();
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
            Alerts.screenLoadAlert();
        } catch (IllegalStateException e){
            Alerts.screenLoadAlert();
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
            Alerts.screenLoadAlert();
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
            Alerts.screenLoadAlert();
        }
        String username = App.car.getUser();
        App.startCarBuildingProcess();
        App.car.setUser(username);
    }

    @FXML
    void logoutBtn(ActionEvent event){
        try{
            App.setRoot("login");
        } catch (IOException e){
            Alerts.screenLoadAlert();
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            Alerts.screenLoadAlert();
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
}