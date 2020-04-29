package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class Filter {

    public static ObservableList<NewCar> filtering (String filteredText, String filterType, ObservableList<NewCar> filteredList, ObservableList<NewCar> carList){

        switch (filterType) {
            case "User":
                filteredList = Filter.usernameFilter(filteredText, carList);
                break;
            case "Name":
                 filteredList = Filter.nameFilter(filteredText, carList);
                break;
            case "Fuel":
                filteredList = Filter.fuelFilter(filteredText, carList);
                break;
            case "Wheels":
                filteredList = Filter.wheelsFilter(filteredText, carList);
                break;
            case "Color":
                filteredList = Filter.colorFilter(filteredText, carList);
                break;
        }
            /*
            //TODO Edit to include deprecated (beyond number specified)
            if(filterType.equals("Addons")) {
                int i = 0;
                int maxAntallAddons = addOnSupUser.size();

                while(i < maxAntallAddons) {
                    filteredList = Filter.addonFilter(filteredText, carList, i);
                    if(filteredList.isEmpty()) {
                        filterLbl.setText("No car exists with that addon.");
                        i++;
                    } else if(!filteredList.isEmpty()) {
                        tableView.setItems(filteredList);
                        filterLbl.setText("Registry filtered by addons.");
                        return;
                    }
                }

            }
            }
            */


        return filteredList;
    }

    public static String filteringFeedback(String filteredText, String filterType, ObservableList<NewCar> filteredList){
        String filterLbl = "";

        switch (filterType) {
            case "User":
                filterLbl = "Registry filtered by username.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with that username.";
                    ;
                }
                break;
            case "Name":
                filterLbl = "Registry filtered by name.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with that name.";
                    ;
                }
                break;
            case "Fuel":
                filterLbl = "Registry filtered by fueltype.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with that fueltype.";
                }
                break;
            case "Wheels":
                filterLbl = "Registry filtered by wheels.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with those wheels.";
                }
                break;
            case "Color":
                filterLbl = "Registry filtered by color.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with that color.";
                }
                break;
        }
        return filterLbl;
    }


    public static ObservableList<NewCar> usernameFilter(String username, ObservableList<NewCar> list) {
        return list.stream().filter(newCar -> newCar.getUser().toLowerCase().matches(String.format("%s%s%s", ".*", username.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static ObservableList<NewCar> nameFilter(String name, ObservableList<NewCar> list) {
        return list.stream().filter(newCar -> newCar.getName().toLowerCase().matches(String.format("%s%s%s", ".*", name.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static ObservableList<NewCar> fuelFilter(String fuel, ObservableList<NewCar> list) {
        return list.stream().filter(newCar -> newCar.getFuel().getName().toLowerCase().matches(String.format("%s%s%s", ".*", fuel.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static ObservableList<NewCar> wheelsFilter(String wheels, ObservableList<NewCar> carList) {
        return carList.stream().filter(newCar -> newCar.getWheels().getName().toLowerCase().matches(String.format("%s%s%s", ".*", wheels.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


    public static ObservableList<NewCar> colorFilter(String color, ObservableList<NewCar> list) {
        return list.stream().filter(newCar -> newCar.getColor().getName().toLowerCase().matches(String.format("%s%s%s", ".*", color.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static ObservableList<NewCar> addonFilter(String addon, ObservableList<NewCar> list, int i) {
        return list.stream().filter(newCar -> newCar.getAddons().getElement(i).getName().toLowerCase().matches(String.format("%s%s%s", ".*", addon.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }




}
