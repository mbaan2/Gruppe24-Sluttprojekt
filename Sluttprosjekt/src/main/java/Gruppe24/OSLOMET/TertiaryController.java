package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TertiaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openFile();
    }

    @FXML
    private VBox vboxColor;

    final ToggleGroup colorGroup = new ToggleGroup();
    List<Car> colorOptions = new ArrayList<>();
    List<RadioButton> colorButtons = new ArrayList<>();

    public void createButtons(List<Car> color){
        colorButtons = LoadingValuesOnScreen.creatingList(colorButtons, colorOptions, colorGroup);
        vboxColor = LoadingValuesOnScreen.returnVbox(colorButtons, vboxColor);
        colorButtons.get(0).setSelected(true);
    }

    public void openFile(){
        Path path = Paths.get("color.jobj");
        colorOptions = FileOpenerJobj.openFile(path);
        createButtons(colorOptions);
    }

    @FXML
    private void switchToQuaternary() throws IOException {
        for(int i = 0; i<colorOptions.size();i++){
            if(colorButtons.get(i).isSelected()){
                for(int j = 0; j<colorOptions.size(); j++){
                    if(colorButtons.get(i).getText().equals(colorOptions.get(j).getName())){
                        BuildingNewCar.addStatic(colorOptions.get(j));
                        System.out.println(colorOptions.get(j).getName());
                    }
                }
            }
        }

        App.setRoot("04-quaternary");
    }






    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setWheels(){
        Car red = new Carparts("Red", 5000);
        Car blue = new Carparts("Blue", 2500);
        Car yellow = new Carparts("Yellow", 2500);
        Car black = new Carparts("Black", 0);
        Car green = new Carparts("Green", 3500);

        colorOptions.add(black);
        colorOptions.add(green);
        colorOptions.add(red);
        colorOptions.add(blue);
        colorOptions.add(yellow);

        createButtons(colorOptions);
    }

    public void createFile(){
        Path filsti = Paths.get("color.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, colorOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}