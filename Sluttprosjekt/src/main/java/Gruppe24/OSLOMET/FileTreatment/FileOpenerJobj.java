package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.NewCar;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileOpenerJobj {
    public static List<Car> openFile(Path path) {
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

    public static NewCar openFileArray(Path path) {
        NewCar car = new NewCar();

        try (   InputStream in = Files.newInputStream(path);
                ObjectInputStream oin = new ObjectInputStream(in))
        {
            car = (NewCar) oin.readObject();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return car;
    }


    public static HashMap<String, String> openFileHashMap(){
        HashMap<String, String> userBase = null;
        try (FileInputStream in = new FileInputStream(StandardPaths.usersJOBJPath);
             ObjectInputStream oin = new ObjectInputStream(in)) {
            userBase = (HashMap) oin.readObject();
            in.close();
            oin.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return userBase;
    }


    public static ArrayList<Car> openingCarArray(String path) throws IOException {
        ArrayList<Car> carList = new ArrayList<>();
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
        boolean moreObjects = true;

        while (moreObjects) {
            try {
                Car aCar = (Car) is.readObject();
                carList.add(aCar);
            } catch (Exception e){
                moreObjects = false;
            }
        }
        return carList;
    }

}
