package Gruppe24.OSLOMET.Car;

import Gruppe24.OSLOMET.UserLogin.User;

import java.util.ArrayList;

public class CarObj{
    private String id;
    private int totalCost;
    private Carparts[] carparts;

    public CarObj(String id, int totalCost, Carparts[] carparts){
        this.id = id;
        this.totalCost = totalCost;
        this.carparts = carparts;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public int getTotalCost(){
        return totalCost;
    }

    public void setTotalCost(int totalCost){
        this.totalCost = totalCost;
    }

    public Carparts[] getCarparts(){
        return carparts;
    }

    public void setCarparts(Carparts[] carparts){
        this.carparts = carparts;
    }

    public static ArrayList<Carparts> fetchCarParts(){
        Car[] carItems = BuildingNewCar.getBuiltCar();
        ArrayList<Carparts> partsArrayList = new ArrayList<>();
        //Carparts fuel = new Carparts(carItems[0].getName(), carItems[0].getCost());
        //ERASE NEXT LINE
        Carparts fuel = new Carparts("mystery fuel", 0);
        Carparts wheels = new Carparts(carItems[1].getName(), carItems[1].getCost());
        Carparts colour = new Carparts(carItems[2].getName(), carItems[2].getCost());
        partsArrayList.add(fuel);
        partsArrayList.add(wheels);
        partsArrayList.add(colour);
        for(int i = 0; i<carItems[3].size(); i++) {
            String name = carItems[3].getElement(i).getName();
            int cost = carItems[3].getElement(i).getCost();
            Carparts x = new Carparts(name, cost);
            partsArrayList.add(x);
        }
        return partsArrayList;
    }

    public static Carparts[] arrayListToArray(ArrayList<Carparts> partsArrayList){
        Carparts[] carpartsArray = new Carparts[partsArrayList.size()];
        for (int i = 0; i<carpartsArray.length; i++){
            carpartsArray[i]=partsArrayList.get(i);
        }

        return carpartsArray;
    }



    public String saveableData(String carName){
        String str = carName + "\t" + getTotalCost() + "\t\t";
        Carparts[] array = getCarparts();

        for (Carparts c : array){
            str+= c.name + "\t" + c.cost + "\t";
        }

        str += "\n";
        return str;
    }
}
