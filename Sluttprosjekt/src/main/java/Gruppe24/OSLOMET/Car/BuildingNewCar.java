package Gruppe24.OSLOMET.Car;

import java.util.ArrayList;
import java.util.List;

public class BuildingNewCar implements Car{

    static Car[] buidlingANewCar = new Car[4];

    public static Car[] getBuiltCar(){
        return buidlingANewCar;
    }

    public static void setBuiltCar(Car[] car){
        buidlingANewCar = car;
    }

    public static String buildCar(){
        String carBuild = "This car contains: \n";
        //carBuild += "Fuel: " + buidlingANewCar[0].getName();
        carBuild += "Wheels: " + buidlingANewCar[1].getName() + buidlingANewCar[1].getCost() + "\n";
        carBuild += "Color: " + buidlingANewCar[2].getName() + buidlingANewCar[2].getCost() + "\n";
        for(int i = 0; i<buidlingANewCar[3].size(); i++) {
            carBuild += "Add on: " + buidlingANewCar[3].getElement(i).getName() + buidlingANewCar[3].getElement(i).getCost() + "\n";
        }

        return carBuild;
    }

    public static int totalCost(){
        int totalcost = 0;
        //totalcost += buidlingANewCar[0].getCost();
        totalcost += buidlingANewCar[1].getCost();
        totalcost += buidlingANewCar[2].getCost();
        for(int i = 0; i<buidlingANewCar[3].size(); i++) {
            totalcost += buidlingANewCar[3].getElement(i).getCost();
        }
        return totalcost;
    }

    public int size(){
        int size =0;
        for(int i = 0 ; i < buidlingANewCar.length; i++){
            size++;
        }
        return size;
    }

    @Override
    public String getNameIndex(int index) {
        return null;
    }

    public static String getNameIndexStatic(int index){
        try{
            return buidlingANewCar[index].getName();
        } catch (NullPointerException e){
            return "Empty";
        }
    }


    @Override
    public Car getElement(int index) {
        return null;
    }

    public static int getCostPart(int index){
        return buidlingANewCar[index].getCost();
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

    public void add(Car part){

    }

    public static void set(int index, Car carparts){
        buidlingANewCar[index] = carparts;
    }

}
