package Gruppe24.OSLOMET.SuperUserClasses.TableView;

import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.UserLogin.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class Filter {
    public static ObservableList<String> choiceBoxList(){
        ObservableList<String> choiceBoxList = FXCollections.observableArrayList();
        choiceBoxList.removeAll();

        String choiceBoxFilter = "Search Filters";
        String choiceBoxUser = "User";
        String choiceBoxName = "Name";
        String choiceBoxFuel = "Fuel";
        String choiceBoxWheels = "Wheels";
        String choiceBoxColor = "Color";
        String choiceBoxAddons = "Addons";

        choiceBoxList.addAll(choiceBoxFilter, choiceBoxUser, choiceBoxName, choiceBoxFuel, choiceBoxWheels, choiceBoxColor, choiceBoxAddons);

        return choiceBoxList;
    }

    public static ObservableList<NewCar> filtering (String filteredText, String filterType, ObservableList<NewCar> filteredList, ObservableList<NewCar> carList){
        switch (filterType) {
            case "User":
                filteredList = carList.stream().filter(
                        newCar -> newCar.getUser().toLowerCase().matches(
                                String.format("%s%s%s", ".*", filteredText.toLowerCase(), ".*")
                        )
                ).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Name":
                 filteredList = carList.stream().filter(
                         newCar -> newCar.getName().toLowerCase().matches(
                                 String.format("%s%s%s", ".*", filteredText.toLowerCase(), ".*")
                         )
                 ).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Fuel":
                filteredList = carList.stream().filter(
                        newCar -> newCar.getFuel().getName().toLowerCase().matches(
                                String.format("%s%s%s", ".*", filteredText.toLowerCase(), ".*")
                        )
                ).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Wheels":
                filteredList = carList.stream().filter(
                        newCar -> newCar.getWheels().getName().toLowerCase().matches(
                                String.format("%s%s%s", ".*", filteredText.toLowerCase(), ".*")
                        )
                ).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Color":
                filteredList = carList.stream().filter(
                        newCar -> newCar.getColor().getName().toLowerCase().matches(
                                String.format("%s%s%s", ".*", filteredText.toLowerCase(), ".*")
                        )
                ).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Addons":
                for(int i=0; i < carList.size(); i++){
                    for (int j =0; j < carList.get(i).getAddons().size(); j++){
                        if(carList.get(i).getAddons().getElement(j).getName().toLowerCase().contains(filteredText.toLowerCase())){
                            filteredList.add(carList.get(i));
                            break;
                        }
                    }
                }
                break;
        }
        return filteredList;
    }

    public static String filteringFeedback(String filterType, ObservableList<NewCar> filteredList){
        String filterLbl = "";

        switch (filterType) {
            case "User":
                filterLbl = "Registry filtered by username.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with that username.";
                }
                break;
            case "Name":
                filterLbl = "Registry filtered by name.";
                if (filteredList.isEmpty()) {
                    filterLbl = "No car exists with that name.";
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
            case "Addons":
                filterLbl = "Registry filtered by addons.";
                if(filteredList.isEmpty()) {
                    filterLbl = "No car exists with that addon.";
                }
                break;
        }
        return filterLbl;
    }

    public static ObservableList<User> filterUser(String filterType, String filterText, ObservableList<User> userList, ObservableList<User> filteredList){
        switch (filterType) {
            case "Username":
                filteredList = userList.stream().filter(User -> User.getUsername().toLowerCase().matches(String.format("%s%s%s", ".*", filterText.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Location":
                filteredList = userList.stream().filter(User -> User.getLocation().toLowerCase().matches(String.format("%s%s%s", ".*", filterText.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Gender":
                filteredList = userList.stream().filter(User -> User.getGender().toLowerCase().matches(String.format("%s%s%s", ".*", filterText.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
            case "Secret Question":
                filteredList = userList.stream().filter(User -> User.getSecretQ().toLowerCase().matches(String.format("%s%s%s", ".*", filterText.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
                break;
        }
        return filteredList;
    }

    public static String filterUserFeedback(String filterType, ObservableList<User> filteredList) {
        String filterLbl = "";
        switch (filterType) {
            case "Username":
                filterLbl = "Registry filtered by username.";
                if(filteredList.isEmpty()) {
                    filterLbl = "No user exists with that username";
                }
                break;
            case "Location":
                filterLbl = "Registry filtered by Location.";
                if(filteredList.isEmpty()) {
                    filterLbl = "No user exists with that location";
                }
                break;
            case "Gender":
                filterLbl = "Registry filtered by gender.";
                if(filteredList.isEmpty()) {
                    filterLbl = "No user exists with that gender";
                }
                break;
            case "Secret Question":
                filterLbl = "Registry filtered by secret question.";
                if(filteredList.isEmpty()) {
                    filterLbl = "No user exists with that secret question";
                }
                break;
        }
        return filterLbl;
    }

    public static ObservableList<NewCar> usernameFilter(String username, ObservableList<NewCar> list) {
        return list.stream().filter(newCar -> newCar.getUser().toLowerCase().matches(String.format("%s%s%s", ".*", username.toLowerCase(), ".*"))).collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
