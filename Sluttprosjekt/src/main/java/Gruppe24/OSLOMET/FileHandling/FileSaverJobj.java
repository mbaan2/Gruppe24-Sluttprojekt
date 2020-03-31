package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
}
