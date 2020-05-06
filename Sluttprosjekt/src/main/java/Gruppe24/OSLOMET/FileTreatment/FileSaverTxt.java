package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.NewCar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaverTxt{
    public void saveTxtFile(NewCar car) throws IOException{
        try {
            File selectedFile = FileChooserTxt.fileChooserTxt("MyCars.txt");

            FileWriter fw = new FileWriter(selectedFile, true);

            fw.write(car.savableData());
            fw.close();

        } catch (Exception e) {
            throw new IOException("Failed to write to this file");
        }
    }
}
