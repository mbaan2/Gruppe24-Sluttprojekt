package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Car;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileOpenerJobj {
    public static List<Car> openFile(Path path) {
        //Path path = Paths.get("filterliste.jobj");
        List<Car> carParts = new ArrayList<>();

        try (   InputStream in = Files.newInputStream(path);
                ObjectInputStream oin = new ObjectInputStream(in))
        {
            carParts = (List<Car>) oin.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return carParts;
    }
}
