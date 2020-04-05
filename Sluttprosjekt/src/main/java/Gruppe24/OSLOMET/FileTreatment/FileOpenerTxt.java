package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.CarObj;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileOpenerTxt{
    public void open() throws IOException{
        FileChooser fc = new FileChooser();
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fc.setInitialDirectory(new File(currentDir));

        int i = SaveOrLoad.showAlert();
        File selectedFile = fc.showOpenDialog(null);
        ArrayList<CarObj> carList = new ArrayList<>();

        try(BufferedReader br = Files.newBufferedReader(selectedFile.toPath())){
            String line;

            while((line = br.readLine()) != null){
                carList.add(ParseCar.parseCar(line));
            }
        }
    }
}