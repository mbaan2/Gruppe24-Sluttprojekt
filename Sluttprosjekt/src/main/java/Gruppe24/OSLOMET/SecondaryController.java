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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class SecondaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openFile();
    }

    @FXML
    private HBox hboxx;

    final ToggleGroup wheelGroup = new ToggleGroup();
    List<Car> wheelOptions = new ArrayList<>();
    List<RadioButton> wheelButtons = new ArrayList<>();

    public void createButtons(List<Car> wheels){
        for(int i = 0; i< wheels.size(); i++){
            String str = "";
            str = wheels.get(i).getName();
            RadioButton newWheels = new RadioButton(str);
            newWheels.setToggleGroup(wheelGroup);
            wheelButtons.add(newWheels);
        }
        hboxx.getChildren().clear();
        hboxx.getChildren().addAll(wheelButtons);
        wheelButtons.get(0).setSelected(true);
    }

    public void setWheels(){
        //ONLY USED FIRST TIME TO SET THE VALUES IN THE DOCUMENT
        Car wheelsBig = new Carparts("Big wheels", 5000);
        Car wheelsSmall = new Carparts("Small wheels", 0);
        Car wheelsMedium = new Carparts("Medium wheels", 2500);

        wheelOptions.add(wheelsBig);
        wheelOptions.add(wheelsMedium);
        wheelOptions.add(wheelsSmall);

        createButtons(wheelOptions);
    }

    public void createFile(){
        //ONLY USED FIRST TIME TO CREATE THE FILE
        Path filsti = Paths.get("wheels.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, wheelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void openFile(){
        Path path = Paths.get("wheels.jobj");
        wheelOptions = FileOpenerJobj.openFile(path);
        createButtons(wheelOptions);
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
}