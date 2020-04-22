package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserCarView implements Initializable {
    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private Label filterLbl;

    ObservableList<NewCar> carList = FXCollections.observableArrayList();
    List<Car> addonList = getAddonListFromObsList(carList);
    ObservableList<NewCar> filteredList = FXCollections.observableArrayList();
    ObservableList<String> choiceBoxList = FXCollections.observableArrayList();

    @FXML
    void loadYourCars() throws IOException {
        ObservableList<NewCar> carList1 = FileOpenerJobj.openCarsToObsL(StandardPaths.carsPath);
        tableView.setItems(carList1);
    }

    @FXML
    void filterCars() throws IOException {
        filteredList.clear();
        filterLbl.setText("");

        String filteredText = filterText.getText();
        String filterType = filterBox.getValue();

        if(filteredText.equals("")) {
            filterLbl.setText("You didnt enter anything..");
            tableView.setItems(carList);
        } else {
            if(filterType.equals("Search Filters")) {
                filterLbl.setText("You didnt choose a filter.");
                tableView.setItems(carList);
            }
            if(filterType.equals("User")) {
                filteredList = Filter.usernameFilter(filteredText, carList);
                tableView.setItems(filteredList);
                filterLbl.setText("Registry filtered by username.");
                if(filteredList.isEmpty()) {
                    filterLbl.setText("No car exists with that username.");
                }
            }
            if(filterType.equals("Name")) {
                filteredList = Filter.nameFilter(filteredText, carList);
                tableView.setItems(filteredList);
                filterLbl.setText("Registry filtered by name.");
                if(filteredList.isEmpty()) {
                    filterLbl.setText("No car exists with that name.");
                }
            }
            if(filterType.equals("Fuel")) {
                filteredList = Filter.fuelFilter(filteredText, carList);
                tableView.setItems(filteredList);
                filterLbl.setText("Registry filtered by fueltype.");
                if(filteredList.isEmpty()) {
                    filterLbl.setText("No car exists with that fueltype.");
                }
            }
            if(filterType.equals("Wheels")) {
                filteredList = Filter.wheelsFilter(filteredText, carList);
                tableView.setItems(filteredList);
                filterLbl.setText("Registry filtered by wheels.");
                if(filteredList.isEmpty()) {
                    filterLbl.setText("No car exists with those wheels.");
                }
            }
            if(filterType.equals("Color")) {
                filteredList = Filter.colorFilter(filteredText, carList);
                tableView.setItems(filteredList);
                filterLbl.setText("Registry filtered by color.");
                if(filteredList.isEmpty()) {
                    filterLbl.setText("No car exists with that color.");
                }
            }
            if(filterType.equals("Addons")) {
                int i = 0;

                while(i < addonList.size()) {
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
    }

    public List<Car> getAddonListFromObsList(ObservableList<NewCar> list) {
        CarCategory addons;
        List<Car> addonList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            addons = list.get(i).getAddons();
            addonList.add(addons);
        }
        return addonList;
    }


    @FXML
    void showCars() throws IOException {
        ArrayList<NewCar> list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        carList.addAll(list2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoiceBoxItems();
        try {
            showCars();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int maxAntallAddones = maxAddones();
        fillemptyAddones();
        //IMPORTANT: IF WE MAKE CHANGES TO THE LIST THAN WE SHOULD ALSO REMOVE ALL THE EMPTY ADDONES!

        //Setting of all the colums
        TableColumn<NewCar, String> user = new TableColumn<>("User");
        tableView.getColumns().add(user);
        TableColumn<NewCar, String> name = new TableColumn<>("Name");
        tableView.getColumns().add(name);
        TableColumn<NewCar, String> fuel = new TableColumn<>("Fuel");
        tableView.getColumns().add(fuel);
        TableColumn<NewCar, String> wheels = new TableColumn<>("Wheels");
        tableView.getColumns().add(wheels);
        TableColumn<NewCar, String> color = new TableColumn<>("Color");
        tableView.getColumns().add(color);
        TableColumn<NewCar, String> addon = new TableColumn<>("Addon");
        tableView.getColumns().add(addon);

        for(int i = 0; i < maxAntallAddones; i ++) {
            TableColumn<NewCar, String> tc = new TableColumn<>("Addon " + (i + 1));
            addon.getColumns().add(tc);
        }


        //Loading of the data into the tableview
        for (int i = 0; i < carList.size(); i++) {
            int finalI = i;
            user.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getUser()));

            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));

            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName()));

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName()));

            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName()));

            for (int j = 0; j < maxAntallAddones; j++) {
                    int finalJ = j;
                    TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addon.getColumns().get(j);
                    //tc.setCellValueFactory(car -> new SimpleStringProperty(carList.get(finalI).getAddons().getElement(finalJ).getName()));
                    tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> car) {
                            return new SimpleStringProperty(car.getValue().getAddons().getElement(finalJ).getName());
                        }
                    });
            }
        }
        tableView.setItems(carList);
    }


    private int maxAddones(){
        int antall = 0;
        for(int i = 0 ; i< carList.size(); i++){
            for(int j = 1; j< (carList.get(i).getAddons().size() + 1); j++){
                if(j > antall){
                    antall = j;
                }
            }
        }
        return antall;
    }

    private  void fillemptyAddones(){
        int maxAntall = maxAddones();
        for(int i = 0 ; i< carList.size(); i++){
            System.out.println(carList.get(i).getAddons().size());
            for(int j = 0; j < maxAntall; j++){
                System.out.println(!carList.get(i).getAddons().exist(j));
                if(j >= carList.get(i).getAddons().size()){
                    Car emptyAddone = new Carparts("", 0);
                    carList.get(i).getAddons().add(emptyAddone);
                    System.out.println("done");
                }
            }
        }
    }

    private void addChoiceBoxItems() {
        choiceBoxList.removeAll();
        String choiceBoxFilter = "Search Filters";
        String choiceBoxUser = "User";
        String choiceBoxName = "Name";
        String choiceBoxFuel = "Fuel";
        String choiceBoxWheels = "Wheels";
        String choiceBoxColor = "Color";
        String choiceBoxAddons = "Addons";

        choiceBoxList.addAll(choiceBoxFilter, choiceBoxUser, choiceBoxName, choiceBoxFuel, choiceBoxWheels, choiceBoxColor, choiceBoxAddons);
        filterBox.getItems().addAll(choiceBoxList);
        filterBox.setValue(choiceBoxFilter);
    }

    @FXML
    private void resetFilter() {
        filterText.setText("");
        filterLbl.setText("Tableview reset.");
        tableView.setItems(carList);
    }
}
