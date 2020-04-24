package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserCarView_Controller implements Initializable {
    @FXML
    private TableView<NewCar> tableView;

    ObservableList<NewCar> carList = FXCollections.observableArrayList();
    ObservableList<NewCar> usersCarList = FXCollections.observableArrayList();

    @FXML
    void loadYourCars() {
        String username = App.car.getUser();
        usersCarList = Filter.usernameFilter(username, carList);
        tableView.setItems(usersCarList);
        tableView.setVisible(true);
        for(int j = 0; j < usersCarList.size(); j++) {
            System.out.println(usersCarList.get(j).getFuel().getName() + " " + usersCarList.get(j).getFuel().getCost());
        }
    }

    @FXML
    void goBack() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    void logout() throws IOException {
        App.setRoot("Login");
    }

    void showCars() throws IOException {
        ArrayList<NewCar> list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        carList.addAll(list2);

        NewCar car1 = new NewCar();
        NewCar car2 = new NewCar();

        car1.setUser("123");
        car1.setName("BMW");
        Carparts fuel = new Carparts("Diesel", 1000);
        Carparts wheels = new Carparts("Big wheels", 1000);
        Carparts color = new Carparts("Red", 1000);
        CarCategory addons = new CarCategory("Addons");
        Carparts gps = new Carparts("Gps", 1000);
        Carparts cupholder = new Carparts("Cupholder", 1000);
        Carparts crate = new Carparts("Crate", 1000);
        addons.add(crate);
        addons.add(gps);
        addons.add(cupholder);
        car1.setFuel(fuel);
        car1.setColor(color);
        car1.setWheels(wheels);
        car1.setAddons(addons);

        car2.setUser("345");
        car2.setName("Mercedes");
        Carparts fuel2 = new Carparts("Electric", 1000);
        Carparts wheels2 = new Carparts("Small wheels", 1000);
        Carparts colors = new Carparts("Blue", 1000);
        CarCategory addons2 = new CarCategory("Addons2");
        Carparts radio = new Carparts("Radio", 1000);
        Carparts window = new Carparts("Window", 1000);
        addons2.add(radio);
        addons2.add(window);
        car2.setAddons(addons2);
        car2.setWheels(wheels2);
        car2.setColor(colors);
        car2.setFuel(fuel2);

        carList.addAll(car2, car1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setVisible(false);
        try {
            showCars();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int maxAntallAddons = maxAddons();
        fillEmptyAddons();
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
        TableColumn<NewCar, String> addon = new TableColumn<>("Addons");
        tableView.getColumns().add(addon);

        for(int i = 0; i < maxAntallAddons; i ++) {
            TableColumn<NewCar, String> tc = new TableColumn<>("Addon " + (i + 1));
            addon.getColumns().add(tc);
        }


        //Loading of the data into the tableview
        for (int i = 0; i < carList.size(); i++) {
            user.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getUser()));
            user.setCellFactory(TextFieldTableCell.forTableColumn());
            user.setOnEditCommit(event -> event.getRowValue().setUser(event.getNewValue()));

            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            user.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName() + " (" + car.getValue().getFuel().getCost() + " kr)"));
            fuel.setCellFactory(TextFieldTableCell.forTableColumn());
            fuel.setOnEditCommit(event -> event.getRowValue().setFuel(new Carparts(event.getNewValue(), event.getRowValue().getFuel().getCost())));

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName() + " (" + car.getValue().getWheels().getCost() + " kr)"));
            wheels.setCellFactory(TextFieldTableCell.forTableColumn());
            wheels.setOnEditCommit(event -> event.getRowValue().setWheels(new Carparts(event.getNewValue(), event.getRowValue().getWheels().getCost())));

            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName() + " (" + car.getValue().getColor().getCost() + " kr)"));
            color.setCellFactory(TextFieldTableCell.forTableColumn());
            color.setOnEditCommit(event -> event.getRowValue().setColor(new Carparts(event.getNewValue(), event.getRowValue().getColor().getCost())));

            for (int j = 0; j < maxAntallAddons; j++) {
                int finalJ = j;
                TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addon.getColumns().get(j);
                tc.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getAddons().getElement(finalJ).getName() + " (" + car.getValue().getAddons().getElement(finalJ).getCost() + " kr)"));
                tc.setCellFactory(TextFieldTableCell.forTableColumn());
                tc.setOnEditCommit(event -> event.getRowValue().setAddons(new CarCategory(event.getNewValue())));
            }
        }
        tableView.setEditable(true);
    }


    private int maxAddons(){
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

    private  void fillEmptyAddons(){
        int maxAntall = maxAddons();
        for(int i = 0 ; i< carList.size(); i++){
            System.out.println(carList.get(i).getAddons().size());
            for(int j = 0; j < maxAntall; j++){
                if(j >= carList.get(i).getAddons().size()){
                    Car emptyAddone = new Carparts("", 0);
                    carList.get(i).getAddons().add(emptyAddone);
                    System.out.println("done");
                }
            }
        }
    }

    @FXML
    private void colorEdited(TableColumn.CellEditEvent<NewCar, Carparts> event) {
        event.getRowValue().setColor(event.getNewValue());
    }

    @FXML
    private void wheelsEdited(TableColumn.CellEditEvent<NewCar, Carparts> event) {
        event.getRowValue().setWheels(event.getNewValue());
    }

    @FXML
    private void addonsEdited(TableColumn.CellEditEvent<NewCar, CarCategory> event) {
        event.getRowValue().setAddons(event.getNewValue());
    }

    @FXML
    private void fuelEdited(TableColumn.CellEditEvent<NewCar, Carparts> event) {
        event.getRowValue().setFuel(event.getNewValue());
    }

    @FXML
    private void nameEdited(TableColumn.CellEditEvent<NewCar, String> event) {
        event.getRowValue().setName(event.getNewValue());
    }

    @FXML
    private void userEdited(TableColumn.CellEditEvent<NewCar, String> event) {
        event.getRowValue().setUser(event.getNewValue());
    }
}
