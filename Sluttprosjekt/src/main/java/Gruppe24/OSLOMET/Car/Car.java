package Gruppe24.OSLOMET.Car;

public interface Car {
    //important that we rethink the function in this interface once we reached the end stage

    String getName();

    int getCost();

    void add(Car carpart);

    int size();

    String getNameIndex(int index);

    Car getElement(int index);
}
