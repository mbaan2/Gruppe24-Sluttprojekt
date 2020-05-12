package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
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
        openFile();
        createButtons();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) colorVbox.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(470);
            } catch (ExceptionInInitializerError e) {
                lblErrorColor.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox colorVbox, vboxColor;

    @FXML
    private Label lblErrorColor;


    public void createButtons(){
        LoadingValuesOnScreen.creatingList(colorButtons, colorOptions, colorGroup);
        LoadingValuesOnScreen.returnVbox(colorButtons, vboxColor);

        if(App.car.color == null) {
            colorButtons.get(0).setSelected(true);
        } else{
            boolean selected = false;
            for(int i=0; i <colorOptions.size();i++){
                if(App.car.getColor().getName().equals(colorOptions.get(i).getName())){
                    selected = true;
                    colorButtons.get(i).setSelected(true);
                    break;
                }
            }

            /*If value doesnt exist anymore, the system gives this error*/
            if(!selected) {
                colorButtons.get(0).setSelected(true);
                lblErrorColor.setText("Your previous selected color is not available, please contact the superUser or select a new color.");
            }
        }
    }

    public void openFile(){
        Path path = Paths.get(StandardPaths.colorPath);
        try {
            colorOptions = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            lblErrorColor.setText("An error occurred while your were in the program. Contact superUser to reset the carparts files");
        }
    }

    @FXML
    private void btnToAddons() {
        for(int i = 0; i<colorOptions.size();i++){
            if(colorButtons.get(i).isSelected()){
                App.car.setColor(colorOptions.get(i));
            }
        }

        try{
            App.setRoot("SetAddons");
        } catch (LoadException e){
            lblErrorColor.setText("Error in one of the Carpart files, please contact the superUser to restore the system.");
        } catch (IOException e){
            lblErrorColor.setText("An error has occurred, please contact the superUser.");
        } catch (IllegalStateException e){
            lblErrorColor.setText("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void backBtn() {
        for(int i = 0; i<colorOptions.size();i++){
            if(colorButtons.get(i).isSelected()){
                App.car.setColor(colorOptions.get(i));
            }
        }

        try{
            App.setRoot("SetWheels");
        } catch (IOException e){
            lblErrorColor.setText("An error has occurred, please contact the superUser");
        }
    }
}