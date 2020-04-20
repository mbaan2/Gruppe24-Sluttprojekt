package Gruppe24.OSLOMET.Car;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarCategory implements Car, Serializable {
    SimpleStringProperty name;
    static transient List<Car> newCar = new ArrayList<>();

    public CarCategory(String name) {
        super();
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String getName() {
        return name.getValue();
    }

    @Override
    public int getCost() {
        int totalcost = 0;
        for(Car carparts : newCar){
            totalcost += carparts.getCost();
        }
        return totalcost;
    }



    public void add(Car part){
        newCar.add(part);
    }

    public Car getElement(int index){
        return newCar.get(index);
    }

    public int size(){
        int size =0;
        for(int i = 0 ; i < newCar.size(); i++){
            size++;
        }
        return size;
    }

    public void clear(){
        newCar.clear();
    }
}
