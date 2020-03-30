package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileSaverSuperuser implements FileSaver{
    @Override
    public void save(List<CarpartOption> carpartOptionList, String carpartOptionListName) throws IOException{
        Path path = Paths.get(carpartOptionListName +".jobj");
        try(OutputStream os = Files.newOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(os)){
            out.writeObject(carpartOptionList);
        }
    }
}
