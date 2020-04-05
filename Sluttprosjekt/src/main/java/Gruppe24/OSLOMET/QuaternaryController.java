package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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

    public void openFile(){
        Path path = Paths.get("AddOnes.jobj");
        addOneOptions = FileOpenerJobj.openFile(path);
        createButtons(addOneOptions);
    }

    @FXML
    private void switchToQuinary() throws IOException {
        Car addones = new CarCategory("addones");
        for(int i = 0; i<addOneOptions.size();i++){
            if(addOneButtons.get(i).isSelected()){
                for(int j = 0; j<addOneOptions.size(); j++){
                    if(addOneButtons.get(i).getText().equals(addOneOptions.get(j).getName())){
                        addones.add(addOneOptions.get(j));
                    }
                }
            }
        }
        BuildingNewCar.set(3, addones);

        App.setRoot("05-quinary");
    }








    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setWheels(){
        Car addOneGPS = new Carparts("GPS", 5000);
        Car spoiler = new Carparts("Spoiler", 4000);
        Car subwoofer = new Carparts("Subwoofer", 7500);

        addOneOptions.add(addOneGPS);
        addOneOptions.add(spoiler);
        addOneOptions.add(subwoofer);

        createButtons(addOneOptions);
    }

    public void createFile(){
        Path filsti = Paths.get("AddOnes.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, addOneOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}