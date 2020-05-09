package Gruppe24.OSLOMET.Controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetFuel_Controller implements Initializable {
    final ToggleGroup fuelGroup = new ToggleGroup();
    List<Carparts> fuelOptions = new ArrayList<>();
    List<RadioButton> fuelButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //createFile();
        openFile();
        createButtons();

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) fuelVbox.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                lblErrorFuel.setText("Error in setting the proper width and height, resize the window manually.");
            }

        });
    }

    @FXML
    private VBox fuelVbox, vboxFuel;

    @FXML
    private Label lblErrorFuel;

    public void createButtons(){
        LoadingValuesOnScreen.creatingList(fuelButtons, fuelOptions, fuelGroup);
        LoadingValuesOnScreen.returnVbox(fuelButtons, vboxFuel);

        if(App.car.fuel == null) {
            fuelButtons.get(0).setSelected(true);
        } else{
            boolean selected = false;
            for(int i=0; i <fuelOptions.size();i++){
                if(App.car.getFuel().getName().equals(fuelOptions.get(i).getName())){
                    selected = true;
                    fuelButtons.get(i).setSelected(true);
                    break;
                }
            }
            if(!selected) {
                fuelButtons.get(0).setSelected(true);
                lblErrorFuel.setText("Your previous selected fuel is not available, please contact the superUser or select a new fuel system.");
            }
        }
    }

    public void openFile() {
        Path path = Paths.get(StandardPaths.fuelPath);
        try {
            fuelOptions = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            lblErrorFuel.setText("An error occurred while your were in the program. Contact superUser to reset the carparts files");
        }
    }

    @FXML
    void btnToWheels(ActionEvent event) {
        for(int i = 0; i<fuelOptions.size();i++){
            if(fuelButtons.get(i).isSelected()){
                App.car.setFuel(fuelOptions.get(i));
            }
        }

        try{
            App.setRoot("SetWheels");
        } catch (LoadException e){
            lblErrorFuel.setText("Error in one of the Carpart files, please contact the superUser to restore the system.");
        } catch (IOException e){
            lblErrorFuel.setText("An error has occurred, please contact the superUser.");
        } catch (IllegalStateException e){
            lblErrorFuel.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void backBtn(){
        for(int i = 0; i<fuelOptions.size();i++){
            if(fuelButtons.get(i).isSelected()){
                App.car.setFuel(fuelOptions.get(i));
            }
        }

        try{
            App.setRoot("WelcomeScreen");
        } catch (IOException e){
            lblErrorFuel.setText("An error has occurred, please contact the superUser");
        }
    }
}
