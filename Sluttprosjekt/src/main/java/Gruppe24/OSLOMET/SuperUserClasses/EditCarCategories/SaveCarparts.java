package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveCarparts {
    final static String fuelCHB = "Fuel type";
    final static String wheelsCHB = "Wheels";
    final static String colorCHB = "Color";
    final static String addOnsCHB = "Add-ons";

    public static void saveChanges(List<Carparts> carCategory, String value){
        switch (value) {
            case fuelCHB: {
                Path filsti = Paths.get(StandardPaths.fuelPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case wheelsCHB: {
                Path filsti = Paths.get(StandardPaths.wheelPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case colorCHB: {
                Path filsti = Paths.get(StandardPaths.colorPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case addOnsCHB: {
                Path filsti = Paths.get(StandardPaths.addonPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
