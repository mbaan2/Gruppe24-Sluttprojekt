package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TableViewCreation {

    List<Carparts> addonSupUser = new ArrayList<>();
    ObservableList<NewCar> carList = FXCollections.observableArrayList();

    public TableView<NewCar> initializeTv(TableView<NewCar> tv) {
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

        return tv;
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
}
