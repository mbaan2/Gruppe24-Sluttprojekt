package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadCategory {
    static List<Carparts> carCategory = new ArrayList<>();
    final static String fuelCHB = "Fuel type";
    final static String wheelsCHB = "Wheels";
    final static String colorCHB = "Color";
    final static String addOnsCHB = "Add-ons";

    public static List<Carparts> loadCategory(String value) throws IOException, ClassNotFoundException {
        switch (value) {
            case fuelCHB: {
                loading(Paths.get(StandardPaths.fuelPath));
                break;
            }
            case wheelsCHB: {
                loading(Paths.get(StandardPaths.wheelPath));
                break;
            }
            case colorCHB: {
                loading(Paths.get(StandardPaths.colorPath));
                break;
            }
            case addOnsCHB: {
                loading(Paths.get(StandardPaths.addonPath));
                break;
            }
        }
        return carCategory;
    }
    public static List<Carparts> loading(Path path) throws ClassNotFoundException, IOException {
        try {
            carCategory = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage());
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
        return carCategory;
    }

}
