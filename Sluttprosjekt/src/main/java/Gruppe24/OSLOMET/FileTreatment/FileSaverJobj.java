package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class FileSaverJobj {
    public static void SaveCarCategory(Path path, List<Carparts> list) throws IOException {
        try {
            OutputStream os = Files.newOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(list);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static void SavingCarArray(String path, List<NewCar> carList) throws IOException {
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

    public static void SaveUser(HashMap<String, String> userList) throws FileNotFoundException, IOException {
        try {
            FileOutputStream os = new FileOutputStream(StandardPaths.usersJOBJPath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(userList);
            oos.flush();
            oos.close();
            os.close();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
