package Gruppe24.OSLOMET.Car;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarCategory implements Car, Serializable {
    String name;
    static List<Car> newCar = new ArrayList<>();

    public CarCategory(String name) {
        super();
        this.name = name;
    }

    public void add(Car part){
        newCar.add(part);
    }

    public Car getElement(int index){
        return newCar.get(index);
    }


    public static String getNamePart(int index){
        return newCar.get(index).getName();
    }

    public static int getCostPart(int index){
        return newCar.get(index).getCost();
    }

    public static String buildCar(){
        String carBuild = "This car contains: \n";
        for(int i = 0; i < newCar.size(); i++){
            carBuild += newCar.get(i).getName();
            carBuild += "\n";
        }
        return carBuild;
    }

    public int size(){
        int size =0;
        for(int i = 0 ; i < newCar.size(); i++){
            size++;
        }
        return size;
    }

    public String getNameIndex(int index){
        return newCar.get(index).getName();
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        int totalcost = 0;
        for(Car carparts : newCar){
            totalcost += carparts.getCost();
        }
        return totalcost;
    }
}
