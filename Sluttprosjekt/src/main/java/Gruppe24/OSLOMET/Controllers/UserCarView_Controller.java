package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.Filter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserCarView_Controller implements Initializable {

    @FXML
    private AnchorPane userViewPane;

    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private Label tvLabel;

    ObservableList<NewCar> carList = FXCollections.observableArrayList();
    ObservableList<NewCar> usersCarList = FXCollections.observableArrayList();

    @FXML
    void loadYourCars() {
        tableView.setItems(usersCarList);
        if(tableView.getItems().isEmpty()) {
            tableView.setVisible(false);
            tvLabel.setText("You dont have any saved cars!");
        } else {
            tableView.setVisible(true);
            Stage stage = (Stage) userViewPane.getScene().getWindow();
            stage.setWidth(850);
            stage.setHeight(470);
        }
    }

    @FXML
    void goBack() {
        try {
            App.setRoot("WelcomeScreen");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void logout() {
        try {
            App.setRoot("Login");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    void showCars() {
        ArrayList<NewCar> list2;
        try {
            list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            carList.addAll(list2);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableView.setVisible(false);
        showCars();
        String username = App.car.getUser();
        usersCarList =  Filter.usernameFilter(username, carList);
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
        TableColumn<NewCar, Integer> totalprice = new TableColumn<>("Totalprice");
        tableView.getColumns().add(totalprice);


        //Loading of the data into the tableview
        for (int i = 0; i < usersCarList.size(); i++) {
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
            totalprice.setCellValueFactory(car -> new ObservableValueBase<Integer>(){
                @Override
                public Integer getValue(){
                    return car.getValue().getCost();
                }
            });
        }
    }


    private int maxAddons(){
        int antall = 0;
        for(int i = 0 ; i< usersCarList.size(); i++){
            for(int j = 1; j< (usersCarList.get(i).getAddons().size() + 1); j++){
                if(j > antall){
                    antall = j;
                }
            }
        }
        return antall;
    }

    private  void fillEmptyAddons(){
        int maxAntall = maxAddons();
        for(int i = 0 ; i< usersCarList.size(); i++){
            for(int j = 0; j < maxAntall; j++){
                if(j >= usersCarList.get(i).getAddons().size()){
                    Car emptyAddon = new Carparts("", 0);
                    usersCarList.get(i).getAddons().add(emptyAddon);
                }
            }
        }
    }
}
