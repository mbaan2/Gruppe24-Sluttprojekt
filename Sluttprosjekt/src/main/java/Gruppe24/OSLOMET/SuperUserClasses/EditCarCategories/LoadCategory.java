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
    final static String fuelCHB = "Fuel type";
    final static String wheelsCHB = "Wheels";
    final static String colorCHB = "Color";
    final static String addOnsCHB = "Add-ons";

    public static List<Carparts> loadCategory(String value, Label lbl){
        List<Carparts> carCategory = new ArrayList<>();
        switch (value) {
            case fuelCHB: {
                Path path = Paths.get(StandardPaths.fuelPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    lbl.setText("Could not open fuel file. Please restore original settings.");
                }
                break;
            }
            case wheelsCHB: {
                Path path = Paths.get(StandardPaths.wheelPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    lbl.setText("Could not open wheels file. Please restore original settings.");

                }                break;
            }
            case colorCHB: {
                Path path = Paths.get(StandardPaths.colorPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    lbl.setText("Could not open color file. Please restore original settings.");
                }                break;
            }
            case addOnsCHB: {
                Path path = Paths.get(StandardPaths.addonPath);
                try {
                    carCategory = FileOpenerJobj.openFile(path);
                } catch (ClassNotFoundException | IOException e) {
                    lbl.setText("Could not open add-ons file. Please restore original settings.");
                }                break;
            }
        }

        return carCategory;
    }
}
