package Gruppe24.OSLOMET.Car;

public interface Car {
    public String getName();

    public int getCost();

    public void add(Car carpart);

    public int size();

    public String getNameIndex(int index);

    public Car getElement(int index);
}
