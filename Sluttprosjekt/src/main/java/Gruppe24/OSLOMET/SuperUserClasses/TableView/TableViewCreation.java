package Gruppe24.OSLOMET.SuperUserClasses.TableView;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.ExceptionClasses.SaveFileException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableViewCreation {
    List<Carparts> addonSupUser = new ArrayList<>();
    public ObservableList<NewCar> carList = FXCollections.observableArrayList();
    public HashMap<String, String> userBase = new HashMap<>();

    public void initializeTv(TableView<NewCar> tv, Label lbl, Boolean tableviewSuperUser) {
        try {
            userBase = FileOpenerJobj.openFileHashMap();
        } catch (IOException | ClassNotFoundException e){
            lbl.setText("Could not load user base.");
        }
        // Opening the cars.jobj file.
        openCars(lbl);

        // Opening the addons.jobj file.
        int maxNrAddons = 0;
        openFile(lbl);
        maxNrAddons = addonSupUser.size();

        //Setting of all the colums
        TableColumn<NewCar, String> user = new TableColumn<>("User");
        tv.getColumns().add(user);
        user.setMinWidth(75);
        TableColumn<NewCar, String> name = new TableColumn<>("Name");
        tv.getColumns().add(name);
        name.setMinWidth(75);
        TableColumn<NewCar, String> fuel = new TableColumn<>("Fuel");
        tv.getColumns().add(fuel);
        if(tableviewSuperUser) {
            fuel.setMinWidth(150);
        } else {
            fuel.setMinWidth(75);
        }
        TableColumn<NewCar, String> wheels = new TableColumn<>("Wheels");
        tv.getColumns().add(wheels);
        if(tableviewSuperUser) {
            wheels.setMinWidth(178);
        } else {
            wheels.setMinWidth(75);
        }
        TableColumn<NewCar, String> color = new TableColumn<>("Color");
        tv.getColumns().add(color);
        if(tableviewSuperUser) {
            color.setMinWidth(130);
        } else {
            color.setMinWidth(75);
        }
        TableColumn<NewCar, String> addon = new TableColumn<>("Add-ons");
        tv.getColumns().add(addon);

        // Setting the addons columns depending on max amount of addons in the list.
        for(int i = 0; i < maxNrAddons; i++) {
            TableColumn<NewCar, CheckBox> tc = new TableColumn<>(addonSupUser.get(i).getName());
            tc.setText(addonSupUser.get(i).getName());
            addon.getColumns().add(tc);
            tc.setMinWidth(75);
        }

        tv.setEditable(true);

        //Setting the deprecated addons column which will contain the out of sale addons that some users cars might have.
        TableColumn<NewCar, HBox> deprecatedAddon = new TableColumn<>("Out-of-sale");
        addon.getColumns().add(deprecatedAddon);
        TableColumn<NewCar, Integer> price = new TableColumn<>("Total price");
        tv.getColumns().add(price);
        TableColumn<NewCar, Button> deleteBtn = new TableColumn<>("");
        tv.getColumns().add(deleteBtn);

        //Loading of the data into the tableview
        for (int i = 0; i < carList.size(); i++) {

            // Setting the username columns data and how editing works for it.
            user.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getUser()));
            user.setCellFactory(TextFieldTableCell.forTableColumn());
            user.setOnEditCommit(event -> {
                // The username will only be changed if the user exists (aka the userbase contains the key).
                if(userBase.containsKey(event.getNewValue())) {
                    // If the car that is being edited already has that username it will show up in the label.
                    if(event.getRowValue().getUser().equals(event.getNewValue())) {
                        lbl.setText("The car already has " + event.getNewValue() + " as a username.");
                    } else {
                        lbl.setText("User " + event.getRowValue().getUser() + " changed to " + event.getNewValue() + " for the car named " + event.getRowValue().getName() + ".");
                        event.getRowValue().setUser(event.getNewValue());
                    }
                } else {
                    lbl.setText("No user exists with that name.");
                }
                btnSaveChanges(lbl);
                tv.refresh();
            });

            // Setting the carname columns data and how editing works for it.
            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setOnEditCommit(event -> {
                if(event.getRowValue().getName().equals(event.getNewValue())) {
                    // If the chosen option is the same as the previous value it will let the user know in the label.
                    lbl.setText("The car already has " + event.getNewValue() + " as a name");
                } else {
                    ValidName.carNameTest(event.getNewValue());
                    // If the carname that is being entered is valid it will be updated.
                    lbl.setText("Name of the car changed from " + event.getRowValue().getName() + " to " + event.getNewValue() + ".");
                    event.getRowValue().setName(event.getNewValue());
                }
                btnSaveChanges(lbl);
            });

            // Loading the fuel from the fuel.jobj file.
            ObservableList<String> fuelList = FXCollections.observableArrayList();
            List<Carparts> fuelOptions = new ArrayList<>();
            try {
                fuelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.fuelPath));
            } catch (ClassNotFoundException | IOException e) {
                lbl.setText("Could not load fuel.");
            }
            fuelOptions.forEach(car -> fuelList.add(car.getName()));

            // Setting the fuel columns data and how editing works for it.
            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName() + " (" + car.getValue().getFuel().getCost() + "kr)"));
            // Adding a combobox with the values of the fuel.jobj file to the column.
            fuel.setCellFactory(ComboBoxTableCell.forTableColumn(fuelList));
            List<Carparts> finalFuelOptions = fuelOptions;
            fuel.setOnEditCommit(event -> {
                for(int j = 0; j < finalFuelOptions.size(); j++){
                    if(event.getNewValue().equals(finalFuelOptions.get(j).getName())) {
                        // If the chosen option is the same as the previous value it will let the user know in the label.
                        if(event.getRowValue().getFuel().getName().equals(event.getNewValue())){
                            lbl.setText("The car already has " + event.getNewValue() + " as fuel.");
                        } else {
                            lbl.setText("The fuel of " + event.getRowValue().getName() + " changed from " + event.getRowValue().getFuel().getName() + " to " + event.getNewValue() + ".");
                            event.getRowValue().setFuel(finalFuelOptions.get(j));
                        }
                    }
                }
                btnSaveChanges(lbl);
                tv.refresh();
            });


            ObservableList<String> wheelList = FXCollections.observableArrayList();
            List<Carparts> wheelOptions = new ArrayList<>();
            try {
                wheelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.wheelPath));
            } catch (ClassNotFoundException | IOException e) {
                lbl.setText("Could not load wheels.");
            }
            wheelOptions.forEach(car -> wheelList.add(car.getName()));
            //Setting the wheels columns data and how editing works for it.
            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName() + " (" + car.getValue().getWheels().getCost() + "kr)"));
            // Adding a combobox with the values of the wheels.jobj file to the column.
            wheels.setCellFactory(ComboBoxTableCell.forTableColumn(wheelList));
            List<Carparts> finalWheelOptions = wheelOptions;
            wheels.setOnEditCommit(event -> {
                for(int j = 0; j < finalWheelOptions.size(); j++){
                    if(event.getNewValue().equals(finalWheelOptions.get(j).getName())) {
                        // If the chosen option is the same as the previous value it will let the user know in the label.
                        if(event.getRowValue().getWheels().getName().equals(event.getNewValue())) {
                            lbl.setText("The car already has " + event.getNewValue() + ".");
                        } else {
                            lbl.setText("The wheels of " + event.getRowValue().getName() + " changed from " + event.getRowValue().getWheels().getName() + " to " + event.getNewValue() + ".");
                            event.getRowValue().setWheels(finalWheelOptions.get(j));
                        }
                    }
                }
                btnSaveChanges(lbl);
                tv.refresh();
            });

            ObservableList<String> colorList = FXCollections.observableArrayList();
            List<Carparts> colorOptions = new ArrayList<>();
            try {
                colorOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.colorPath));
            } catch (ClassNotFoundException | IOException e) {
                lbl.setText("Could not load colors.");
            }
            colorOptions.forEach(car -> colorList.add(car.getName()));

            //Setting the color columns data and how editing works for it.
            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName() + " (" + car.getValue().getColor().getCost() + "kr)"));
            // Adding a combobox with the values of the wheels.jobj file to the column.
            color.setCellFactory(ComboBoxTableCell.forTableColumn(colorList));
            List<Carparts> finalColorOptions = colorOptions;
            color.setOnEditCommit(event -> {
                for(int j = 0; j < finalColorOptions.size(); j++){
                    if(event.getNewValue().equals(finalColorOptions.get(j).getName())) {
                        // If the chosen option is the same as the previous value it will let the user know in the label.
                        if(event.getRowValue().getColor().getName().equals(event.getNewValue())) {
                            lbl.setText("The car already has " + event.getNewValue() + " color.");
                        } else {
                            lbl.setText("The color of " + event.getRowValue().getName() + " changed from " + event.getRowValue().getColor().getName() + " to " + event.getNewValue() + ".");

                            event.getRowValue().setColor(finalColorOptions.get(j));

                        }
                    }
                }
                btnSaveChanges(lbl);
                tv.refresh();
            });


            // Setting the data in the addons columns depending on how many addons there are - and adding checkboxes to the columns.
            // If the checkboxes are checked or unchecked it will update the car in realtime and save it to the cars.jobj file.
            for (int j = 0; j < maxNrAddons; j++) {
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
                                    lbl.setText(addonSupUser.get(k).getName() + " added to " + newcar.getName() + ".");
                                    newcar.getAddons().add(addonSupUser.get(k));
                                }
                            }
                        } else {
                            for(int k = 0; k < newcar.getAddons().size(); k++) {
                                if(newcar.getAddons().getElement(k).getName().toLowerCase().equals(tc.getText().toLowerCase())) {
                                    lbl.setText(newcar.getAddons().getElement(k).getName() + " removed from " + newcar.getName() + ".");
                                    newcar.getAddons().remove(k);
                                }
                            }
                        }
                        btnSaveChanges(lbl);
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

            //Setting the data for the deprecated addons column. If the addons in the cars arent a part of the addons.jobj file it will be counted as a deprecated addon.
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
                    // If the addon isnt found in the addons.jobj list it gets added to deprecated addons here.
                    if (!hasMatch){
                        Button unmatchedAddon = new Button();
                        Tooltip deleteInfo = new Tooltip("Pressing this button will delete the addon.\nThis action cannot be undone.");
                        unmatchedAddon.setDisable(false);
                        deleteInfo.setWrapText(true);
                        deleteInfo.setShowDelay(Duration.millis(5));
                        deleteInfo.setStyle("&#10#10");
                        unmatchedAddon.setTooltip(deleteInfo);

                        // Disabling the deprecated buttons for the users car tableview.
                        if(!tableviewSuperUser){
                            deleteInfo.hide();
                            unmatchedAddon.setDisable(true);
                        }
                        unmatchedAddon.setText(car.getValue().getAddons().getElement(j).getName());
                        int finalJ = j;
                        unmatchedAddon.setOnAction(actionEvent ->
                                deleteDeprecatedAddon(actionEvent, car.getValue().getAddons().getElement(finalJ), car.getValue().addons, tv, lbl));
                        deprecatedAddonsList.getChildren().add(unmatchedAddon);
                    }
                }
                return new SimpleObjectProperty<HBox>(deprecatedAddonsList);
            });

            // Setting the data to the totalprice column. It also gets edited in realtime if the user edits the car.
            price.setCellValueFactory(car -> new ObservableValueBase<Integer>(){
                @Override
                public Integer getValue(){
                    return car.getValue().getCost();
                }
            });

            // Adding the delete button to delete cars in the tableview, it also saves the cars.jobj file without the removed car.
            // The removing from the list happens in another method further down in the class.
            deleteBtn.setCellValueFactory(car -> {
                Button delete = new Button();
                delete.setText("Delete");
                delete.getStyleClass().add("delete-button");
                delete.setAlignment(Pos.CENTER);
                delete.setOnAction(actionEvent -> deleteCar(carList, car.getValue(), tv, lbl));

                ObservableValue<Button> btn = new ObservableValueBase<Button>(){
                    @Override
                    public Button getValue(){
                        return delete;
                    }
                };
                return btn;
            });
        }
        tv.setItems(carList);
    }

    public void openFile(Label lbl){
        Path path = Paths.get(StandardPaths.addonPath);
        try {
            addonSupUser = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            lbl.setText("Could not load add-ons.");
        }
    }

    // The method to save changes to the cars.jobj file.
    private void btnSaveChanges(Label lbl){
        List<NewCar> newList = new ArrayList<>();
        newList.addAll(carList);
        try {
            FileSaverJobj.SavingCarArray(StandardPaths.carsPath, newList);
        } catch (SaveFileException e){
            lbl.setText(e.getMessage());
        }
    }

    // The method for deleting deprecated addons from a car when clicking the deprecated addons button.
    private void deleteDeprecatedAddon(ActionEvent actionEvent, Car addon, CarCategory addonlist, TableView<NewCar> tv, Label lbl){
        addonlist.remove(addon);
        lbl.setText("Deprecated addon named " + addon.getName() + " removed from the car.");
        btnSaveChanges(lbl);
        tv.refresh();
    }

    void openCars(Label lbl) {
        carList.clear();

        ArrayList<NewCar> list2 = new ArrayList<>();
        try{
            list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        } catch (IOException e){
            lbl.setText("Could not load the carlist.");
        }
        carList.addAll(list2);
    }

    // The method for deleting the car from the cars.jobj file.
    void deleteCar(ObservableList<NewCar> carList, NewCar car, TableView<NewCar> tv, Label lbl){
        carList.remove(car);
        lbl.setText(car.getName() + " removed from the list.");
        btnSaveChanges(lbl);
        tv.refresh();
    }
}

