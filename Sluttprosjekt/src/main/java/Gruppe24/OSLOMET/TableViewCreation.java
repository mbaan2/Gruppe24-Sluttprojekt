package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TableViewCreation {

    List<Carparts> addonSupUser = new ArrayList<>();
    ObservableList<NewCar> carList = FXCollections.observableArrayList();

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
        TableColumn<NewCar, String> name = new TableColumn<>("Name");
        tv.getColumns().add(name);
        TableColumn<NewCar, String> fuel = new TableColumn<>("Fuel");
        tv.getColumns().add(fuel);
        TableColumn<NewCar, String> wheels = new TableColumn<>("Wheels");
        tv.getColumns().add(wheels);
        TableColumn<NewCar, String> color = new TableColumn<>("Color");
        tv.getColumns().add(color);
        TableColumn<NewCar, String> addon = new TableColumn<>("Add-ons");
        tv.getColumns().add(addon);

        for(int i = 0; i < maxNrAddons; i++) {
            TableColumn<NewCar, CheckBox> tc = new TableColumn<>(addonSupUser.get(i).getName());
            tc.setText(addonSupUser.get(i).getName());
            addon.getColumns().add(tc);
        }

        tv.setEditable(true);
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

            //Adding values to the lists for the comboboxes for editing
            // Fuel doesnt come from a file yet.
            ObservableList<String> fuelList = FXCollections.observableArrayList();
            String dieselFuel = "Diesel Car";
            String electricFuel = "Electric Car";
            String gasFuel = "Gasoline Car";
            fuelList.addAll(dieselFuel, electricFuel, gasFuel);

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

                btnSaveChanges();

            });


            ObservableList<String> wheelList = FXCollections.observableArrayList();
            List<Carparts> wheelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.wheelPath));
            wheelOptions.forEach(car -> wheelList.add(car.getName()));

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName()));
            wheels.setCellFactory(ComboBoxTableCell.forTableColumn(wheelList));
            wheels.setOnEditCommit(event -> {
                for(int j = 0; j < wheelOptions.size(); j++){
                    if(event.getNewValue().equals(wheelOptions.get(j).getName())) {
                        event.getRowValue().setWheels(wheelOptions.get(j));
                    }
                }
                btnSaveChanges();

            });


            ObservableList<String> colorList = FXCollections.observableArrayList();
            List<Carparts> colorOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.colorPath));
            colorOptions.forEach(car -> colorList.add(car.getName()));

            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName()));
            color.setCellFactory(ComboBoxTableCell.forTableColumn(colorList));
            color.setOnEditCommit(event -> {
                for(int j = 0; j < colorOptions.size(); j++){
                    if(event.getNewValue().equals(colorOptions.get(j).getName())) {
                        event.getRowValue().setColor(colorOptions.get(j));
                    }
                }
                btnSaveChanges();
            });


            for (int j = 0; j < maxNrAddons; j++) {
                //TableColumn<NewCar, CheckBox> tc = (TableColumn<NewCar, CheckBox>) addon.getColumns().get(j);
                int finalJ = j;
                TableColumn<NewCar, Boolean> tc = (TableColumn<NewCar, Boolean>) addon.getColumns().get(j);
                tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, Boolean>, ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<NewCar, Boolean> newCarBooleanCellDataFeatures) {
                        NewCar newcar = newCarBooleanCellDataFeatures.getValue();
                        SimpleBooleanProperty booleanProp = new SimpleBooleanProperty();
                        booleanProp.set(false);
                        for(int k = 0; k < newcar.getAddons().size(); k++){
                            if(newcar.getAddons().getElement(k).getName().toLowerCase().equals(tc.getText().toLowerCase())){
                                booleanProp.set(true);
                            }
                        }

                        booleanProp.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                                if(newValue){
                                    List<Carparts> addonOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.addonPath));
                                    for(int k = 0; k < addonOptions.size(); k++) {
                                        if(addonOptions.get(k).getName().toLowerCase().equals(tc.getText().toLowerCase())){
                                            newcar.getAddons().add(addonOptions.get(k));
                                        }
                                    }
                                    btnSaveChanges();
                                    } else {
                                    for(int k = 0; k < newcar.getAddons().size(); k++) {
                                        if(newcar.getAddons().getElement(k).getName().toLowerCase().equals(tc.getText().toLowerCase())) {
                                            newcar.getAddons().remove(k);
                                        }
                                    }
                                    btnSaveChanges();
                                }
                            }
                        });
                        return booleanProp;
                    }
                });
                tc.setCellFactory(new Callback<TableColumn<NewCar, Boolean>, TableCell<NewCar, Boolean>>() {
                    @Override
                    public TableCell<NewCar, Boolean> call(TableColumn<NewCar, Boolean> newCarBooleanTableColumn) {
                        CheckBoxTableCell<NewCar, Boolean> cell = new CheckBoxTableCell<NewCar, Boolean>();
                        cell.setText(addonSupUser.get(finalJ).getCost() + "kr");
                        cell.setAlignment(Pos.CENTER_LEFT);
                        return cell;
                    }
                });





                /*
                tc.setCellValueFactory(car -> {


                    CheckBox checkbox = new CheckBox("kr" + addonSupUser.get(finalJ).getCost());
                    checkbox.setSelected(false);
                    for (int k = 0; k < car.getValue().getAddons().size(); k++) {
                        if (car.getValue().getAddons().getElement(k).getName().toLowerCase().equals(car.getTableColumn().getText().toLowerCase())) {
                            checkbox.setSelected(true);
                        }

                        int finalK = k;
                        //checkbox.setOnAction(actionEvent -> handleAddonCheckbox(actionEvent, addonSupUser.get(finalJ), car.getValue().getAddons().getElement(finalK), car.getValue().addons, checkbox.isSelected(), tv));
                    }

                    return new SimpleObjectProperty<CheckBox>(checkbox);
                });

                 */
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
                        unmatchedAddon.setOnAction(actionEvent -> deleteDeprecatedAddon(actionEvent, car.getValue().getAddons().getElement(finalJ), car.getValue().addons, tv));
                        deprecatedAddonsList.getChildren().add(unmatchedAddon);
                    }
                }
                return new SimpleObjectProperty<HBox>(deprecatedAddonsList);
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

    private void handleAddonCheckbox(ActionEvent actionEvent, Car addonSupUser, Car addonUser, CarCategory addonsCarUser, boolean selected, TableView<NewCar> tv){
        if (!selected){
            addonsCarUser.remove(addonUser);

        } else {
            addonsCarUser.add(addonSupUser);
        }
        tv.refresh();
    }

    private void deleteDeprecatedAddon(ActionEvent actionEvent, Car addon, CarCategory addonlist, TableView<NewCar> tv){
        addonlist.remove(addon);
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
}
