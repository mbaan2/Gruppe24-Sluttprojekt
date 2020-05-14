package Gruppe24.OSLOMET.SuperUserClasses.RestoreFiles;

import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.ExceptionClasses.SaveFileException;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.User;
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
    List<User> userList = new ArrayList<>();
    HashMap<String, String> userBase = new HashMap<>();
    List<String> secretQList = new ArrayList<>();

    public void createCars() throws SaveFileException {
        setCars();
        FileSaverJobj.SavingCarArray(StandardPaths.carsPath, carList);
    }

    public void setCars() {
        NewCar car1 = new NewCar();
        NewCar car2 = new NewCar();
        NewCar car3 = new NewCar();
        NewCar car4 = new NewCar();

        car1.setUser("Philip");
        car1.setName("GreatCar");
        car1.setFuel(new Carparts("Diesel", 20000));
        car1.setWheels(new Carparts("Medium wheels", 2500));
        car1.setColor(new Carparts("Blue", 2500));
        CarCategory addons = new CarCategory("Addons");
        addons.add(new Carparts("Spoiler", 4000));
        addons.add(new Carparts("Subwoofer", 7500));
        car1.setAddons(addons);
        car1.setCost(car1.getCost());

        car2.setUser("Philip");
        car2.setName("BestCar");
        car2.setFuel(new Carparts("Gasoline", 15000));
        car2.setWheels(new Carparts("Big wheels", 5000));
        car2.setColor(new Carparts("Black", 0));
        CarCategory addons2 = new CarCategory("Addons");
        addons2.add(new Carparts("Spoiler", 4000));
        addons2.add(new Carparts("Subwoofer", 7500));
        addons2.add(new Carparts("GPS", 5000));
        car2.setAddons(addons2);
        car2.setCost(car2.getCost());

        car3.setUser("Karen");
        car3.setName("ZoomZoom");
        car3.setFuel(new Carparts("Electric", 35000));
        car3.setWheels(new Carparts("Medium wheels", 2500));
        car3.setColor(new Carparts("Red", 5000));
        CarCategory addons3 = new CarCategory("Addons");
        addons3.add(new Carparts("Spoiler", 4000));
        addons3.add(new Carparts("GPS", 5000));
        car3.setAddons(addons3);
        car3.setCost(car3.getCost());

        car4.setUser("Karen");
        car4.setName("ChooChoo");
        car4.setFuel(new Carparts("Electric", 35000));
        car4.setWheels(new Carparts("Small wheels", 0));
        car4.setColor(new Carparts("Yellow", 2500));
        CarCategory addons4 = new CarCategory("Addons");
        addons4.add(new Carparts("Subwoofer", 7500));
        car4.setAddons(addons4);
        car4.setCost(car4.getCost());

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

    public void createColors() throws SaveFileException{
        setColors();
        Path filsti = Paths.get(StandardPaths.colorPath);
        FileSaverJobj.SaveCarCategory(filsti, colorOptions);
    }

    public void setWheels(){
        Carparts wheelsBig = new Carparts("Big wheels", 5000);
        Carparts wheelsSmall = new Carparts("Small wheels", 0);
        Carparts wheelsMedium = new Carparts("Medium wheels", 2500);

        wheelOptions.add(wheelsBig);
        wheelOptions.add(wheelsMedium);
        wheelOptions.add(wheelsSmall);
    }

    public void createWheels() throws SaveFileException{
        setWheels();
        Path filsti = Paths.get(StandardPaths.wheelPath);
        FileSaverJobj.SaveCarCategory(filsti, wheelOptions);
    }

    public void setFuel(){
        Carparts diesel = new Carparts("Diesel", 20_000);
        Carparts gasoline = new Carparts("Gasoline", 15_000);
        Carparts electric = new Carparts("Electric", 35_000);

        fuelOptions.add(gasoline);
        fuelOptions.add(diesel);
        fuelOptions.add(electric);
    }

    public void createFuel() throws SaveFileException{
        setFuel();
        Path filsti = Paths.get(StandardPaths.fuelPath);
        FileSaverJobj.SaveCarCategory(filsti, fuelOptions);
    }

    public void setAddOns(){
        Carparts addOneGPS = new Carparts("GPS", 5000);
        Carparts spoiler = new Carparts("Spoiler", 4000);
        Carparts subwoofer = new Carparts("Subwoofer", 7500);

        addOnOptions.add(addOneGPS);
        addOnOptions.add(spoiler);
        addOnOptions.add(subwoofer);
    }

    public void createAddons() throws SaveFileException{
        setAddOns();
        Path filsti = Paths.get(StandardPaths.addonPath);
        FileSaverJobj.SaveCarCategory(filsti, addOnOptions);
    }

    public void setUser() {
        User user = new User("Karen", "karenpassord", "Oslo", "Other", "Whos your daddy?", "Someone");
        User user1 = new User("Cutipie", "heipådeg", "Oslo", "Male", "Whos your daddy?", "Me");
        User user2 = new User("Bruker200", "bruker200", "Location", "Male", "Mothers maiden name?", "???");
        User user3 = new User("Philip", "Passord1", "Oslo", "Male", "Name of first pet?", "Dog");
        User user4 = new User("Bruker", "passord", "Nowhere", "Female", "Name of first pet?", "Katt");

        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userBase.put(user.getUsername(), user.getPassword());
        userBase.put(user1.getUsername(), user1.getPassword());
        userBase.put(user2.getUsername(), user2.getPassword());
        userBase.put(user3.getUsername(), user3.getPassword());
        userBase.put(user4.getUsername(), user4.getPassword());
    }

    public void createUser() throws SaveFileException {
        setUser();
        FileSaverJobj.SaveUser(userBase);
        FileSaverJobj.SaveUserList(userList);
    }

    public void createSecretQ() throws SaveFileException {
        String checkBoxQType = "Name of first pet?";
        String checkBoxQType2 = "Mothers maiden name?";
        String checkBoxQType3 = "Whos your daddy?";

        secretQList.add(checkBoxQType);
        secretQList.add(checkBoxQType2);
        secretQList.add(checkBoxQType3);

        FileSaverJobj.SaveSecretQList(secretQList);
    }
}
