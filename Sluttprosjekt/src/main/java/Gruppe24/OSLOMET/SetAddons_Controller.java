package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetAddons_Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAddOns();
        createFile();
        //openFile();
    }

    @FXML
    private VBox vboxAddOns;

    List<Car> addOnOptions = new ArrayList<>();
    List<CheckBox> addOnButtons = new ArrayList<>();

    public void createButtons(List<Car> addon) {
        LoadingValuesOnScreen.creatingList(addOnButtons, addOnOptions);
        LoadingValuesOnScreen.returnVbox(addOnButtons, vboxAddOns);

        if (App.car.addons != null) {
            for (int i = 0; i < addOnOptions.size(); i++) {
                for (int j = 0; j < App.car.getAddons().size(); j++) {
                    if (App.car.getAddons().getElement(j).getName().equals(addOnOptions.get(i).getName())) {
                        addOnButtons.get(i).setSelected(true);
                    }
                }
            }
        }
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.addonPath);
        addOnOptions = FileOpenerJobj.openFile(path);
        createButtons(addOnOptions);
    }

    @FXML
    private void switchToQuinary() throws IOException {
        CarCategory addons = new CarCategory("addons");
        addons.clear();
        for(int i = 0; i< addOnOptions.size(); i++){
            if(addOnButtons.get(i).isSelected()){
              addons.add(addOnOptions.get(i));
            }
        }
        App.car.setAddons(addons);
        App.setRoot("Summary");
    }




    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setAddOns(){
        Car addOneGPS = new Carparts("GPS", 5000);
        Car spoiler = new Carparts("Spoiler", 4000);
        Car subwoofer = new Carparts("Subwoofer", 7500);

        addOnOptions.add(addOneGPS);
        addOnOptions.add(spoiler);
        addOnOptions.add(subwoofer);

        createButtons(addOnOptions);
    }

    public void createFile(){
        Path filsti = Paths.get(StandardPaths.addonPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, addOnOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}