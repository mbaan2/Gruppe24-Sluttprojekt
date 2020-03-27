package Gruppe24.OSLOMET.Car;

public class Carparts implements Car{
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
}
