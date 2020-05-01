package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeScreen_Controller implements Initializable {

    @FXML
    void btnStartBuilding(ActionEvent event) throws IOException{
        App.setRoot("SetFuel");
    }

    @FXML
    void btnLoadCars(ActionEvent event) throws IOException{
        App.setRoot("UserCarView");
    }

    @FXML
    void logoutBtn() throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
}