package Gruppe24.OSLOMET.Car;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Carparts implements Car, Serializable {
    SimpleStringProperty name;
    SimpleIntegerProperty cost;

    public Carparts(String name, int cost) {
        this.name = new SimpleStringProperty(name);
        this.cost = new SimpleIntegerProperty(cost);
    }

    @Override
    public String getName() {
        return name.getValue();
    }

    @Override
    public int getCost() {
        return cost.getValue();
    }

}
