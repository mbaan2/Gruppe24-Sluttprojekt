package Gruppe24.OSLOMET.FileTreatment;

import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;

public class FileChooserTxt{
    public static File fileChooserTxt(String initialFileName){
        FileChooser fc = new FileChooser();
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fc.setInitialFileName(initialFileName);
        fc.setInitialDirectory(new File(currentDir));

        int i = SaveOrLoad.showAlert();
        if (i==2){
            File selectedFile = fc.showOpenDialog(null);
            return selectedFile;
        } else if (i==1){
            File selectedFile = fc.showSaveDialog(null);
            return selectedFile;
        } else {
            System.err.println("You did not select a valid file");
            return null;
        }
    }
}
