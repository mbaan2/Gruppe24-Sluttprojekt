package Gruppe24.OSLOMET;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarBuild;
import Gruppe24.OSLOMET.Car.Carparts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class SecondaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWheels();
    }

    @FXML
    private HBox hboxx;

    final ToggleGroup group = new ToggleGroup();
    List<RadioButton> buttonList = new ArrayList<>();

    public void createButtons(CarBuild carbuild){
        //System.out.println(carbuild.size());

        for(int i = 0; i< carbuild.size(); i++){
            String wheels = "";
            wheels = carbuild.getNameIndex(i);
            //System.out.println(carbuild.getNameIndex(i));
            RadioButton newWheels = new RadioButton(wheels);
            newWheels.setToggleGroup(group);
            buttonList.add(newWheels);
        }
        hboxx.getChildren().clear();
        hboxx.getChildren().addAll(buttonList);
        buttonList.get(0).setSelected(true);

    }

    CarBuild wheels = new CarBuild("wheels");


    public void setWheels(){
        //Has to come from a file -> must be compatible with Excel
        Car wheelsBig = new Carparts("Big wheels", 5000);
        Car wheelsSmall = new Carparts("Small wheels", 0);
        Car wheelsMedium = new Carparts("Mediuem wheels", 2500);

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
        \
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


        for(int i=0; i<buttonList.size();i++){
            if(buttonList.get(i).isSelected()){
                for(int j = 0; j < wheels.size(); j++)
                if(buttonList.get(i).getText().equals(wheels.getNameIndex(j))){
                    BuildingNewCar.add(wheels.getElement(j));
                    System.out.println(wheels.getElement(j).getName() + " is added");
                    System.out.println(BuildingNewCar.getNameIndex(0));
                    System.out.println(BuildingNewCar.getCostPart(0));
                }


                }

            }




        App.setRoot("03-tertiary");
    }
}