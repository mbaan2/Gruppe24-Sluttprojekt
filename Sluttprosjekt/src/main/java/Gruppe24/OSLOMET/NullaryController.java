package Gruppe24.OSLOMET;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NullaryController implements Initializable {

    @FXML
    void btnStartBuilding(ActionEvent event) throws IOException{
        App.setRoot("01-primary");
    }

    @FXML
    void btnLoadCars(ActionEvent event) throws IOException{
        App.setRoot("loadedCars");
    }

    @FXML
    void logoutBtn() throws IOException {
        App.setRoot("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
}