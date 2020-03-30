package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileImporterUser implements FileImporter{

    @Override
    public void open(List<CarpartOption> selectedCarParts, Path filepath) throws IOException{
        selectedCarParts.clear();
        try (BufferedReader br = Files.newBufferedReader(filepath)){
            String line;

            while ((line=br.readLine()) != null){
                //parseCar has to be created according to how we're going to handle this
                //is the user going to be able to save multiple cars in one file? Can they create multiple files?
                //are they going to have a file to which their cars are saved, and then it is immediately loaded when their profile is opened?
                selectedCarParts.add(parseCar(line));
            }
        }
    }
}
