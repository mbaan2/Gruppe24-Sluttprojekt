package sample;

public interface Car {
    public void add(Car gps);

    public String getName();

    int size();

    Car getElement();

    Car getByIndex(int i);
}
