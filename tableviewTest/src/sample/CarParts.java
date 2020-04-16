package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CarParts implements Car{
    public SimpleStringProperty name;
    public SimpleIntegerProperty cost;

    public CarParts(String name, Integer cost) {
        super();
        this.name = new SimpleStringProperty(name);
        this.cost = new SimpleIntegerProperty(cost);
    }

    @Override
    public void add(Car gps) {

    }

    public String getName() {
        return name.get();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Car getElement() {
        return null;
    }

    @Override
    public Car getByIndex(int i) {
        return null;
    }

    public int getCost() {
        return cost.get();
    }

}
