package Gruppe24.OSLOMET.Car;

import java.util.ArrayList;
import java.util.List;

public class CarBuild implements Car{
    String name;
    static List<Carparts> newCar = new ArrayList<>();

    public CarBuild(String name) {
        super();
        this.name = name;
    }

    public static void add(Carparts part){
        newCar.add(part);
    }

    public static String buildCar(){
        String carBuild = "This car contains: \n";
        for(int i = 0; i < newCar.size(); i++){
            carBuild += newCar.get(i).name;
            carBuild += "\n";
        }
        return carBuild;
    }

    public static int totalCost(){
        int totalcost = 0;
        for(Carparts carparts : newCar){
            totalcost += carparts.cost;
        }
        return totalcost;
    }


    @Override
    public String getName() {
        return null;
    }
}
