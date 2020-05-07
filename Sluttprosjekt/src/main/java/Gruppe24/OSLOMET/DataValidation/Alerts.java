package Gruppe24.OSLOMET.DataValidation;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts{
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
        error.setHeaderText("Fatal error");
        error.setContentText("An error has occurred in the " + processName.toLowerCase() + " process.\nContact your administrator.");

        return error;
    }

    public static Alert processCancelled(String processName){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(processName + " cancelled");
        error.setHeaderText(processName + " cancelled");
        error.setContentText("Your " + processName.toLowerCase() + " process was cancelled.");

        return error;
    }

    public static Alert overrideORappend(){
        Alert overrideORappend = new Alert(Alert.AlertType.CONFIRMATION);
        overrideORappend.setTitle("File already exists");
        overrideORappend.setHeaderText("You selected an existing file");
        overrideORappend.setContentText("You selected an existing file.\nDo you want to override its contents or add to it?");

        return overrideORappend;
    }
}
