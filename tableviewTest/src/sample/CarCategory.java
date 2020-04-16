package sample;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class CarCategory implements Car{
    public SimpleStringProperty name;
    static List<Car> newCar = new ArrayList<>();

    public CarCategory(String name) {
        super();
        this.name = new SimpleStringProperty(name);
    }


    public String getName() {
        return name.get();
    }

    public static List<Car> getNewCar() {
        return newCar;
    }

    public void add(Car carpart){
        newCar.add(carpart);
    }

    public int size(){
        return newCar.size();
    }

    @Override
    public Car getElement() {
        return null;
    }

    public Car getElement(int index){
        return newCar.get(index);
    }

    public Car getByIndex(int index){
        return newCar.get(index);
    }

}
