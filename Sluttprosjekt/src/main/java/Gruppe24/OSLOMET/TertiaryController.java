package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileHandling.FileOpenerJobj;
import Gruppe24.OSLOMET.FileHandling.FileSaverJobj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

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
    private HBox hboxColor;

    @FXML
    private Button tertiaryButton;

    final ToggleGroup colorGroup = new ToggleGroup();
    List<Car> colorOptions = new ArrayList<>();
    List<RadioButton> colorButtons = new ArrayList<>();

    public void createButtons(List<Car> color){
        for(int i = 0; i< color.size(); i++){
            String str = "";
            str = color.get(i).getName();
            RadioButton newColors = new RadioButton(str);
            newColors.setToggleGroup(colorGroup);
            colorButtons.add(newColors);
        }
        hboxColor.getChildren().clear();
        hboxColor.getChildren().addAll(colorButtons);
        colorButtons.get(0).setSelected(true);
    }

    public void setWheels(){
        //ONLY USED FIRST TIME TO SET THE VALUES IN THE DOCUMENT
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
        //ONLY USED FIRST TIME TO CREATE THE FILE
        Path filsti = Paths.get("color.jobj");
        try {
            FileSaverJobj.SaveCarCategory(filsti, colorOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
}