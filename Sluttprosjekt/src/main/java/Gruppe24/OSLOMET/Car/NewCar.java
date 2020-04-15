package Gruppe24.OSLOMET.Car;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.App;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class NewCar implements Serializable {
    public String user;
    public String name;
    public Carparts fuel;
    public Car wheels;
    public Car color;
    public CarCategory addones;
    
    public String getUser() {
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }

    //GETTER AND SETTER METHODS
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getCost() {
        return totalCost();
    }
    public Carparts getFuel() {
        return fuel;
    }
    public void setFuel(Carparts fuel) {
        this.fuel = fuel;
    }
    public Car getWheels() {
        return wheels;
    }
    public void setWheels(Car wheels) {
        this.wheels = wheels;
    }
    public Car getColor() { return color; }
    public void setColor(Car color) { this.color = color; }
    public CarCategory getAddones() { return addones; }
    public void setAddones(CarCategory addones) { this.addones = addones; }



    //ALL THE FUNCTIONS
    public int totalCost(){
        int totalcost = 0;
        totalcost += App.car.getFuel().getCost();
        totalcost += App.car.getWheels().getCost();
        totalcost += App.car.getColor().getCost();
        for(int i = 0; i< App.car.getAddones().size(); i++) {
            totalcost += App.car.getAddones().getElement(i).getCost();
        }
        return totalcost;
    }

    public String savableData(){
        String ut = "User: " + "\t" + App.car.getUser() + "\t" + App.car.getName() +"\t Cost: " + totalCost() + "\t" + "FEATURES:" + "\t";
        ut += App.car.getFuel().getName() +"\t"+ App.car.getFuel().getCost() + "\t";
        ut += App.car.getWheels().getName() +"\t"+ App.car.getWheels().getCost() + "\t";
        ut += App.car.getColor().getName() +"\t"+ App.car.getColor().getCost() + "\t";
        for(int i = 0; i< App.car.getAddones().size(); i++) {
            ut += App.car.getAddones().getElement(i).getName() + "\t" + App.car.getAddones().getElement(i).getCost() + "\t";
        }
        ut += "\n";

        return ut;
    }

    @Override
    public String toString(){
        String carBuild = "This car belongs to: \n";
        carBuild += "Username: " + App.car.getUser() + "\n";
        carBuild += "This car contains: " + "\n";
        carBuild += "Fuel: " + App.car.getFuel().getName() + " (kr" + App.car.getFuel().getCost() + ")\n";
        carBuild += "Wheels: " + App.car.getWheels().getName() + " (kr" + App.car.getWheels().getCost() + ")\n";
        carBuild += "Color: " + App.car.getColor().getName() + " (kr" + App.car.getColor().getCost() + ")\n";
        for(int i = 0; i< App.car.getAddones().size(); i++) {
            carBuild += "Add on: " + App.car.getAddones().getElement(i).getName() + " (kr" + App.car.getAddones().getElement(i).getCost() + ")\n";
        }
        
        return carBuild;
    }

}
