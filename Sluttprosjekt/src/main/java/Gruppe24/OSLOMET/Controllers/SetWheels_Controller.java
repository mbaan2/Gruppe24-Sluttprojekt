package Gruppe24.OSLOMET.Controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.*;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetWheels_Controller implements Initializable {
    final ToggleGroup wheelGroup = new ToggleGroup();
    List<Carparts> wheelOptions = new ArrayList<>();
    List<RadioButton> wheelButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //createFile();
        openFile();
        createButtons();

        Platform.runLater(() -> {
            Stage stage = (Stage) wheelsVbox.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }

    @FXML
    private VBox vboxWheels;

    @FXML
    private VBox wheelsVbox;

    @FXML
    private Label lblErrorWheels;

    public void createButtons(){
        LoadingValuesOnScreen.creatingList(wheelButtons, wheelOptions, wheelGroup);
        LoadingValuesOnScreen.returnVbox(wheelButtons, vboxWheels);

        if(App.car.wheels == null) {
            wheelButtons.get(0).setSelected(true);
        } else{
            for(int i=0; i <wheelOptions.size();i++){
                if(App.car.getWheels().getName().equals(wheelOptions.get(i).getName())){
                    wheelButtons.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.wheelPath);
        try {
            wheelOptions = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            lblErrorWheels.setText("An error occurred while your were in the program. Contact superUser to reset carpart files");
        }
    }

    @FXML
    private void btnToColor() {
        for(int i = 0; i<wheelOptions.size();i++){
            if(wheelButtons.get(i).isSelected()){
                App.car.setWheels(wheelOptions.get(i));
            }
        }

        try{
            App.setRoot("SetColors");
        } catch (LoadException e){
            lblErrorWheels.setText("Error in one of the Carpart files, please contact the superUser to restore the system.");
        } catch (IOException e){
            lblErrorWheels.setText("An error has occurred, please contact the superUser.");
        } catch (IllegalStateException e){
            lblErrorWheels.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void backBtn() {
        try{
            App.setRoot("SetFuel");
        } catch (IOException e){
            lblErrorWheels.setText("An error has occurred, please contact the superUser");
        }
    }

    public void setWheels(){
        Carparts wheelsBig = new Carparts("Big wheels", 5000);
        Carparts wheelsSmall = new Carparts("Small wheels", 0);
        Carparts wheelsMedium = new Carparts("Medium wheels", 2500);

        wheelOptions.add(wheelsBig);
        wheelOptions.add(wheelsMedium);
        wheelOptions.add(wheelsSmall);
    }

    public void createFile(){
        setWheels();
        Path filsti = Paths.get(StandardPaths.wheelPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, wheelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}