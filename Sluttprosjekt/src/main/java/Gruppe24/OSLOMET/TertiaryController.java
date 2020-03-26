package Gruppe24.OSLOMET;

import javafx.fxml.FXML;

import java.io.IOException;

public class TertiaryController{

    @FXML
    private void switchToQuaternary() throws IOException {
        App.setRoot("quaternary");
    }
}