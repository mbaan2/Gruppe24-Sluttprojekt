package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.Alerts;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class FileSaverTxt{
    public void testExisting(ObservableList<NewCar> carsToAdd) throws IOException{
        try {
            System.out.println("In try");
            File selectedFile = FileChooserTxt.fileChooserTxt("MyCars.txt");
            if (selectedFile.exists()){
                System.out.println("Selected file exists");
                Alert appendORoverride = Alerts.overrideORappend();
                ButtonType append = new ButtonType("Append");
                ButtonType override = new ButtonType("Override");
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                appendORoverride.getButtonTypes().setAll(append, override, cancel);

                Optional<ButtonType> choice = appendORoverride.showAndWait();
                if (choice.get() == append){
                    System.out.println("In append");
                    FileWriter fw = new FileWriter(selectedFile, true);
                    for (NewCar c: carsToAdd){
                        System.out.println("In newcar");
                        int i = 1;
                        FileSaverTxt.append(c, fw);
                        System.out.println("Round " + 1 + ": appended");
                        i++;
                    }
                } else if (choice.get() == override){
                    FileWriter fw = new FileWriter(selectedFile, false);
                    FileSaverTxt.override(carsToAdd.get(0), fw);

                    for (int i=1; i<carsToAdd.size(); i++){
                        FileSaverTxt.append(carsToAdd.get(i), fw);
                    }
                } else {
                    Alerts.processCancelled("Saving");
                }
            } else {
                File newFile = new File("");
                FileWriter fw = new FileWriter(selectedFile);
                for (NewCar c: carsToAdd){
                    FileSaverTxt.append(c, fw);
                }
            }
        } catch (Exception e) {
            Alerts.processFailAlert("Saving");
            throw new IOException("Failed to write to this file");
        }
    }

    private static void append(NewCar car, FileWriter fw) throws IOException{
        try{
            fw.write(car.savableData());
        } catch (Exception e){
            Alerts.processFailAlert("Saving");
            throw new IOException("Failed to write to this file");
        }
    }

    private static void override(NewCar car, FileWriter fw) throws IOException{
        try{
            fw.write(car.savableData());
        } catch (Exception e){
            Alerts.processFailAlert("Saving");
            throw new IOException("Failed to write to this file");
        }
    }
}
