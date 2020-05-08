package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileOpenerJobj {
    public static List<Carparts> openFile(Path path) throws IOException, ClassNotFoundException {
        List<Carparts> carParts = new ArrayList<>();

        try (   InputStream in = Files.newInputStream(path);
                ObjectInputStream oin = new ObjectInputStream(in))
        {
            carParts = (List<Carparts>) oin.readObject();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("The file that is trying to be opened has not been properly saved. Please contact the developer.");
        } catch (IOException e){
            throw new IOException("Corrupted file please contact the superUser for a reset.");
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

    public static List<String> openSecretQList() {
        List<String> choiceBoxList = new ArrayList<>();
        try (FileInputStream in = new FileInputStream(StandardPaths.secretQPath);
             ObjectInputStream oin = new ObjectInputStream(in)) {
            choiceBoxList = (List<String>) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return choiceBoxList;
    }

    public static ArrayList<NewCar> openingCarArray(String path) throws IOException {
        ArrayList<NewCar> carList = new ArrayList<>();
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
        boolean moreObjects = true;

        while (moreObjects) {
            try {
                NewCar aCar = (NewCar) is.readObject();
                carList.add(aCar);
            } catch (Exception e){
                moreObjects = false;
            }
        }
        return carList;
    }
}

