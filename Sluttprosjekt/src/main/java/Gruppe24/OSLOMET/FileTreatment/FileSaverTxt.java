package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.BuildingNewCar;
import Gruppe24.OSLOMET.Car.CarObj;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSaverTxt{
    public void saveTxtFile(CarObj car) throws IOException{
        try {
            File selectedFile = FileChooserTxt.fileChooserTxt("MyCars.txt");

            FileWriter fw = new FileWriter(selectedFile, true);

            fw.write(car.saveableData(car.getId()));
            fw.close();

        } catch (Exception e) {
            throw new IOException("Failed to write to this file");
        }
    }
}
