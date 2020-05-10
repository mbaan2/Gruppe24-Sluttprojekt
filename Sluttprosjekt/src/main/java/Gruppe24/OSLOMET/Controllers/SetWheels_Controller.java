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
        openFile();
        createButtons();

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) wheelsVbox.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                lblErrorWheels.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
    }

    @FXML
    private VBox vboxWheels, wheelsVbox;

    @FXML
    private Label lblErrorWheels;

    public void createButtons(){
        LoadingValuesOnScreen.creatingList(wheelButtons, wheelOptions, wheelGroup);
        LoadingValuesOnScreen.returnVbox(wheelButtons, vboxWheels);

        if(App.car.wheels == null) {
            wheelButtons.get(0).setSelected(true);
        } else{
            boolean selected = false;
            for(int i=0; i <wheelOptions.size();i++){
                if(App.car.getWheels().getName().equals(wheelOptions.get(i).getName())){
                    selected = true;
                    wheelButtons.get(i).setSelected(true);
                    break;
                }
            }
            /*If value doesnt exist anymore, the system gives this error*/
            if(!selected) {
                wheelButtons.get(0).setSelected(true);
                lblErrorWheels.setText("Your previous selected wheels are not available, please contact the superUser or select a new set of wheels.");
            }
        }
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.wheelPath);
        try {
            wheelOptions = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            lblErrorWheels.setText("An error occurred while your were in the program. Contact superUser to reset the carparts files");
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
        } catch (LoadException e) {
            lblErrorWheels.setText("Error in one of the Carpart files, please contact the superUser to restore the system.");
        } catch (IOException e){
            lblErrorWheels.setText("An error has occurred, please contact the superUser.");
        } catch (IllegalStateException e){
            lblErrorWheels.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void backBtn() {
        for(int i = 0; i<wheelOptions.size();i++){
            if(wheelButtons.get(i).isSelected()){
                App.car.setWheels(wheelOptions.get(i));
            }
        }

        try{
            App.setRoot("SetFuel");
        } catch (IOException e){
            lblErrorWheels.setText("An error has occurred, please contact the superUser");
        }
    }
}