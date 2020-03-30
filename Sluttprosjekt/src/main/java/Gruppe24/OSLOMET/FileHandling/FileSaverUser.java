package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileSaverUser implements FileSaver{
    @Override
    public void save(List<CarpartOption> selectedCarpartOptions, String carInfo) throws IOException{
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text files", "*txt"));
        File selectedFile = fc.showOpenDialog(null);
        //we need to decide if the user is going to have one automatic file for all their cars or will be able to save more than one.
        //Check fileImporterUser for more questions.
        //This should also define the structure of carInfo, which will need to be elaborated.
        Files.write(selectedFile.toPath(), carInfo.getBytes());
    }
}
