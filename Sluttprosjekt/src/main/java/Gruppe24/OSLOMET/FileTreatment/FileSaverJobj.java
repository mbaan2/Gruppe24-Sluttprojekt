package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSaverJobj {
    public static void SaveCarCategory(Path path, ObservableList<NewCar> c) throws IOException {
        try {
            OutputStream os = Files.newOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(c);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static void SaveCarCategoryArray(Path path, Car[] c) throws IOException {
        try {
            OutputStream os = new FileOutputStream(new File("cars.jobj"), true);
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(c);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }




    public static void SavingCarArray(String path, ObservableList<NewCar> carList) throws IOException {
        ObjectOutputStream os1 = new ObjectOutputStream(new FileOutputStream(path));
        os1.writeObject(carList.get(0));
        os1.close();

        for(int i = 1; i < carList.size(); i++){
            ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream(path, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            os2.writeObject(carList.get(i));
            //NOT SURE IF WE HAVE TO CLOSE IT SO THIS IS SOMETHING WE NEED TO TEST!
            os2.close();
        }
    }

    public static void addingOnlyOneCarObject (String path, NewCar carObject) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path, true)) {
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        };
        os.writeObject(carObject);
        os.close();
    }
}
