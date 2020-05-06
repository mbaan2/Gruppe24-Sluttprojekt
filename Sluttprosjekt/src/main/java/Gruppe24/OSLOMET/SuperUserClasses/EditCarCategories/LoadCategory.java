package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import java.io.IOException;
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
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    System.err.println(e.getMessage());
                }
                break;
            }
            case wheelsCHB: {
                Path path = Paths.get(StandardPaths.wheelPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    System.err.println(e.getMessage());
                }                break;
            }
            case colorCHB: {
                Path path = Paths.get(StandardPaths.colorPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    System.err.println(e.getMessage());
                }                break;
            }
            case addOnesCHB: {
                Path path = Paths.get(StandardPaths.addonPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    System.err.println(e.getMessage());
                }                break;
            }
        }

        return carCategory;
    }
}
