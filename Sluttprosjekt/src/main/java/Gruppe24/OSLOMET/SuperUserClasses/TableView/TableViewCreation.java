package Gruppe24.OSLOMET.SuperUserClasses.TableView;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.Controllers.Userlist_Controller;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.ImportUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
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

import java.io.File;
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
    public ObservableList<String> secretQObsList = FXCollections.observableArrayList();
    public List<String> secretQList = new ArrayList<>();
    public ObservableList<User> userList = FXCollections.observableArrayList();

    public void initializeTv(TableView<NewCar> tv, Label lbl, Boolean tableviewSuperUser) {
        userBase = FileOpenerJobj.openFileHashMap();
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
            user.setOnEditCommit(event -> {
                if(userBase.containsKey(event.getNewValue())) {
                    if(event.getRowValue().getUser().equals(event.getNewValue())) {
                        lbl.setText("The car already has " + event.getNewValue() + " as a username.");
                    } else {
                        lbl.setText("User " + event.getRowValue().getUser() + " changed to " + event.getNewValue() + " for the car named " + event.getRowValue().getName() + ".");
                        event.getRowValue().setUser(event.getNewValue());
                    }
                } else {
                    lbl.setText("No user exists with that name.");
                }
                btnSaveChanges();
                tv.refresh();
            });

            name.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getName()));
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setOnEditCommit(event -> {
                if(event.getRowValue().getName().equals(event.getNewValue())) {
                    lbl.setText("The car already has " + event.getNewValue() + " as a name");
                } else {
                    lbl.setText("Name of the car changed from " + event.getRowValue().getName() + " to " + event.getNewValue() + ".");
                    event.getRowValue().setName(event.getNewValue());
                }
                btnSaveChanges();
            });

            ObservableList<String> fuelList = FXCollections.observableArrayList();
            List<Carparts> fuelOptions = new ArrayList<>();
            try {
                fuelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.fuelPath));
            } catch (ClassNotFoundException | IOException e) {
                System.err.println(e.getMessage());
            }
            fuelOptions.forEach(car -> fuelList.add(car.getName()));

            fuel.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getFuel().getName() + " (" + car.getValue().getFuel().getCost() + "kr)"));
            fuel.setCellFactory(ComboBoxTableCell.forTableColumn(fuelList));
            List<Carparts> finalFuelOptions = fuelOptions;
            fuel.setOnEditCommit(event -> {
                for(int j = 0; j < finalFuelOptions.size(); j++){
                    if(event.getNewValue().equals(finalFuelOptions.get(j).getName())) {
                        if(event.getRowValue().getFuel().getName().equals(event.getNewValue())){
                            lbl.setText("The car already has " + event.getNewValue() + " as fuel.");
                        } else {
                            lbl.setText("The fuel of " + event.getRowValue().getName() + " changed from " + event.getRowValue().getFuel().getName() + " to " + event.getNewValue() + ".");
                            event.getRowValue().setCost(event.getRowValue().getCost() - event.getRowValue().getFuel().getCost());
                            event.getRowValue().setFuel(finalFuelOptions.get(j));
                            event.getRowValue().setCost(event.getRowValue().getCost() + event.getRowValue().getFuel().getCost());
                        }
                    }
                }
                btnSaveChanges();
                tv.refresh();
            });


            ObservableList<String> wheelList = FXCollections.observableArrayList();
            List<Carparts> wheelOptions = new ArrayList<>();
            try {
                wheelOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.wheelPath));
            } catch (ClassNotFoundException | IOException e) {
                System.err.println(e.getMessage());
            }
            wheelOptions.forEach(car -> wheelList.add(car.getName()));

            wheels.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getWheels().getName() + " (" + car.getValue().getWheels().getCost() + "kr)"));
            wheels.setCellFactory(ComboBoxTableCell.forTableColumn(wheelList));
            List<Carparts> finalWheelOptions = wheelOptions;
            wheels.setOnEditCommit(event -> {
                for(int j = 0; j < finalWheelOptions.size(); j++){
                    if(event.getNewValue().equals(finalWheelOptions.get(j).getName())) {
                        if(event.getRowValue().getWheels().getName().equals(event.getNewValue())) {
                            lbl.setText("The car already has " + event.getNewValue() + ".");
                        } else {
                            lbl.setText("The wheels of " + event.getRowValue().getName() + " changed from " + event.getRowValue().getWheels().getName() + " to " + event.getNewValue() + ".");
                            event.getRowValue().setCost(event.getRowValue().getCost() - event.getRowValue().getWheels().getCost());
                            event.getRowValue().setWheels(finalWheelOptions.get(j));
                            event.getRowValue().setCost(event.getRowValue().getCost() + event.getRowValue().getWheels().getCost());
                        }
                    }
                }
                btnSaveChanges();
                tv.refresh();
            });

            ObservableList<String> colorList = FXCollections.observableArrayList();
            List<Carparts> colorOptions = new ArrayList<>();
            try {
                colorOptions = FileOpenerJobj.openFile(Paths.get(StandardPaths.colorPath));
            } catch (ClassNotFoundException | IOException e) {
                System.err.println(e.getMessage());
            }
            colorOptions.forEach(car -> colorList.add(car.getName()));

            color.setCellValueFactory(car -> new SimpleStringProperty(car.getValue().getColor().getName() + " (" + car.getValue().getColor().getCost() + "kr)"));
            color.setCellFactory(ComboBoxTableCell.forTableColumn(colorList));
            List<Carparts> finalColorOptions = colorOptions;
            color.setOnEditCommit(event -> {
                for(int j = 0; j < finalColorOptions.size(); j++){
                    if(event.getNewValue().equals(finalColorOptions.get(j).getName())) {
                        if(event.getRowValue().getColor().getName().equals(event.getNewValue())) {
                            lbl.setText("The car already has " + event.getNewValue() + " color.");
                        } else {
                            lbl.setText("The color of " + event.getRowValue().getName() + " changed from " + event.getRowValue().getColor().getName() + " to " + event.getNewValue() + ".");
                            event.getRowValue().setCost(event.getRowValue().getCost() - event.getRowValue().getColor().getCost());
                            event.getRowValue().setColor(finalColorOptions.get(j));
                            event.getRowValue().setCost(event.getRowValue().getCost() + event.getRowValue().getColor().getCost());
                        }
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
                                    lbl.setText(addonSupUser.get(k).getName() + " added to " + newcar.getName() + ".");
                                    newcar.getAddons().add(addonSupUser.get(k));
                                    newcar.setCost(newcar.getCost() + addonSupUser.get(k).getCost());
                                }
                            }
                        } else {
                            for(int k = 0; k < newcar.getAddons().size(); k++) {
                                if(newcar.getAddons().getElement(k).getName().toLowerCase().equals(tc.getText().toLowerCase())) {
                                    lbl.setText(newcar.getAddons().getElement(k).getName() + " removed from " + newcar.getName() + ".");
                                    newcar.setCost(newcar.getCost() - newcar.getAddons().getElement(k).getCost());
                                    newcar.getAddons().remove(k);
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
                        Tooltip deleteInfo = new Tooltip("Pressing this button will delete the addon.\nThis action cannot be undone.");
                        unmatchedAddon.setDisable(false);
                        deleteInfo.setWrapText(true);
                        deleteInfo.setShowDelay(Duration.millis(5));
                        deleteInfo.setStyle("&#10#10");
                        unmatchedAddon.setTooltip(deleteInfo);

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

            price.setCellValueFactory(car -> new ObservableValueBase<Integer>(){
                @Override
                public Integer getValue(){
                    return car.getValue().getCost();
                }
            });

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

    public void openFile() throws IOException{
        Path path = Paths.get(StandardPaths.addonPath);
        try {
            addonSupUser = FileOpenerJobj.openFile(path);
        } catch (ClassNotFoundException | IOException e) {
            System.err.println(e.getMessage());
        }
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

    private void deleteDeprecatedAddon(ActionEvent actionEvent, Car addon, CarCategory addonlist, TableView<NewCar> tv, Label lbl){
        addonlist.remove(addon);
        lbl.setText("Deprecated addon named " + addon.getName() + " removed from the car.");
        btnSaveChanges();
        tv.refresh();
    }

    void openCars() {
        carList.clear();

        ArrayList<NewCar> list2 = new ArrayList<>();
        try{
            list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
        carList.addAll(list2);
    }

    void deleteCar(ObservableList<NewCar> carList, NewCar car, TableView<NewCar> tv, Label lbl){
        carList.remove(car);
        lbl.setText(car.getName() + " removed from the list.");
        btnSaveChanges();
        tv.refresh();
    }

    void openUsers() throws IOException {
        userBase.clear();
        userList.clear();
        userBase = FileOpenerJobj.openFileHashMap();
        userList = ImportUser.readUser(StandardPaths.usersTXTPath);
    }

    public void initializeUserTv(TableView<User> tableView, Label lblUserList) throws IOException {
        openUsers();
        TableColumn<User, String> username = new TableColumn<>("Username");
        tableView.getColumns().add(username);
        username.setMinWidth(100);
        TableColumn<User, String> pw = new TableColumn<>("Password");
        tableView.getColumns().add(pw);
        pw.setMinWidth(100);
        TableColumn<User, String> location = new TableColumn<>("Location");
        tableView.getColumns().add(location);
        location.setMinWidth(100);
        TableColumn<User, String> gender = new TableColumn<>("Gender");
        tableView.getColumns().add(gender);
        gender.setMinWidth(60);
        TableColumn<User, String> secretQ = new TableColumn<>("Secret Question");
        tableView.getColumns().add(secretQ);
        secretQ.setMinWidth(170);
        TableColumn<User, String> secretQA = new TableColumn<>("Secret Question \n      Answer");
        tableView.getColumns().add(secretQA);
        secretQA.setMinWidth(75);

        username.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getUsername()));
        username.setCellFactory(TextFieldTableCell.forTableColumn());
        username.setOnEditCommit(user -> {
            if(user.getRowValue().getUsername().equals(user.getNewValue()) && userBase.containsKey(user.getRowValue().getUsername())) {
                lblUserList.setText("That username is already in use.");
            } else {
                lblUserList.setText("Username changed from " + user.getRowValue().getUsername() + " to " + user.getNewValue() + ".");
                user.getRowValue().setUsername(user.getNewValue());
                btnSaveUserChanges(lblUserList);
            }
            tableView.refresh();
        });
        pw.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getPassword()));
        pw.setCellFactory(TextFieldTableCell.forTableColumn());
        pw.setOnEditCommit(user -> {
            if(user.getRowValue().getPassword().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that password.");
            } else {
                lblUserList.setText("The password for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getPassword() + " to " + user.getNewValue() + ".");
                user.getRowValue().setPassword(user.getNewValue());
                btnSaveUserChanges(lblUserList);
            }
            tableView.refresh();
        });
        location.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getLocation()));
        location.setCellFactory(TextFieldTableCell.forTableColumn());
        location.setOnEditCommit(user -> {
            if(user.getRowValue().getLocation().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that location.");
            } else {
                lblUserList.setText("The location for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getLocation() + " to " + user.getNewValue() + ".");
                user.getRowValue().setLocation(user.getNewValue());
                btnSaveUserChanges(lblUserList);
            }
            tableView.refresh();
        });
        ObservableList<String> genderList = Userlist_Controller.genderList();
        gender.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getGender()));
        gender.setCellFactory(ComboBoxTableCell.forTableColumn(genderList));
        gender.setOnEditCommit(user -> {
            if(user.getRowValue().getGender().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that gender.");
            } else {
                lblUserList.setText("The gender for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getGender() + " to " + user.getNewValue() + ".");
                user.getRowValue().setGender(user.getNewValue());
                btnSaveUserChanges(lblUserList);
            }
            tableView.refresh();
        });
        secretQList = FileOpenerJobj.openSecretQList();
        secretQObsList.addAll(secretQList);
        secretQ.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSecretQ()));
        secretQ.setCellFactory(ComboBoxTableCell.forTableColumn(secretQObsList));
        secretQ.setOnEditCommit(user -> {
            if(user.getRowValue().getSecretQ().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that secret question.");
            } else {
                lblUserList.setText("The secret question for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getSecretQ() + " to " + user.getNewValue());
                user.getRowValue().setSecretQ(user.getNewValue());
                btnSaveUserChanges(lblUserList);
            }
            tableView.refresh();
        });
        secretQA.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSecretQAnswer()));
        secretQA.setCellFactory(TextFieldTableCell.forTableColumn());
        secretQA.setOnEditCommit(user -> {
            if(user.getRowValue().getSecretQAnswer().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that answer.");
            } else {
                lblUserList.setText("The secret questions answer for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getSecretQAnswer() + " to " + user.getNewValue() + ".");
                user.getRowValue().setSecretQAnswer(user.getNewValue());
                btnSaveUserChanges(lblUserList);
            }
            tableView.refresh();
        });

        TableColumn<User, Button> deleteBtn = new TableColumn<>();
        tableView.getColumns().add(deleteBtn);

        deleteBtn.setCellValueFactory(user -> {
            Button delete = new Button();
            delete.setText("Delete");
            delete.getStyleClass().add("delete-button");
            delete.setAlignment(Pos.CENTER);
            delete.setOnAction(event -> deleteUser(userList, user.getValue(), userBase, tableView, lblUserList));

            ObservableValue<Button> btn = new ObservableValueBase<Button>() {
                @Override
                public Button getValue() {
                    return delete;
                }
            };
            return btn;
        });
        tableView.setItems(userList);
    }

    void deleteUser(ObservableList<User> userList, User user, HashMap<String, String> userBase, TableView<User> tv, Label lblUserList){
        userList.remove(user);
        userBase.remove(user.getUsername(), user.getPassword());
        Path path = Paths.get(user.getUsername() + "sCars.txt");
        if(!path.toString().isEmpty()) {
            File selectedFile = new File(String.valueOf(path));
            selectedFile.delete();
        }
        lblUserList.setText(user.getUsername() + " deleted. Its associated files are also deleted.");
        btnSaveUserChanges(lblUserList);
        tv.refresh();
    }

    private void btnSaveUserChanges(Label lblUserList) {
        try {
            userList = ImportUser.readUser(StandardPaths.usersTXTPath);
        } catch (IOException e) {
            lblUserList.setText(e.getLocalizedMessage());
        }
        List<User> newList = new ArrayList<>(userList);
        try {
            String str = FormatUser.formatUsers(newList);
            Path path = Paths.get(StandardPaths.usersTXTPath);
            File selectedFile = new File(String.valueOf(path));
            WriteUser.writeString(selectedFile, str);
        } catch (IOException e) {
            lblUserList.setText(e.getLocalizedMessage());
        }

        HashMap<String, String> newMap = new HashMap<>(userBase);
        try {
            FileSaverJobj.SaveUser(newMap);
        } catch (IOException e) {
            lblUserList.setText(e.getLocalizedMessage());
        }
    }
}