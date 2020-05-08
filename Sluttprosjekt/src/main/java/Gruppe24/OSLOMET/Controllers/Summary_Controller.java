package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.Alerts;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.OpenFileException;
import Gruppe24.OSLOMET.ExceptionClasses.ScreenNotFoundException;
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
                    //SAVES THE INITIAL LIST
                    FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
                    FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                    summaryLbl.setText("Car is added to the list. You named it " + App.car.getName());
                    btnSaveCarToText.setVisible(true);
                } catch (OpenFileException e){
                    summaryLbl.setText("Could not open file");
                    Alerts.fileLoadAlert("Car list");
                } catch (IOException e){
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
            } catch (OpenFileException e){
                summaryLbl.setText("Could not open file");
                e.printStackTrace();
                Alerts.fileLoadAlert("Car list");
            } catch (IOException e){
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
        Path path = Paths.get("./Car Txt Files/" + username + "sCars.txt");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save your car to a txt file!");
        alert.setHeaderText("");
        alert.setContentText("Do you want to overwrite or append your current file? Or create a new file?" + "\n\nIf you choose 'New File' your new file will be named " + username + "sCars.txt");
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
                FileSaverTxt.append(str, selectedFile, summaryLbl, username);
                btnSaveCarToText.setVisible(false);
            } else if(result.get() == newFile) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.newFile(str, selectedFile, summaryLbl, username);
                btnSaveCarToText.setVisible(false);
            } else if(result.get() == overwrite) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.overwrite(str, selectedFile, summaryLbl, username);
                btnSaveCarToText.setVisible(false);
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
        } catch (ScreenNotFoundException | IllegalStateException e){
            Alerts.screenLoadAlert();
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        } catch (IOException e){
            System.err.println(e.getMessage());
            Alerts.screenLoadAlert();
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
        } catch (ScreenNotFoundException | IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
            Alerts.screenLoadAlert();
        } catch (IOException e){
            System.err.println(e.getMessage());
            Alerts.screenLoadAlert();
        }
    }


    //EDIT SO THAT USER CAN GO THROUGH THE WHOLE PROCESS, BUT ALREADY WITH INPUT DATA THAT HAD PREVIOUSLY BEEN INSERTED
    @FXML
    void btnToWelcomeScreen(ActionEvent event) {
        txtCarName.setVisible(false);
        btnNameCar.setVisible(false);
        btnBuildCar.setLayoutX(318.0);
        btnBuildCar.setDisable(false);
        btnSaveCarToText.setVisible(false);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return to homepage");
        alert.setHeaderText("");
        alert.setContentText("Returning to the homepage, this will reset your current car.\nRemember to save it if you haven't already!");
        ButtonType returnHome = new ButtonType("Go to home");
        ButtonType cancel  = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(returnHome, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent()) {
            if(result.get() == returnHome) {
                try {
                    App.setRoot("WelcomeScreen");
                    String username = App.car.getUser();
                    App.startCarBuildingProcess();
                    App.car.setUser(username);
                } catch (ScreenNotFoundException | IllegalStateException e) {
                    System.err.println("There is an error in loading the next screen, please contact your developer.");
                    Alerts.screenLoadAlert();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    Alerts.screenLoadAlert();
                }
            } else {
                summaryLbl.setText("Process cancelled, staying in the summary page.");
            }
        }
    }

    @FXML
    void logoutBtn(ActionEvent event){
        try{
            App.setRoot("login");
        } catch (ScreenNotFoundException | IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
            Alerts.screenLoadAlert();
        } catch (IOException e){
            System.err.println(e.getMessage());
            Alerts.screenLoadAlert();
        }
    }
}