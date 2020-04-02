package Gruppe24.OSLOMET;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.Car.*;
import Gruppe24.OSLOMET.FileHandling.FileOpenerJobj;
import Gruppe24.OSLOMET.FileHandling.FileSaverJobj;
import Gruppe24.OSLOMET.LoadingValuesOnScreen.LoadingValuesOnScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SecondaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openFile();
    }

    @FXML
    private VBox vboxWheels;

    final ToggleGroup wheelGroup = new ToggleGroup();
    List<Car> wheelOptions = new ArrayList<>();
    List<RadioButton> wheelButtons = new ArrayList<>();

    public void createButtons(){
        wheelButtons = LoadingValuesOnScreen.creatingList(wheelButtons, wheelOptions, wheelGroup);
        vboxWheels = LoadingValuesOnScreen.returnVbox(wheelButtons, vboxWheels);
        wheelButtons.get(0).setSelected(true);
    }

    public void openFile(){
        Path path = Paths.get("wheels.jobj");
        wheelOptions = FileOpenerJobj.openFile(path);
        createButtons();
    }

    @FXML
    private void switchToTertiary() throws IOException {
        for(int i = 0; i<wheelOptions.size();i++){
            if(wheelButtons.get(i).isSelected()){
                for(int j = 0; j<wheelOptions.size(); j++){
                    if(wheelButtons.get(i).getText().equals(wheelOptions.get(j).getName())){
                        BuildingNewCar.addStatic(wheelOptions.get(j));
                    }
                }
            }
        }

        App.setRoot("03-tertiary");
    }








    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setWheels(){
        Car wheelsBig = new Carparts("Big wheels", 5000);
        Car wheelsSmall = new Carparts("Small wheels", 0);
        Car wheelsMedium = new Carparts("Medium wheels", 2500);

        wheelOptions.add(wheelsBig);
        wheelOptions.add(wheelsMedium);
        wheelOptions.add(wheelsSmall);

        createButtons();
    }

    public void createFile(){
        Path filsti = Paths.get("wheels.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, wheelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}