package Gruppe24.OSLOMET.FileTreatment;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SaveOrLoad{
    public static int showAlert(){
        int i = 0;

        Alert saveOrLoad = new Alert(Alert.AlertType.CONFIRMATION);
        saveOrLoad.setTitle("Save to new or existing file?");
        saveOrLoad.setHeaderText("Do you want to save your car to a new or existing file?");

        ButtonType newFile = new ButtonType("New file");
        ButtonType existingFile = new ButtonType("Existing file");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        saveOrLoad.getButtonTypes().setAll(newFile, existingFile, cancel);

        Optional<ButtonType> choice = saveOrLoad.showAndWait();
        if (choice.get() == newFile){
            i = 1;
        } else if (choice.get() == existingFile){
            i = 2;
        } else {
            i = 0;
        }

        return i;
    }
    //Based on: https://code.makery.ch/blog/javafx-dialogs-official/
}
