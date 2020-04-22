package Gruppe24.OSLOMET.Car;

public interface Car {
    String getName();
    int getCost();

    void setName(String name);

    void setCost(int cost);

    boolean exist(int index);
}
