package Gruppe24.OSLOMET.Controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetFuel_Controller implements Initializable {
    final ToggleGroup fuelGroup = new ToggleGroup();
    List<Carparts> fuelOptions = new ArrayList<>();
    List<RadioButton> fuelButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //createFile();
        openFile();
        createButtons();

        Platform.runLater(() -> {
            Stage stage = (Stage) fuelVbox.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }

    @FXML
    private VBox fuelVbox;

    @FXML
    private VBox vboxFuel;

    public void createButtons(){
        LoadingValuesOnScreen.creatingList(fuelButtons, fuelOptions, fuelGroup);
        LoadingValuesOnScreen.returnVbox(fuelButtons, vboxFuel);

        if(App.car.fuel == null) {
            fuelButtons.get(0).setSelected(true);
        } else{
            for(int i=0; i <fuelOptions.size();i++){
                if(App.car.getFuel().getName().equals(fuelOptions.get(i).getName())){
                    fuelButtons.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.fuelPath);
        try {
            fuelOptions = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void btnToWheels(ActionEvent event) {
        for(int i = 0; i<fuelOptions.size();i++){
            if(fuelButtons.get(i).isSelected()){
                App.car.setFuel(fuelOptions.get(i));
            }
        }
        try{
            App.setRoot("SetWheels");
        } catch (LoadException e){
            System.err.println(e.getMessage() + "Error in file, please contact superUSer to restore the system");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void backBtn(){
        try{
            App.setRoot("WelcomeScreen");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }




    //Only used for creating/setting the initial .jobj file
    public void setFuel(){
        Carparts diesel = new Carparts("Diesel", 20_000);
        Carparts gasoline = new Carparts("Gasoline", 15_000);
        Carparts electric = new Carparts("Electric", 35_000);

        fuelOptions.add(gasoline);
        fuelOptions.add(diesel);
        fuelOptions.add(electric);
    }

    public void createFile(){
        setFuel();
        Path filsti = Paths.get(StandardPaths.fuelPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, fuelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
