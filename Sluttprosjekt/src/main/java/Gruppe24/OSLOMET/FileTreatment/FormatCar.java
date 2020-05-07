package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.ObservableList;

public class FormatCar {

    public static String formatCar(ObservableList<NewCar> carList) {
        String out = "";
        for(int i = 0; i < carList.size(); i++) {
            out += "User:\t" + carList.get(i).getUser() + "\nName:\t" + carList.get(i).getName() + "\nCost:\t(" + carList.get(i).getCost() + "kr)\n";
            out += "Fuel:\t" + carList.get(i).getFuel().getName() + "(" + carList.get(i).getFuel().getCost() + "kr)" + "\n";
            out += "Wheels:\t" + carList.get(i).getWheels().getName() + "(" + carList.get(i).getWheels().getCost() + "kr)" + "\n";
            out += "Color:\t" + carList.get(i).getColor().getName() + "(" + carList.get(i).getColor().getCost() + "kr)" + "\n";
            for (int j = 0; j < carList.get(i).getAddons().size(); j++) {
                if(!carList.get(i).getAddons().getElement(j).getName().equals("")) {
                    out += "Addon:\t" + carList.get(i).getAddons().getElement(j).getName() + "(" + carList.get(i).getAddons().getElement(j).getCost() + "kr)" + "\n";
                }
            }
            out += "\n";
        }
        return out;
    }
}
