package Gruppe24.OSLOMET.Car;

import java.util.ArrayList;
import java.util.List;

public class BuildingNewCar implements Car{

    static List<Car> buidlingANewCar = new ArrayList<>();

    public static void add(Car part){
        buidlingANewCar.add(part);
    }

    public static String buildCar(){
        String carBuild = "This car contains: \n";
        for(int i = 0; i < buidlingANewCar.size(); i++){
            carBuild += buidlingANewCar.get(i).getName();
            carBuild += "\n";
        }
        return carBuild;
    }

    public static int totalCost(){
        int totalcost = 0;
        for(Car carparts : buidlingANewCar){
            totalcost += carparts.getCost();
        }
        return totalcost;
    }

    public int size(){
        int size =0;
        for(int i = 0 ; i < buidlingANewCar.size(); i++){
            size++;
        }
        return size;
    }

    public static String getNameIndex(int index){
        return buidlingANewCar.get(index).getName();
    }

    public static int getCostPart(int index){
        return buidlingANewCar.get(index).getCost();
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getCost() {
        int totalcost = 0;
        for(Car carparts : buidlingANewCar){
            totalcost += carparts.getCost();
        }
        return totalcost;
    }
}
