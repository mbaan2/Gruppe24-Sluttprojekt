package Gruppe24.OSLOMET;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.Car.*;
import Gruppe24.OSLOMET.FileHandling.CarpartOptionToRadioButton;
import Gruppe24.OSLOMET.FileHandling.FileImporterSuperuser;
import Gruppe24.OSLOMET.FileHandling.FileSaverSuperuser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SecondaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            setWheels();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private HBox hboxx;

    final ToggleGroup wheelGroup = new ToggleGroup();
    List<CarpartOption> wheelOptions = new ArrayList<>();
    List<RadioButton> wheelButtons;

    public void createButtons(Car carbuild) throws IOException{
        FileImporterSuperuser impWheels = new FileImporterSuperuser();
        impWheels.open(wheelOptions, Paths.get("wheelOptions.jobj"));

        /* SETS INITIAL VALUES TO JOBJ (comment out 2 lines above)
        CarpartOption smallWheels = new CarpartOption("Small wheels", 0, "Small wheels");
        wheelOptions.add(smallWheels);
        CarpartOption mediumWheels = new CarpartOption("Medium wheels", 2500, "Medium wheels");
        wheelOptions.add(mediumWheels);
        CarpartOption bigWheels = new CarpartOption("Big wheels", 5000, "Big wheels");
        wheelOptions.add(bigWheels);
        FileSaverSuperuser wheelSave = new FileSaverSuperuser();
        wheelSave.save(wheelOptions, "wheelOptions");*/

        CarpartOptionToRadioButton converter = new CarpartOptionToRadioButton();

        wheelButtons = converter.optionToButtonList(wheelOptions);
        for (RadioButton btn : wheelButtons){
            btn.setToggleGroup(wheelGroup);
        }
        hboxx.getChildren().clear();
        hboxx.getChildren().addAll(wheelButtons);
        wheelButtons.get(0).setSelected(true);
    }

    Car wheels = new CarCategory("wheels");

    //Would comment out everything from here down to btnSmallWheels

    public void setWheels() throws IOException{
        //Has to come from a file -> must be compatible with Excel
        Car wheelsBig = new Carparts("Big wheels", 5000);
        Car wheelsSmall = new Carparts("Small wheels", 0);
        Car wheelsMedium = new Carparts("Medium wheels", 2500);

        wheels.add(wheelsBig);
        wheels.add(wheelsMedium);
        wheels.add(wheelsSmall);

        createButtons(wheels);
    }


    @FXML
    void btnLargeWheels(ActionEvent event) {
       /* Carparts largeWheels = new Carparts("Large Wheels", 5000);
        BuildingNewCar.add(largeWheels);
        System.out.println("added");
        */
    }

    @FXML
    void btnMediumWheels(ActionEvent event) {
        /*Carparts mediumWheels = new Carparts("Medium Wheels", 2500);
        BuildingNewCar.add(mediumWheels);
         */
    }

    @FXML
    void btnSmallWheels(ActionEvent event) {
        /*Carparts smallWheels = new Carparts("Small Wheels", 0);
        BuildingNewCar.add(smallWheels);
         */
    }



    @FXML
    private void switchToTertiary() throws IOException {


        for(int i = 0; i< wheelButtons.size(); i++){
            if(wheelButtons.get(i).isSelected()){
                for(int j = 0; j < wheels.size(); j++)
                if(wheelButtons.get(i).getText().equals(wheels.getNameIndex(j))){
                    BuildingNewCar.addStatic(wheels.getElement(j));
                }
             }
        }

        App.setRoot("03-tertiary");
    }
}