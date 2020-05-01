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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SetFuel_Controller implements Initializable {

    final ToggleGroup fuelGroup = new ToggleGroup();
    List<Carparts> fuelOptions = new ArrayList<>();
    List<RadioButton> fuelButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFuel();
        createFile();
        //openFile();
    }

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

    @FXML
    void btnGasCar(ActionEvent event) {
        App.car.setFuel(new Carparts("Gasoline Car", 30_000));
        //txtGasCar.setStyle("-fx-background-color: #def2f1;");
        //txtGasCar.setTextFill(Color.valueOf("#17252a"));
        //txtElectricCar.setStyle("-fx-background-color: #17252a;");
        //txtElectricCar.setTextFill(Color.valueOf("#def2f1"));
        //txtDieselCar.setStyle("-fx-background-color: #17252a;");
        //txtDieselCar.setTextFill(Color.valueOf("#def2f1"));
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.fuelPath);
        fuelOptions = FileOpenerJobj.openFile(path);
        createButtons();
    }

    @FXML
    void switchToSecondary(ActionEvent event) throws IOException{

        for(int i = 0; i<fuelOptions.size();i++){
            if(fuelButtons.get(i).isSelected()){
                App.car.setFuel(fuelOptions.get(i));
            }
        }

        App.setRoot("SetWheels");
    }

    @FXML
    void backBtn() throws IOException{
        App.setRoot("WelcomeScreen");
    }



    //ONLY USED FOR CREATING THE .JOBJ FILE
    public void setFuel(){
        Carparts diesel = new Carparts("Diesel", 20_000);
        Carparts gasoline = new Carparts("Gasoline", 15_00);
        Carparts electric = new Carparts("Electric", 35_000);


        fuelOptions.add(gasoline);
        fuelOptions.add(diesel);
        fuelOptions.add(electric);


        createButtons();
    }

    public void createFile(){
        Path filsti = Paths.get(StandardPaths.fuelPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, fuelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
