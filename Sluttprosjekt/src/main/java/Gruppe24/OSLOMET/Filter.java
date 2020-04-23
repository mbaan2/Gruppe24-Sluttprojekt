package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class Filter {
    public static ObservableList<NewCar> usernameFilter(String username, ObservableList<NewCar> list) {
        if(!username.isEmpty()) {
            return list.stream().filter(newCar -> newCar.getUser().toLowerCase().matches(String.format("%s%s%s", ".*", username.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return list;
    }

    public static ObservableList<NewCar> nameFilter(String name, ObservableList<NewCar> list) {
        if(!name.isEmpty()) {
            return list.stream().filter(newCar -> newCar.getName().toLowerCase().matches(String.format("%s%s%s", ".*", name.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return list;
    }

    public static ObservableList<NewCar> fuelFilter(String fuel, ObservableList<NewCar> list) {
        if(!fuel.isEmpty()) {
            return list.stream().filter(newCar -> newCar.getFuel().getName().toLowerCase().matches(String.format("%s%s%s", ".*", fuel.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return list;
    }

    public static ObservableList<NewCar> wheelsFilter(String wheels, ObservableList<NewCar> list) {
        if(!wheels.isEmpty()) {
            return list.stream().filter(newCar -> newCar.getWheels().getName().toLowerCase().matches(String.format("%s%s%s", ".*", wheels.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return list;
    }

    public static ObservableList<NewCar> colorFilter(String color, ObservableList<NewCar> list) {
        if(!color.isEmpty()) {
            return list.stream().filter(newCar -> newCar.getColor().getName().toLowerCase().matches(String.format("%s%s%s", ".*", color.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return list;
    }

    public static ObservableList<NewCar> addonFilter(String addon, ObservableList<NewCar> list, int i) {
        if(!addon.isEmpty()) {
            return list.stream().filter(newCar -> newCar.getAddons().getElement(i).getName().toLowerCase().matches(String.format("%s%s%s", ".*", addon.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return list;
    }
}
