package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NewCar {
    public SimpleStringProperty fuel;
    public SimpleStringProperty wheels;
    public SimpleStringProperty color;
    public String[] adonneFROMCLASS;


    public SimpleStringProperty user;
    public SimpleStringProperty name;
    public SimpleIntegerProperty cost;
    public Car[] features;

    public NewCar(String fuel, String wheels, String color, String[] addone) {
        this.fuel = new SimpleStringProperty(fuel);
        this.wheels = new SimpleStringProperty(wheels);
        this.color = new SimpleStringProperty(color);
        this.adonneFROMCLASS = addone;
    }

    public String getFuel() {
        return fuel.get();
    }

    public String getWheels() {
        return wheels.get();
    }

    public String getColor() {
        return color.get();
    }

}
