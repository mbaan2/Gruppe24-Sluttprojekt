package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileImporterSuperuser implements FileImporter{

    @Override
    public void open(List<CarpartOption> carpartOptionList, Path filepath) throws IOException{
        try(InputStream is = Files.newInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(is)){
            ArrayList<CarpartOption> carpartOptions = (ArrayList<CarpartOption>) ois.readObject();
            carpartOptionList.clear();
            carpartOptions.addAll(carpartOptionList);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            throw new IOException("Could not load. Check stack trace.");
        }
    }
}
