package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.ObservableList;

public class FormatCar {

    public static String formatCar(ObservableList<NewCar> carList) {
        String out = "";
        for(int i = 0; i < carList.size(); i++) {
            out += "User:\t" + carList.get(i).getUser() + "\nName:\t" + carList.get(i).getName() + "\nCost:\t\t" + carList.get(i).getCost() + "\n";
            out += "Fuel:\t" + carList.get(i).getFuel().getName() + "\t" + carList.get(i).getFuel().getCost() + "\n";
            out += "Wheels:\t" + carList.get(i).getWheels().getName() + "\t" + carList.get(i).getWheels().getCost() + "\n";
            out += "Color:\t" + carList.get(i).getColor().getName() + "\t" + carList.get(i).getColor().getCost() + "\n";
            for (int j = 0; j < carList.get(i).getAddons().size(); j++) {
                if(!carList.get(i).getAddons().getElement(j).getName().equals("")) {
                    out += "Addon:\t" + carList.get(i).getAddons().getElement(j).getName() + "\t" + carList.get(i).getAddons().getElement(j).getCost() + "\n";
                }
            }
            out += "\n";
        }
        return out;
    }
}
