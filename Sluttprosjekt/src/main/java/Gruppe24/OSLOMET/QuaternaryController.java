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
    private VBox vboxAddOns;

    List<Car> addOnOptions = new ArrayList<>();
    List<CheckBox> addOnButtons = new ArrayList<>();

    public void createButtons(List<Car> addon){
        addOnButtons = LoadingValuesOnScreen.creatingList(addOnButtons, addOnOptions);
        vboxAddOns = LoadingValuesOnScreen.returnVbox(addOnButtons, vboxAddOns);
        //addOneButtons.get(0).setSelected(true);
    }

    public void openFile(){
        Path path = Paths.get("AddOns.jobj");
        addOnOptions = FileOpenerJobj.openFile(path);
        createButtons(addOnOptions);
    }

    @FXML
    private void switchToQuinary() throws IOException {
        Car addons = new CarCategory("addons");
        for(int i = 0; i< addOnOptions.size(); i++){
            if(addOnButtons.get(i).isSelected()){
                for(int j = 0; j< addOnOptions.size(); j++){
                    if(addOnButtons.get(i).getText().equals(addOnOptions.get(j).getName())){
                        addons.add(addOnOptions.get(j));
                    }
                }
            }
        }
        BuildingNewCar.set(3, addons);

        App.setRoot("05-quinary");
    }








    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setWheels(){
        Car addOneGPS = new Carparts("GPS", 5000);
        Car spoiler = new Carparts("Spoiler", 4000);
        Car subwoofer = new Carparts("Subwoofer", 7500);

        addOnOptions.add(addOneGPS);
        addOnOptions.add(spoiler);
        addOnOptions.add(subwoofer);

        createButtons(addOnOptions);
    }

    public void createFile(){
        Path filsti = Paths.get("AddOns.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, addOnOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}