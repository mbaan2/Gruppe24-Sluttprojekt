package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Car;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileSaverJobj {
    public static void SaveCarCategory(Path path, List<Car> c) throws IOException {
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
}
