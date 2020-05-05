package Gruppe24.OSLOMET.Car;

import Gruppe24.OSLOMET.App;

import java.io.Serializable;

public class NewCar implements Serializable {
    public String user;
    public String name;
    public Carparts fuel;
    public Car wheels;
    public Car color;
    public CarCategory addons;
    public int cost;


    //GETTER AND SETTER METHODS
    public String getUser() {
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost){
        this.cost = cost;
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
    public CarCategory getAddons() { return addons; }
    public void setAddons(CarCategory addons) {
        this.addons = addons;
    }


    //ALL THE FUNCTIONS
    public int totalCost(){
        int totalcost = 0;
        totalcost += App.car.getFuel().getCost();
        totalcost += App.car.getWheels().getCost();
        totalcost += App.car.getColor().getCost();
        for(int i = 0; i< App.car.getAddons().size(); i++) {
            totalcost += App.car.getAddons().getElement(i).getCost();
        }
        return totalcost;
    }

    public String savableData(){
        String ut = "User: " + "\t" + App.car.getUser() + "\t" + App.car.getName() +"\t Cost: " + totalCost() + "\t" + "FEATURES:" + "\t";
        ut += App.car.getFuel().getName() +"\t"+ App.car.getFuel().getCost() + "\t";
        ut += App.car.getWheels().getName() +"\t"+ App.car.getWheels().getCost() + "\t";
        ut += App.car.getColor().getName() +"\t"+ App.car.getColor().getCost() + "\t";
        for(int i = 0; i< App.car.getAddons().size(); i++) {
            ut += App.car.getAddons().getElement(i).getName() + "\t" + App.car.getAddons().getElement(i).getCost() + "\t";
        }
        ut += "\n";

        return ut;
    }

    @Override
    public String toString(){
        String carBuild = "This car belongs to: \n";
        carBuild += "Username: " + App.car.getUser() + "\n\n";
        carBuild += "This car contains: " + "\n";
        carBuild += "Fuel: " + App.car.getFuel().getName() + " (kr" + App.car.getFuel().getCost() + ")\n";
        carBuild += "Wheels: " + App.car.getWheels().getName() + " (kr" + App.car.getWheels().getCost() + ")\n";
        carBuild += "Color: " + App.car.getColor().getName() + " (kr" + App.car.getColor().getCost() + ")\n";
        for(int i = 0; i< App.car.getAddons().size(); i++) {
            carBuild += "Add on: " + App.car.getAddons().getElement(i).getName() + " (kr" + App.car.getAddons().getElement(i).getCost() + ")\n";
        }
        carBuild += "\n";
        
        return carBuild;
    }

}
