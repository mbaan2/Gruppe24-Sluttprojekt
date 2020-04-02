package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileHandling.FileOpenerJobj;
import Gruppe24.OSLOMET.FileHandling.FileSaverJobj;
import Gruppe24.OSLOMET.LoadingValuesOnScreen.LoadingValuesOnScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class QuaternaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openFile();
    }

    @FXML
    private VBox vboxAddOnes;

    List<Car> addOneOptions = new ArrayList<>();
    List<CheckBox> addOneButtons = new ArrayList<>();

    public void createButtons(List<Car> addone){
        addOneButtons = LoadingValuesOnScreen.creatingList(addOneButtons, addOneOptions);
        vboxAddOnes = LoadingValuesOnScreen.returnVbox(addOneButtons, vboxAddOnes);
        //addOneButtons.get(0).setSelected(true);
    }

    public void setWheels(){
        //ONLY USED FIRST TIME TO SET THE VALUES IN THE DOCUMENT
        Car addOneGPS = new Carparts("GPS", 5000);
        Car spoiler = new Carparts("Spoiler", 4000);
        Car subwoofer = new Carparts("Subwoofer", 7500);

        addOneOptions.add(addOneGPS);
        addOneOptions.add(spoiler);
        addOneOptions.add(subwoofer);

        createButtons(addOneOptions);
    }

    public void createFile(){
        //ONLY USED FIRST TIME TO CREATE THE FILE
        Path filsti = Paths.get("AddOnes.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, addOneOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void openFile(){
        Path path = Paths.get("AddOnes.jobj");
        addOneOptions = FileOpenerJobj.openFile(path);
        createButtons(addOneOptions);
    }

    @FXML
    private void switchToQuinary() throws IOException {
        for(int i = 0; i<addOneOptions.size();i++){
            if(addOneButtons.get(i).isSelected()){
                for(int j = 0; j<addOneOptions.size(); j++){
                    if(addOneButtons.get(i).getText().equals(addOneOptions.get(j).getName())){
                        BuildingNewCar.addStatic(addOneOptions.get(j));
                        System.out.println(addOneOptions.get(j).getName());
                    }
                }
            }
        }

        App.setRoot("05-quinary");
    }

}