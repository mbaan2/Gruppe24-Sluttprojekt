package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadCategory {
    final static String fuelCHB = "Fuel type";
    final static String wheelsCHB = "Wheels";
    final static String colorCHB = "Color";
    final static String addOnesCHB = "Addons";

    public static List<Carparts> loadCategory(String value){
        List<Carparts> carCategory = new ArrayList<>();
        switch (value) {
            case fuelCHB:{
                Path path = Paths.get(StandardPaths.fuelPath);
                carCategory = FileOpenerJobj.openFile(path);
                break;
            }
            case wheelsCHB: {
                Path path = Paths.get(StandardPaths.wheelPath);
                carCategory = FileOpenerJobj.openFile(path);
                break;
            }
            case colorCHB: {
                Path path = Paths.get(StandardPaths.colorPath);
                carCategory = FileOpenerJobj.openFile(path);
                break;
            }
            case addOnesCHB: {
                Path path = Paths.get(StandardPaths.addonPath);
                carCategory = FileOpenerJobj.openFile(path);
                break;
            }
        }

        return carCategory;
    }
}
