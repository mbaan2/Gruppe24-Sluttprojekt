package Gruppe24.OSLOMET.Car;

public interface Car {
    //important that we rethink the function in this interface once we reached the end stage

    public String getName();

    public int getCost();

    public void add(Car carpart);

    public int size();

    public String getNameIndex(int index);

    public Car getElement(int index);
}
