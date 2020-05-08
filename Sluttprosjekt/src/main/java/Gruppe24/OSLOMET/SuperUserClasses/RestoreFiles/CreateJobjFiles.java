package Gruppe24.OSLOMET.SuperUserClasses.RestoreFiles;

import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateJobjFiles {
    List<NewCar> carList = new ArrayList<>();
    List<Carparts> colorOptions = new ArrayList<>();
    List<Carparts> wheelOptions = new ArrayList<>();
    List<Carparts> fuelOptions = new ArrayList<>();
    List<Carparts> addOnOptions = new ArrayList<>();
    ObservableList<User> userList = FXCollections.observableArrayList();
    HashMap<String, String> userBase = new HashMap<>();
    List<String> secretQList = new ArrayList<>();

    public void createCars() {
        setCars();
        try {
            FileSaverJobj.SavingCarArray(StandardPaths.carsPath, carList);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void setCars() {
        NewCar car1 = new NewCar();
        NewCar car2 = new NewCar();
        NewCar car3 = new NewCar();
        NewCar car4 = new NewCar();

        car1.setUser("123");
        car1.setName("GreatCar");
        car1.setFuel(new Carparts("Diesel", 20000));
        car1.setWheels(new Carparts("Medium wheels", 2500));
        car1.setColor(new Carparts("Blue", 2500));
        CarCategory addons = new CarCategory("Addons");
        addons.add(new Carparts("Spoiler", 4000));
        addons.add(new Carparts("Subwoofer", 7500));
        car1.setAddons(addons);

        car2.setUser("123");
        car2.setName("BestCar");
        car2.setFuel(new Carparts("Gasoline", 15000));
        car2.setWheels(new Carparts("Big wheels", 5000));
        car2.setColor(new Carparts("Black", 0));
        CarCategory addons2 = new CarCategory("Addons");
        addons2.add(new Carparts("Spoiler", 4000));
        addons2.add(new Carparts("Subwoofer", 7500));
        addons2.add(new Carparts("GPS", 5000));
        car2.setAddons(addons2);

        car3.setUser("234");
        car3.setName("ZoomZoom");
        car3.setFuel(new Carparts("Electric", 35000));
        car3.setWheels(new Carparts("Medium wheels", 2500));
        car3.setColor(new Carparts("Red", 5000));
        CarCategory addons3 = new CarCategory("Addons");
        addons3.add(new Carparts("Spoiler", 4000));
        addons3.add(new Carparts("GPS", 5000));
        car3.setAddons(addons3);

        car4.setUser("Aaa");
        car4.setName("ChooChoo");
        car4.setFuel(new Carparts("Electric", 35000));
        car4.setWheels(new Carparts("Small wheels", 2500));
        car4.setColor(new Carparts("Yellow", 2500));
        CarCategory addons4 = new CarCategory("Addons");
        addons4.add(new Carparts("Subwoofer", 7500));
        car4.setAddons(addons4);

        carList.add(car1);
        carList.add(car2);
        carList.add(car3);
        carList.add(car4);
    }

    public void setColors(){
        Carparts red = new Carparts("Red", 5000);
        Carparts blue = new Carparts("Blue", 2500);
        Carparts yellow = new Carparts("Yellow", 2500);
        Carparts black = new Carparts("Black", 0);
        Carparts green = new Carparts("Green", 3500);

        colorOptions.add(black);
        colorOptions.add(green);
        colorOptions.add(red);
        colorOptions.add(blue);
        colorOptions.add(yellow);
    }

    public void createColors(){
        setColors();
        Path filsti = Paths.get(StandardPaths.colorPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, colorOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setWheels(){
        Carparts wheelsBig = new Carparts("Big wheels", 5000);
        Carparts wheelsSmall = new Carparts("Small wheels", 0);
        Carparts wheelsMedium = new Carparts("Medium wheels", 2500);

        wheelOptions.add(wheelsBig);
        wheelOptions.add(wheelsMedium);
        wheelOptions.add(wheelsSmall);
    }

    public void createWheels(){
        setWheels();
        Path filsti = Paths.get(StandardPaths.wheelPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, wheelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setFuel(){
        Carparts diesel = new Carparts("Diesel", 20_000);
        Carparts gasoline = new Carparts("Gasoline", 15_000);
        Carparts electric = new Carparts("Electric", 35_000);

        fuelOptions.add(gasoline);
        fuelOptions.add(diesel);
        fuelOptions.add(electric);
    }

    public void createFuel(){
        setFuel();
        Path filsti = Paths.get(StandardPaths.fuelPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, fuelOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setAddOns(){
        Carparts addOneGPS = new Carparts("GPS", 5000);
        Carparts spoiler = new Carparts("Spoiler", 4000);
        Carparts subwoofer = new Carparts("Subwoofer", 7500);

        addOnOptions.add(addOneGPS);
        addOnOptions.add(spoiler);
        addOnOptions.add(subwoofer);
    }

    public void createAddons(){
        setAddOns();
        Path filsti = Paths.get(StandardPaths.addonPath);
        try {
            FileSaverJobj.SaveCarCategory(filsti, addOnOptions);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setUser() {
        User newUser = new User("123", "123", "Oslo", "Male", "Whos your daddy?", "123");
        User newUser2 = new User("oyvind91", "oyvind1991", "Oslo", "Male", "Whos your daddy?", "Me");
        User newUser3 = new User("234", "234", "Oslo", "Male", "Name of first pet?", "234");
        User newUser4 = new User("Aaa", "aaa", "aaa", "Other", "Whos your daddy?", "aaa");

        userList.addAll(newUser, newUser2, newUser3, newUser4);
        userBase.put(newUser.getUsername(), newUser.getPassword());
        userBase.put(newUser2.getUsername(), newUser2.getPassword());
        userBase.put(newUser3.getUsername(), newUser3.getPassword());
        userBase.put(newUser4.getUsername(), newUser4.getPassword());
    }

    public void createUser() {
        setUser();
        try{
            FileSaverJobj.SaveUser(userBase);
            String str = FormatUser.formatUsers(userList);
            Path path = Paths.get(StandardPaths.usersTXTPath);
            File selectedFile = new File(String.valueOf(path));
            WriteUser.writeString(selectedFile, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSecretQ() throws IOException {
        String checkBoxQType = "Name of first pet?";
        String checkBoxQType2 = "Mothers maiden name?";
        String checkBoxQType3 = "Whos your daddy?";

        secretQList.add(checkBoxQType);
        secretQList.add(checkBoxQType2);
        secretQList.add(checkBoxQType3);

        FileSaverJobj.SaveSecretQList(secretQList);
    }
}
