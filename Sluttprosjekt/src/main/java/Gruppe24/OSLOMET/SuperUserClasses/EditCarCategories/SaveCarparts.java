package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveCarparts {
    final static String fuelCHB = "Fuel type";
    final static String wheelsCHB = "Wheels";
    final static String colorCHB = "Color";
    final static String addOnsCHB = "Add-ons";
    private static Object CarCategory;

    public static void saveChanges(List<Carparts> carList, String value, Label lbl) throws IOException {
        switch (value) {
            case fuelCHB: {
                saving(carList, Paths.get(StandardPaths.fuelPath));
                break;
            }
            case wheelsCHB: {
                saving(carList, Paths.get(StandardPaths.wheelPath));
                break;
            }
            case colorCHB: {
                saving(carList, Paths.get(StandardPaths.colorPath));
                break;
            }
            case addOnsCHB: {
                saving(carList, Paths.get(StandardPaths.addonPath));
                break;
            }
        }
    }

    public static void saving(List<Carparts> carCategory, Path filsti) throws IOException {
        try {
            FileSaverJobj.SaveCarCategory(filsti, carCategory);
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }

    }


    }
