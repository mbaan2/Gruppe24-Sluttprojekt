package Gruppe24.OSLOMET.BuiltCarsHistory;

import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class BuiltCarsSingleton{
    private static BuiltCarsSingleton singleInstance = null;

    private ObservableList<NewCar> carList;

    private BuiltCarsSingleton(){
        carList = FXCollections.observableArrayList();
    }

    public static BuiltCarsSingleton getInstance(){
        if(singleInstance == null){
            singleInstance = new BuiltCarsSingleton();
        }
        return singleInstance;
    }

    public void add(NewCar car, String path) throws IOException{
        carList.add(car);
        FileSaverJobj.addingOnlyOneCarObject(path, car);
    }

    public ObservableList<NewCar> getCarList(){
        return carList;
    }
}
