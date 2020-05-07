package Gruppe24.OSLOMET.DataValidation;

import javafx.scene.control.Alert;

public class Errors{
    public static Alert screenLoadAlert(){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Loading error");
        error.setHeaderText("Fatal error");
        error.setContentText("There is an error in loading the next screen.\nPlease contact your developer.");

        return error;
    }

    public static Alert fileLoadAlert(String fileId){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(fileId + " error");
        error.setHeaderText("Could not load " + fileId);
        error.setContentText("There is a problem with the " + fileId + " file.\nContact your administrator.");

        return error;
    }

    public static Alert processFailAlert(String processName){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(processName + " error");
        error.setContentText("Fatal error");
        error.setContentText("An error has occurred in the " + processName + " process.\nContact your administrator.");

        return error;
    }
}
