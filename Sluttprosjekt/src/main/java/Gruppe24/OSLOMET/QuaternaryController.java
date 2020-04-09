package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.NewCar;
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
        LoadingValuesOnScreen.creatingList(addOnButtons, addOnOptions);
        LoadingValuesOnScreen.returnVbox(addOnButtons, vboxAddOns);

        //We have to add to function to check for prelaoded data see secondaryController however nothing is selected in there is no predata

            for(int i=0; i <addOnOptions.size();i++){
                for(int j=0; j<NewCar.sizeAddone(); j++) {
                    if (NewCar.getNameIndexAddoneStatic(j).equals(addOnOptions.get(i).getName())) {
                        addOnButtons.get(i).setSelected(true);
                    }
                }
            }
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
              addons.add(addOnOptions.get(i));
            }
        }
        NewCar.set(3, addons);

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