package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class UserCarView implements Initializable {
    @FXML
    private TableView<NewCar> tableView;

    Path path = Paths.get("cars.jobj");
    ObservableList<NewCar> carList = FXCollections.observableArrayList();


    @FXML
    void showCars() {
        NewCar car = new NewCar();
        Car wheels = new Carparts("Medium Wheels", 1000);
        Carparts fuel = new Carparts("Diesel", 1000);
        Car color = new Carparts("Red", 1000);
        Car gps = new Carparts("Gps", 1000);
        Car stereo = new Carparts("Stereo", 1000);
        CarCategory addons = new CarCategory("Addon");
        addons.add(gps);
        addons.add(stereo);

        car.setUser("Deg");
        car.setName("Hei");
        car.setFuel(fuel);
        car.setWheels(wheels);
        car.setColor(color);
        car.setAddons(addons);

        NewCar car2 = new NewCar();
        Car wheels2 = new Carparts("Big Wheels", 1000);
        Carparts fuel2 = new Carparts("Electric", 1000);
        Car color2 = new Carparts("Blue", 1000);
        Car radio = new Carparts("Radio", 1000);
        Car cupholder = new Carparts("Cupholder", 1000);
        CarCategory addons2 = new CarCategory("Addon");
        addons2.add(radio);
        addons2.add(cupholder);

        car2.setUser("Hei");
        car2.setName("Deg");
        car2.setFuel(fuel2);
        car2.setWheels(wheels2);
        car2.setColor(color2);
        car2.setAddons(addons2);

        carList.add(car);
        carList.add(car2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showCars();

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


        //Adding tablecolumns, need to find a way to add only as many as max amount of addon buttons currently in the quaternarycontroller, because you cant have more addons than the amount of buttons there.
        for(int i = 0; i < 4; i ++) {
            TableColumn<NewCar, String> tc = new TableColumn<>("Addon " + (i + 1));
            addon.getColumns().add(tc);
        }

        for (int i = 0; i < carList.size(); i++) {
            int finalI = i;
            user.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getUser()));

            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));

            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName()));

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName()));

            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName()));

            for (int j = 0; j < carList.get(i).getAddons().size(); j++) {
                int finalJ = j;
                TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addon.getColumns().get(j);

                tc.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getAddons().getElement(finalJ).getName()));
            }
        }
        tableView.setItems(carList);
    }
}
