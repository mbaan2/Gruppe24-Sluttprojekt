package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.Alerts;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.ExceptionClasses.OpenFileException;
import Gruppe24.OSLOMET.ExceptionClasses.ScreenNotFoundException;
import Gruppe24.OSLOMET.FileTreatment.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Label lblErrorSummary, summaryLbl;

    @FXML
    private Button btnBuildCar , btnSaveCarToText;

    @FXML
    private TextField txtCarName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            Stage stage = (Stage) summaryPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
        btnSaveCarToText.setVisible(false);
    }

    @FXML
    void btnBuildCar(ActionEvent event) {
        try{
            btnNameCar(event);
            String ut = App.car.toString();

            int totalCost = App.car.totalCost();
            App.car.setCost(totalCost);
            summaryLbl.setText(ut + "Total cost of this car is: " + App.car.getCost() + "kr");

            btnSaveCarToText.setVisible(true);
        } catch (InvalidNameException e){
            lblErrorSummary.setText(e.getMessage());
        }
    }

    void btnNameCar(ActionEvent event) throws InvalidNameException {
        /* Throws InvalidNameException*/
        ValidName.carNameTest(txtCarName.getText());
        boolean unique = true;

        try{
            unique = ValidName.uniqueCarNameTest(txtCarName.getText(), App.car.getUser());
        } catch (IOException e){
            lblErrorSummary.setText(e.getMessage());
        }

        if(!unique){
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
                    lblErrorSummary.setText("An error occurred while opening the Car file. Please contact the superUser to restore the file.");
                }

                try{
                    //SAVES THE INITIAL LIST
                    FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
                    FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                    lblErrorSummary.setText("Car is added to the list. You named it " + App.car.getName());
                    btnSaveCarToText.setVisible(true);
                } catch (OpenFileException e){
                    lblErrorSummary.setText("Error in opening the car file, please contact the superUser to restore the files");
                } catch (IOException e){
                    lblErrorSummary.setText("An error occurred while saving the file, please contact the developer.");
                }
            }
        } else{
            App.car.setName(txtCarName.getText());

            try{
                FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
                lblErrorSummary.setText(App.car.getName() + " has been added to the list.");
                btnSaveCarToText.setVisible(true);
            } catch (OpenFileException e){
                lblErrorSummary.setText("Could not open file");
                e.printStackTrace();
                Alerts.fileLoadAlert("Car list");
            } catch (IOException e){
                lblErrorSummary.setText("An error has occurred please contact the developer.");
            }
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
    void backBtn() {
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
                    txtCarName.setVisible(false);
                    btnBuildCar.setDisable(false);
                    btnSaveCarToText.setVisible(false);
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
        } catch (IllegalStateException e){
            lblErrorSummary.setText("There is an error in loading the next screen, please contact your developer.");
        } catch (IOException e){
            lblErrorSummary.setText("An error has occurred, please contact your developer.");
        }
    }
}