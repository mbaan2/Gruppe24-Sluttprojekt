package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.CarCategory;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetAddons_Controller implements Initializable {
    static List<Carparts> addOnOptions = new ArrayList<>();
    List<CheckBox> addOnButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //createFile();
        openFile();
        createButtons();


        Platform.runLater(() -> {
            Stage stage = (Stage) addonsVbox.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(470);
        });
    }

    @FXML
    private VBox vboxAddOns;

    @FXML
    private VBox addonsVbox;

    @FXML
    private Label lblErrorAddons;

    public void createButtons() {
        LoadingValuesOnScreen.creatingList(addOnButtons, addOnOptions);
        LoadingValuesOnScreen.returnVbox(addOnButtons, vboxAddOns);
        if(App.car.getAddons() == null){
            addOnButtons.get(0).setSelected(false);
        }
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
        try {
            addOnOptions = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            lblErrorAddons.setText("An error occurred while your were in the program. Contact superUser to reset carpart files");
        }
    }

    @FXML
    public void btnToSummary(ActionEvent actionEvent){
        CarCategory addons = new CarCategory("addons");
        addons.clear();

        for(int i = 0; i < addOnOptions.size(); i++){
            if(addOnButtons.get(i).isSelected()){
                addons.add(addOnOptions.get(i));
            }
        }
        App.car.setAddons(addons);

        try{
            App.setRoot("Summary");
        } catch (LoadException e){
            lblErrorAddons.setText("Error in one of the Carpart files, please contact the superUser to restore the system.");
        } catch (IOException e){
            lblErrorAddons.setText("An error has occurred, please contact the superUser.");
        } catch (IllegalStateException e){
            lblErrorAddons.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void backBtn(){
        try{
            App.setRoot("SetColors");
        } catch (IOException e){
            lblErrorAddons.setText("An error has occurred, please contact the superUser");
        }
    }




    //Only used for creating/setting the initial .jobj file
    public void setAddOns(){
        Carparts addOneGPS = new Carparts("GPS", 5000);
        Carparts spoiler = new Carparts("Spoiler", 4000);
        Carparts subwoofer = new Carparts("Subwoofer", 7500);

        addOnOptions.add(addOneGPS);
        addOnOptions.add(spoiler);
        addOnOptions.add(subwoofer);
    }

    public void createFile(){
        setAddOns();
        Path filsti = Paths.get(StandardPaths.addonPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, addOnOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}