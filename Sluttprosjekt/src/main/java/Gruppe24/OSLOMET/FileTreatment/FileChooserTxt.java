package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.DataValidation.Alerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

public class FileChooserTxt{
    public static File fileChooserTxt(String initialFileName){
        FileChooser fc = new FileChooser();
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fc.setInitialFileName(initialFileName);
        fc.setInitialDirectory(new File(currentDir));

        File selectedFile = fc.showOpenDialog(null);

        return selectedFile;
    }
}
