package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
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

public class SetColor_Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColors();
        createFile();
        //openFile();
    }

    @FXML
    private VBox vboxColor;

    final ToggleGroup colorGroup = new ToggleGroup();
    List<Car> colorOptions = new ArrayList<>();
    List<RadioButton> colorButtons = new ArrayList<>();

    public void createButtons(){
        LoadingValuesOnScreen.creatingList(colorButtons, colorOptions, colorGroup);
        LoadingValuesOnScreen.returnVbox(colorButtons, vboxColor);

        if(App.car.color == null) {
            colorButtons.get(0).setSelected(true);
        } else{
            for(int i=0; i <colorOptions.size();i++){
                if(App.car.getColor().getName().equals(colorOptions.get(i).getName())){
                    colorButtons.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.colorPath);
        colorOptions = FileOpenerJobj.openFile(path);
        createButtons();
    }

    @FXML
    private void switchToQuaternary() throws IOException {
        for(int i = 0; i<colorOptions.size();i++){
            if(colorButtons.get(i).isSelected()){
                App.car.setColor(colorOptions.get(i));
            }
        }

        App.setRoot("SetAddons");
    }






    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setColors(){
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

        createButtons();
    }

    public void createFile(){
        Path filsti = Paths.get(StandardPaths.colorPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, colorOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}