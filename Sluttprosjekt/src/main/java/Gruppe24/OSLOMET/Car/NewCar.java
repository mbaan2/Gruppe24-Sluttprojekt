package Gruppe24.OSLOMET.Car;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NewCar implements Car{
    //Creates car object
    private SimpleStringProperty user;
    private SimpleStringProperty name;
    private SimpleIntegerProperty cost;
    private Car[] features;

    public NewCar(String user, String name, int cost, Car[] features){
        this.user = new SimpleStringProperty(user);
        this.name = new SimpleStringProperty(name);
        this.cost = new SimpleIntegerProperty(cost);
        this.features = features;
    }

    public String getUser(){
        return user.getValue();
    }

    public void setUser(String user){
        this.user.setValue(user);
    }

    @Override
    public String getName(){
        return name.getValue();
    }

    public void setName(String name){
        this.name.setValue(name);
    }

    @Override
    public int getCost(){
        return cost.getValue();
    }

    public void setCost(int totalPrice){
        this.cost.setValue(totalPrice);
    }

    public Car[] getFeatures(){
        return features;
    }

    public void setFeatures(Car[] features){
        this.features = features;
    }

    //Creates temporary car
    static Car[] buildingANewCar = new Car[5];

    public static Car[] getCarInBuilding(){
        return buildingANewCar;
    }

    public static void setCarInBuilding(Car[] car){
        buildingANewCar = car;
    }

    @Override
    public String getNameIndex(int index) {
        return null;
    }

    public static String getNameIndexStatic(int index){
        try{
            return buildingANewCar[index].getName();
        } catch (NullPointerException e){
            return "Empty";
        }
    }
    public static String getNameIndexAddoneStatic(int index){
        try{
            return buildingANewCar[4].getNameIndex(index);
        } catch (NullPointerException e){
            return "Empty";
        }
    }

    public static int sizeAddone(){
        int size = 0;
        try{
            size = buildingANewCar[4].size();
        } catch (NullPointerException e){
            size =0;
        }
        return size;
    }

    @Override
    public Car getElement(int index) {
        return null;
    }

    @Override
    public void add(Car part){

    }

    public static void set(int index, Car carparts){
        buildingANewCar[index] = carparts;
    }

    public static void setUsername(Car carUsername) {
        buildingANewCar[0] = carUsername;
    }

    public static int totalCost(){
        int totalcost = 0;
        totalcost += buildingANewCar[1].getCost();
        totalcost += buildingANewCar[2].getCost();
        totalcost += buildingANewCar[3].getCost();
        for(int i = 0; i< buildingANewCar[4].size(); i++) {
            totalcost += buildingANewCar[4].getElement(i).getCost();
        }
        return totalcost;
    }


    public int size(){
        int size =0;
        for(int i = 0; i < buildingANewCar.length; i++){
            size++;
        }
        return size;
    }

    public static String builtCarUserView(){
        String carBuild = "This car belongs to: \n";
        carBuild += "Username: " + buildingANewCar[0].getName() + "\n\n";
        carBuild += "This car contains: " + "\n";
        carBuild += "Fuel: " + buildingANewCar[1].getName() + " (kr" + buildingANewCar[1].getCost() + ")\n";
        carBuild += "Wheels: " + buildingANewCar[2].getName() + " (kr" + buildingANewCar[2].getCost() + ")\n";
        carBuild += "Color: " + buildingANewCar[3].getName() + " (kr" + buildingANewCar[3].getCost() + ")\n";
        for(int i = 0; i< buildingANewCar[4].size(); i++) {
            carBuild += "Add on: " + buildingANewCar[4].getElement(i).getName() + " (kr" + buildingANewCar[4].getElement(i).getCost() + ")\n";
        }
        return carBuild;
    }

    public static NewCar createCarObject(String innCarName, String innCarUsername){
        NewCar car = new NewCar("", null, 0, null);
        car.setUser(innCarUsername);
        car.setName(innCarName);
        car.setCost(totalCost());
        car.setFeatures(buildingANewCar);
        return car;
    }

    public String savableData(String carName, String carUsername){
        NewCar car = createCarObject(carName, carUsername);
        String ut = car.getUser() + "\t" + carName +"\t" + totalCost() + "\tFEATURES:";

        ut += buildingANewCar[1].getName() +"\t"+ buildingANewCar[1].getCost() + "\t";
        ut += buildingANewCar[2].getName() +"\t"+ buildingANewCar[2].getCost() + "\t";
        ut += buildingANewCar[3].getName() +"\t"+ buildingANewCar[3].getCost() + "\t";
        for(int i = 0; i< buildingANewCar[4].size(); i++) {
            ut += buildingANewCar[4].getElement(i).getName() + "\t" + buildingANewCar[4].getElement(i).getCost() + "\t";
        }
        ut += "\n";

        return ut;
    }

}
