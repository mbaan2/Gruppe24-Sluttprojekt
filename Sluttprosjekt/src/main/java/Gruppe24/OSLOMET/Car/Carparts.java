package Gruppe24.OSLOMET.Car;

import java.io.Serializable;

public class Carparts implements Car, Serializable {
    String name;
    int cost;

    public Carparts(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }
}
