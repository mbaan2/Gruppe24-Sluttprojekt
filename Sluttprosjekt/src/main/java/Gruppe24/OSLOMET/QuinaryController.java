package Gruppe24.OSLOMET;

import javafx.fxml.FXML;

import java.io.IOException;

public class QuinaryController{

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}