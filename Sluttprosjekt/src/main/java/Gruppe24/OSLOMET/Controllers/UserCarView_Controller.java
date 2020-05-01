package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.Filter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserCarView_Controller implements Initializable {
    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private Label tvLabel;

    ObservableList<NewCar> carList = FXCollections.observableArrayList();
    ObservableList<NewCar> usersCarList = FXCollections.observableArrayList();

    @FXML
    void loadYourCars() {
        String username = App.car.getUser();
        usersCarList = Filter.usernameFilter(username, carList);
        tableView.setItems(usersCarList);
        if(tableView.getItems().isEmpty()) {
            tableView.setVisible(false);
            tvLabel.setText("You dont have any saved cars!");
        } else {
            tableView.setVisible(true);
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
            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));
            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName()));
            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName()));
            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName()));

            for (int j = 0; j < maxAntallAddons; j++) {
                int finalJ = j;
                TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addon.getColumns().get(j);
                tc.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getAddons().getElement(finalJ).getName()));
            }
        }
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
            for(int j = 0; j < maxAntall; j++){
                if(j >= carList.get(i).getAddons().size()){
                    Car emptyAddon = new Carparts("", 0);
                    carList.get(i).getAddons().add(emptyAddon);
                }
            }
        }
    }
}
