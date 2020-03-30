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
