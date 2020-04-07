package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Car;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static Car[] openFileArray(Path path) {
        //Path path = Paths.get("filterliste.jobj");
        Car[] car = new Car[5];

        try (   InputStream in = Files.newInputStream(path);
                ObjectInputStream oin = new ObjectInputStream(in))
        {
            car = (Car[]) oin.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return car;
    }


    public static HashMap<String, String> openFileHashMap(){
        HashMap<String, String> userBase = null;
        try (FileInputStream in = new FileInputStream("users.jobj");
             ObjectInputStream oin = new ObjectInputStream(in)) {
            userBase = (HashMap) oin.readObject();
            in.close();
            oin.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return userBase;
    }
}
