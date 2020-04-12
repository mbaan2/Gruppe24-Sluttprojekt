package Gruppe24.OSLOMET.Car;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class CarUsername implements Car, Serializable {
    SimpleStringProperty username;

    public CarUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }

    @Override
    public String getName() {
        return username.getValue();
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public void add(Car carpart) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String getNameIndex(int index) {
        return null;
    }

    @Override
    public Car getElement(int index) {
        return null;
    }
}
