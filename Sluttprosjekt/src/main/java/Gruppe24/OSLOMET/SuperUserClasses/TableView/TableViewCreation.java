package Gruppe24.OSLOMET.SuperUserClasses.TableView;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TableViewCreation {
    List<Carparts> addonSupUser = new ArrayList<>();
    public ObservableList<NewCar> carList = FXCollections.observableArrayList();

    public void initializeTv(TableView<NewCar> tv) {
        openCars();
        int maxNrAddons = 0;

        try {
            openFile();
            maxNrAddons = addonSupUser.size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Setting of all the colums
        TableColumn<NewCar, String> user = new TableColumn<>("User");
        tv.getColumns().add(user);
        user.setMinWidth(75);
        TableColumn<NewCar, String> name = new TableColumn<>("Name");
        tv.getColumns().add(name);
        name.setMinWidth(75);
        TableColumn<NewCar, String> fuel = new TableColumn<>("Fuel");
        tv.getColumns().add(fuel);
        fuel.setMinWidth(75);
        TableColumn<NewCar, String> wheels = new TableColumn<>("Wheels");
        tv.getColumns().add(wheels);
        wheels.setMinWidth(75);
        TableColumn<NewCar, String> color = new TableColumn<>("Color");
        tv.getColumns().add(color);
        color.setMinWidth(75);
        TableColumn<NewCar, String> addon = new TableColumn<>("Add-ons");
        tv.getColumns().add(addon);

        for(int i = 0; i < maxNrAddons; i++) {
            TableColumn<NewCar, CheckBox> tc = new TableColumn<>(addonSupUser.get(i).getName());
            tc.setText(addonSupUser.get(i).getName());
            addon.getColumns().add(tc);
            tc.setMinWidth(75);
        }

        tv.setEditable(true);
        TableColumn<NewCar, HBox> deprecatedAddon = new TableColumn<>("Out-of-sale");
        addon.getColumns().add(deprecatedAddon);
        TableColumn<NewCar, Integer> price = new TableColumn<>("Total price");
        tv.getColumns().add(price);
        TableColumn<NewCar, Button> deleteBtn = new TableColumn<>("");
        tv.getColumns().add(deleteBtn);

        //Loading of the data into the tableview
        for (int i = 0; i < carList.size(); i++) {

            user.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getUser()));
            user.setCellFactory(TextFieldTableCell.forTableColumn());
            user.setOnEditCommit(event -> event.getRowValue().setUser(event.getNewValue()));

            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

            ObservableList<String> fuelList = FXCollections.observableArrayList();
            List<Carparts> fuelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.fuelPath));
            fuelOptions.forEach(car -> fuelList.add(car.getName()));

            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName()));
            fuel.setCellFactory(ComboBoxTableCell.forTableColumn(fuelList));
            fuel.setOnEditCommit(event -> {
                for(int j = 0; j < fuelOptions.size(); j++){
                    if(event.getNewValue().equals(fuelOptions.get(j).getName())) {
                        event.getRowValue().setCost(event.getRowValue().getCost() - event.getRowValue().getFuel().getCost());
                        event.getRowValue().setFuel(fuelOptions.get(j));
                        event.getRowValue().setCost(event.getRowValue().getCost() + event.getRowValue().getFuel().getCost());
                    }
                }
                btnSaveChanges();
                tv.refresh();
            });


            ObservableList<String> wheelList = FXCollections.observableArrayList();
            List<Carparts> wheelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.wheelPath));
            wheelOptions.forEach(car -> wheelList.add(car.getName()));

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName()));
            wheels.setCellFactory(ComboBoxTableCell.forTableColumn(wheelList));
            wheels.setOnEditCommit(event -> {
                for(int j = 0; j < wheelOptions.size(); j++){
                    if(event.getNewValue().equals(wheelOptions.get(j).getName())) {
                        event.getRowValue().setCost(event.getRowValue().getCost() - event.getRowValue().getWheels().getCost());
                        event.getRowValue().setWheels(wheelOptions.get(j));
                        event.getRowValue().setCost(event.getRowValue().getCost() + event.getRowValue().getWheels().getCost());
                    }
                }
                btnSaveChanges();
                tv.refresh();
            });

            ObservableList<String> colorList = FXCollections.observableArrayList();
            List<Carparts> colorOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.colorPath));
            colorOptions.forEach(car -> colorList.add(car.getName()));

            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName()));
            color.setCellFactory(ComboBoxTableCell.forTableColumn(colorList));
            color.setOnEditCommit(event -> {
                for(int j = 0; j < colorOptions.size(); j++){
                    if(event.getNewValue().equals(colorOptions.get(j).getName())) {
                        event.getRowValue().setCost(event.getRowValue().getCost() - event.getRowValue().getColor().getCost());
                        event.getRowValue().setColor(colorOptions.get(j));
                        event.getRowValue().setCost(event.getRowValue().getCost() + event.getRowValue().getColor().getCost());
                    }
                }
                btnSaveChanges();
                tv.refresh();
            });


            for (int j = 0; j < maxNrAddons; j++) {
                //TableColumn<NewCar, CheckBox> tc = (TableColumn<NewCar, CheckBox>) addon.getColumns().get(j);
                int finalJ = j;
                // Based on: https://o7planning.org/en/11079/javafx-tableview-tutorial
                TableColumn<NewCar, Boolean> tc = (TableColumn<NewCar, Boolean>) addon.getColumns().get(j);
                tc.setCellValueFactory(newCarBooleanCellDataFeatures -> {
                    NewCar newcar = newCarBooleanCellDataFeatures.getValue();
                    SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();
                    booleanProp.set(false);
                    for(int k = 0; k < newcar.getAddons().size(); k++){
                        if(newcar.getAddons().getElement(k).getName().toLowerCase().equals(tc.getText().toLowerCase())){
                            booleanProp.set(true);
                        }
                    }

                    booleanProp.addListener((observableValue, oldValue, newValue) -> {
                        if(newValue){
                            for(int k = 0; k < addonSupUser.size(); k++) {
                                if(addonSupUser.get(k).getName().toLowerCase().equals(tc.getText().toLowerCase())){
                                    newcar.getAddons().add(addonSupUser.get(k));
                                    newcar.setCost(newcar.getCost() +addonSupUser.get(k).getCost());
                                }
                            }
                        } else {
                            for(int k = 0; k < newcar.getAddons().size(); k++) {
                                if(newcar.getAddons().getElement(k).getName().toLowerCase().equals(tc.getText().toLowerCase())) {
                                    newcar.getAddons().remove(k);
                                    newcar.setCost(newcar.getCost() - addonSupUser.get(k).getCost());
                                }
                            }
                        }
                        btnSaveChanges();
                        tv.refresh();
                    });
                    return booleanProp;
                });
                tc.setCellFactory(newCarBooleanTableColumn -> {
                    CheckBoxTableCell<NewCar, Boolean> cell = new CheckBoxTableCell<>();
                    cell.setText(addonSupUser.get(finalJ).getCost() + "kr");
                    cell.setAlignment(Pos.CENTER_LEFT);
                    return cell;
                });
            }

            deprecatedAddon.setCellValueFactory(car -> {
                List<String> availableAddOns = new ArrayList<>();
                for (Car c : addonSupUser){
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
                        unmatchedAddon.setOnAction(actionEvent -> 
                                deleteDeprecatedAddon(actionEvent, car.getValue().getAddons().getElement(finalJ), car.getValue().addons, tv));
                        deprecatedAddonsList.getChildren().add(unmatchedAddon);
                    }
                }
                return new SimpleObjectProperty<HBox>(deprecatedAddonsList);
            });

            price.setCellValueFactory(car -> new ObservableValueBase<Integer>(){
                @Override
                public Integer getValue(){
                    return car.getValue().getCost();
                }
            });
        }
        tv.setItems(carList);
    }

    public void openFile() throws IOException{
        Path path = Paths.get(StandardPaths.addonPath);
        addonSupUser = FileOpenerJobj.openFile(path);
    }

    private void btnSaveChanges(){
        List<NewCar> newList = new ArrayList<>();
        newList.addAll(carList);
        try {
            FileSaverJobj.SavingCarArray(StandardPaths.carsPath, newList);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void deleteDeprecatedAddon(ActionEvent actionEvent, Car addon, CarCategory addonlist, TableView<NewCar> tv){
        addonlist.remove(addon);
        btnSaveChanges();
        tv.refresh();
    }

    void openCars() {
        carList.clear();

        ArrayList<NewCar> list2 = new ArrayList<>();
        try{
            list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        } catch (IOException e){
            e.getMessage();
        }
        carList.addAll(list2);
    }

    void deleteCar(NewCar car){

    }
}
