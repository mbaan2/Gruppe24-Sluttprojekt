package Gruppe24.OSLOMET.Car;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarCategory implements Car, Serializable {
    String name;
    List<Car> carpartsList = new ArrayList<>();

    final static long serialVersionUID = 1;

    public CarCategory(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        int totalcost = 0;
        for(Car carparts : carpartsList){
            totalcost += carparts.getCost();
        }
        return totalcost;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void add(Car part){
        carpartsList.add(part);
    }

    public Car getElement(int index){
        return carpartsList.get(index);
    }

    public void remove(int index){
        carpartsList.remove(index);
    }

    public void remove(Car addon){
        carpartsList.remove(addon);
    }

    public int size(){
        int size =0;
        for(int i = 0 ; i < carpartsList.size(); i++){
            size++;
        }
        return size;
    }

    public void clear(){
        carpartsList.clear();
    }

}
