package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
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
    private Label lblErrorSummary, summaryLbl;

    @FXML
    private Button btnSaveCarToText;

    @FXML
    private TextField txtCarName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) summaryPane.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                lblErrorSummary.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
        btnSaveCarToText.setVisible(false);
    }

    @FXML
    void btnBuildCar(ActionEvent event) {
        try{
            btnNameCar(event);
            summaryLbl.setText(App.car.toString());
            btnSaveCarToText.setVisible(true);
        } catch (InvalidNameException | IOException e){
            lblErrorSummary.setText(e.getMessage());
        }
    }

    void btnNameCar(ActionEvent event) throws InvalidNameException, IOException {
        /* Throws InvalidNameException*/
        ValidName.carpartNameTest(txtCarName.getText());

        boolean unique;

        try{
            unique = ValidName.uniqueCarNameTest(txtCarName.getText(), App.car.getUser());
        } catch (IOException | ClassNotFoundException e){
            throw new IOException ("Couldnt load the car file to check if the carname is unique. Contact the developer to reset files.");
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
                overwriteCar();
            } else if (choice.get() == cancel) {
                txtCarName.setDisable(false);
            }
        } else{
            saveCar();
            txtCarName.setDisable(true);
        }
    }

    public void saveCar(){
        App.car.setName(txtCarName.getText());

        try{
            FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
            lblErrorSummary.setText(App.car.getName() + " has been added to the list.");
            btnSaveCarToText.setVisible(true);
        } catch (IOException e){
            lblErrorSummary.setText("An error has occurred please contact the developer.");
        }
    }

    public void overwriteCar(){
        App.car.setName(txtCarName.getText());
        List<NewCar> list = new ArrayList<>();

        /*Removing the old car */
        try{
            list = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            for (int i=0; i<list.size(); i++){
                if(list.get(i).getUser().equals(App.car.getUser())){
                    if(list.get(i).getName().equals(App.car.getName())){
                        list.remove(i);
                    }
                }
            }
        } catch (IOException e){
            lblErrorSummary.setText("An error occurred while opening the Car file. Please contact the superUser to restore the file.");
        }

        /*Saving both the list and the new car, new car will be last in the list so it easier to find for the user*/
        try{
            //SAVES THE INITIAL LIST
            FileSaverJobj.SavingCarArray(StandardPaths.carsPath, list);
            FileSaverJobj.addingOnlyOneCarObject(StandardPaths.carsPath, App.car);
            lblErrorSummary.setText("Car is added to your account. You named it " + App.car.getName());
            btnSaveCarToText.setVisible(true);
        } catch (IOException e){
            lblErrorSummary.setText("An error occurred while saving the file, please contact the developer.");
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
        alert.setContentText("Do you want to overwrite or append your current file?" + "\n\nEither option creates a new file in case you dont have one. Your new file will be named   "  + username + "sCars.txt and it will be placed in the folder called 'Car Txt Files'. Remember to close your txt file if you have it open!");
        alert.setHeight(350);
        ButtonType append = new ButtonType("Append");
        ButtonType overwrite = new ButtonType("Overwrite");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(append, overwrite, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent()) {
            if (result.get() == append) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.append(str, selectedFile, summaryLbl, username);
            } else if(result.get() == overwrite) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                try{
                    FileSaverTxt.overwrite(str, selectedFile, summaryLbl, username);
                } catch (IOException e){
                    lblErrorSummary.setText(e.getMessage());
                }
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
        } catch (IllegalStateException e){
            lblErrorSummary.setText("There is an error in loading the next screen, please contact your developer.");
        } catch (IOException e){
            lblErrorSummary.setText("An error has occurred, please contact your developer.");
        }
    }

    @FXML
    void btnToWelcomeScreen(ActionEvent event) {
        if(txtCarName.isDisable()){
            backToWelcomeScreen();
        } else {
            /*If car hasnt been saved yet: */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Return to homepage");
            alert.setHeaderText("");
            alert.setContentText("Returning to the homepage, this will reset your current car.\nRemember to save it if you haven't already!");
            ButtonType returnHome = new ButtonType("Go to home");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(returnHome, cancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == returnHome) {
                    backToWelcomeScreen();
                } else {
                    lblErrorSummary.setText("Process cancelled, staying in the summary page.");
                }
            }
        }
    }

    public void backToWelcomeScreen(){
        try {
            App.setRoot("WelcomeScreen");
            String username = App.car.getUser();
            App.startCarBuildingProcess();
            App.car.setUser(username);
        } catch (IllegalStateException e) {
            lblErrorSummary.setText("There is an error in loading the next screen, please contact your developer.");
        } catch (IOException e) {
            lblErrorSummary.setText("An error has occurred please contact your developer.");
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