package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarObj;
import Gruppe24.OSLOMET.Car.Carparts;

import java.util.ArrayList;

public class ParseCar{
    public static CarObj parseCar(String str){
        String[] split = str.split("\t");
        String id = split[0];
        int totalPrice;
        try{
            totalPrice = Integer.parseInt(split[1]);
        } catch (NumberFormatException e){
            totalPrice = 0;
            System.err.println("Total cost could not be retrieved.");
        }
        ArrayList<Carparts> carparts = new ArrayList<>();
        for(int i=3; i<split.length; i++){
            try {
                int partCost = Integer.parseInt(split[i+1]);
                Carparts x = new Carparts(split[i], partCost);
                carparts.add(x);
            } catch (NumberFormatException e){
                System.err.println("Could not fetch carpart price");
            }
            i++;
        }
        CarObj retrievedCar = new CarObj(id, totalPrice, CarObj.arrayListToArray(carparts));
        return retrievedCar;
    }
}
