package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetColor_Controller implements Initializable {
    final ToggleGroup colorGroup = new ToggleGroup();
    List<Carparts> colorOptions = new ArrayList<>();
    List<RadioButton> colorButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setColors();
        createFile();
        //openFile();

        Platform.runLater(() -> {
            Stage stage = (Stage) colorVbox.getScene().getWindow();
            stage.setWidth(600);
        });
    }

    @FXML
    private VBox colorVbox;

    @FXML
    private VBox vboxColor;

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

    @FXML
    void backBtn() throws IOException{
        App.setRoot("SetWheels");
    }




    //Only used for creating/setting the initial .jobj file
    public void setColors(){
        Carparts red = new Carparts("Red", 5000);
        Carparts blue = new Carparts("Blue", 2500);
        Carparts yellow = new Carparts("Yellow", 2500);
        Carparts black = new Carparts("Black", 0);
        Carparts green = new Carparts("Green", 3500);

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