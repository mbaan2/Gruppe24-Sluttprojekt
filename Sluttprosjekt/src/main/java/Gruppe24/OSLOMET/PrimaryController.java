package Gruppe24.OSLOMET;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Gruppe24.OSLOMET.FileTreatment.FileOpenerTxt;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PrimaryController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createButtons();
    }

    List<Button> buttonList = new ArrayList<>();
    public void createButtons(){
        String fuel = "";
        fuel = "Gasoline";
        buttonList.add(new Button(fuel));
        fuel = "Diesel";
        buttonList.add(new Button(fuel));
        fuel = "Electric";
        buttonList.add(new Button(fuel));
        hboxx.getChildren().clear();
        hboxx.getChildren().addAll(buttonList);
    }

    @FXML
    private HBox hboxx;
    /*Based on: https://stackoverflow.com/questions/44949774/javafx-how-to-add-elements-eg-buttons-dynamically-created-from-items-stored-in*/

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("02-secondary");
    }

    @FXML
    void logoutBtn() throws IOException {
        App.setRoot("login");
    }

    @FXML
    void btnLoadCars(ActionEvent event) throws IOException{
        App.setRoot("loadedCars");
    }
}
