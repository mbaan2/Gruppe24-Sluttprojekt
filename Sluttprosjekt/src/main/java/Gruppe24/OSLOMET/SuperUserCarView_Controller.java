package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SuperUserCarView_Controller implements Initializable {
    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private Label filterLbl;

    ObservableList<NewCar> carList = FXCollections.observableArrayList();
    ObservableList<NewCar> filteredList = FXCollections.observableArrayList();
    ObservableList<String> choiceBoxList = FXCollections.observableArrayList();
    ObservableList<String> fuelList = FXCollections.observableArrayList();
    ObservableList<String> wheelList = FXCollections.observableArrayList();
    ObservableList<String> colorList = FXCollections.observableArrayList();

    @FXML
    void filterCars() {
        for(int i = 0; i < carList.size(); i++) {
            System.out.println(carList.get(i).getFuel().getName() + " " + carList.get(i).getFuel().getCost());
        }

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
    }

    void showCars() throws IOException {
        //TODO Fix opening of JOBJ
        ArrayList<NewCar> list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        carList.addAll(list2);

        NewCar car1 = new NewCar();
        car1.setFuel(new Carparts("Diesel", 10000));
        car1.setColor(new Carparts("Red", 5));
        CarCategory addonsCar1 = new CarCategory("Addons");
        Car GPS = new Carparts("GPS", 100);
        Car subwoofer = new Carparts("Subwoofer", 1000);
        Car ice = new Carparts("Ice", 5);
        Car lemons = new Carparts("Lemons", 10);
        addonsCar1.add(GPS);
        addonsCar1.add(subwoofer);
        addonsCar1.add(ice);
        addonsCar1.add(lemons);
        car1.setAddons(addonsCar1);
        car1.setUser("Aaa");
        car1.setWheels(new Carparts("Big wheels", 1000));
        car1.setName("Car1");

        NewCar car2 = new NewCar();
        car2.setFuel(new Carparts("Electric", 10000));
        car2.setColor(new Carparts("Pink", 5));
        CarCategory addonsCar2 = new CarCategory("Addons");
        Car spoiler = new Carparts("Spoiler", 100);
        Car paprika = new Carparts("Paprika", 10);
        addonsCar2.add(spoiler);
        addonsCar2.add(ice);
        addonsCar2.add(paprika);
        car2.setAddons(addonsCar2);
        car2.setUser("Bbb");
        car2.setWheels(new Carparts("Tiny wheels", 1000));
        car2.setName("Car2");

        carList.addAll(car1, car2);
    }

    List<Car> addOnSupUser = new ArrayList<>();

    public void openFile() throws IOException{
        Path path = Paths.get(StandardPaths.addonPath);
        addOnSupUser = FileOpenerJobj.openFile(path);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoiceBoxItems();
        try {
            showCars();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxNrAddons = 0;

        try {
            openFile();
            maxNrAddons = addOnSupUser.size();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        TableColumn<NewCar, String> addon = new TableColumn<>("Add-ons");
        tableView.getColumns().add(addon);

        for(int i = 0; i < maxNrAddons; i++) {
            TableColumn<NewCar, CheckBox> tc = new TableColumn<>(addOnSupUser.get(i).getName());
            tc.setText(addOnSupUser.get(i).getName());
            addon.getColumns().add(tc);
        }

        //Adding values to the lists for the comboboxes for editing
        String dieselFuel = "Diesel Car";
        String electricFuel = "Electric Car";
        String gasFuel = "Gasoline Car";
        fuelList.addAll(dieselFuel, electricFuel, gasFuel);

        String bigWheels = "Big wheels";
        String smallWheels = "Small wheels";
        String mediumWheels = "Medium wheels";
        wheelList.addAll(smallWheels, mediumWheels, bigWheels);

        String redColor = "Red";
        String blueColor = "Blue";
        String yellowColor = "Yellow";
        String blackColor = "Black";
        String greenColor = "Green";
        colorList.addAll(redColor, blueColor, yellowColor, blackColor, greenColor);

        tableView.setEditable(true);
        TableColumn<NewCar, HBox> deprecatedAddon = new TableColumn<>("Out-of-sale");
        addon.getColumns().add(deprecatedAddon);


        //Loading of the data into the tableview
        for (int i = 0; i < carList.size(); i++) {
            user.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getUser()));
            user.setCellFactory(TextFieldTableCell.forTableColumn());
            user.setOnEditCommit(event -> event.getRowValue().setUser(event.getNewValue()));

            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName()));
            fuel.setCellFactory(ComboBoxTableCell.forTableColumn(fuelList));
            fuel.setOnEditCommit(event -> {
                if(event.getNewValue().equals("Diesel Car")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 20000));
                } else if(event.getNewValue().equals("Electric Car")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 17500));
                } else if(event.getNewValue().equals("Gasoline Car")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 30000));
                }
            });

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName()));
            wheels.setCellFactory(ComboBoxTableCell.forTableColumn(wheelList));
            wheels.setOnEditCommit(event -> {
                if(event.getNewValue().equals("Small wheels")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 0));
                } else if(event.getNewValue().equals("Medium wheels")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 2500));
                } else if(event.getNewValue().equals("Big wheels")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 5000));
                }
            });
            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName()));
            color.setCellFactory(ComboBoxTableCell.forTableColumn(colorList));
            color.setOnEditCommit(event -> {
                if(event.getNewValue().equals("Red")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 5000));
                } else if(event.getNewValue().equals("Blue")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 2500));
                } else if(event.getNewValue().equals("Yellow")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 2500));
                } else if(event.getNewValue().equals("Black")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 0));
                } else if(event.getNewValue().equals("Green")) {
                    event.getRowValue().setFuel(new Carparts(event.getNewValue(), 3500));
                }
            });

            for (int j = 0; j < maxNrAddons; j++) {
                TableColumn<NewCar, CheckBox> tc = (TableColumn<NewCar, CheckBox>) addon.getColumns().get(j);
                int finalJ = j;
                tc.setCellValueFactory(car -> {
                    CheckBox checkbox = new CheckBox("kr" + addOnSupUser.get(finalJ).getCost());
                    checkbox.setSelected(false);
                    for (int k = 0; k < car.getValue().getAddons().size(); k++) {
                        if (car.getValue().getAddons().getElement(k).getName().toLowerCase().equals(car.getTableColumn().getText().toLowerCase())) {
                            checkbox.setSelected(true);
                        }

                        int finalK = k;
                        checkbox.setOnAction(actionEvent -> handleAddonCheckbox(actionEvent, addOnSupUser.get(finalJ), car.getValue().getAddons().getElement(finalK), car.getValue().addons, checkbox.isSelected()));
                    }
                    return new SimpleObjectProperty<CheckBox>(checkbox);
                });
            }


            deprecatedAddon.setCellValueFactory(car -> {
                List<String> availableAddOns = new ArrayList<>();
                for (Car c : addOnSupUser){
                    String s = c.getName().toLowerCase();
                    availableAddOns.add(s);
                }

                HBox deprecatedAddonsList = new HBox();
                for (int j = 0; j < car.getValue().getAddons().size(); j++) {
                    boolean hasMatch = false;
                    for (String availableAddOn : availableAddOns){
                        if (car.getValue().getAddons().getElement(j).getName().toLowerCase().equals(availableAddOn)){
                            hasMatch = true;
                        }
                    }
                    if (!hasMatch){
                        Button unmatchedAddon = new Button();
                        unmatchedAddon.setText(car.getValue().getAddons().getElement(j).getName());
                        int finalJ = j;
                        unmatchedAddon.setOnAction(actionEvent -> deleteDeprecatedAddon(actionEvent, car.getValue().getAddons().getElement(finalJ), car.getValue().addons));
                        deprecatedAddonsList.getChildren().add(unmatchedAddon);
                    }
                }
                return new SimpleObjectProperty<HBox>(deprecatedAddonsList);
            });
        }
        tableView.setItems(carList);
    }

    private void handleAddonCheckbox(ActionEvent actionEvent, Car addonSupUser, Car addonUser, CarCategory addonsCarUser, boolean selected){
        if (!selected){
            addonsCarUser.remove(addonUser);

        } else {
            addonsCarUser.add(addonSupUser);
        }
        tableView.refresh();
    }

    private void deleteDeprecatedAddon(ActionEvent actionEvent, Car addon, CarCategory addonlist){
        addonlist.remove(addon);
        tableView.refresh();
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
        filterBox.setValue("Search Filters");
    }
}
