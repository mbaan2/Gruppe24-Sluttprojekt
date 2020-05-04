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
        setWheels();
        createFile();
        //openFile();

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
        wheelOptions = FileOpenerJobj.openFile(path);
        createButtons();
    }

    @FXML
    private void btnToColor() throws IOException {
        for(int i = 0; i<wheelOptions.size();i++){
            if(wheelButtons.get(i).isSelected()){
                App.car.setWheels(wheelOptions.get(i));
            }
        }

        App.setRoot("SetColors");
    }

    @FXML
    void backBtn() throws IOException{
        App.setRoot("SetFuel");
    }




    //Only used for creating/setting the initial .jobj file
    public void setWheels(){
        Carparts wheelsBig = new Carparts("Big wheels", 5000);
        Carparts wheelsSmall = new Carparts("Small wheels", 0);
        Carparts wheelsMedium = new Carparts("Medium wheels", 2500);

        wheelOptions.add(wheelsBig);
        wheelOptions.add(wheelsMedium);
        wheelOptions.add(wheelsSmall);

        createButtons();
    }

    public void createFile(){
        Path filsti = Paths.get(StandardPaths.wheelPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, wheelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}