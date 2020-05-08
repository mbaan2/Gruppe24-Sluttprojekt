package Gruppe24.OSLOMET.DataValidation;

import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import javafx.scene.control.Alert;
import kotlin.text.Regex;

import java.io.IOException;
import java.util.ArrayList;

public class ValidName {
    public static boolean locationTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-ZØÅÆ][a-zøåæ]+([ ]([A-ZØÅÆ][a-zøåæ]+))*");
        boolean valid;
        if(name.matches(reg.getPattern())){
            valid = true;
        } else {
            valid = false;
            throw new InvalidNameException("Your location is invalid");
        }
        return valid;
    }

    public static boolean usernameTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-Za-zøåæØÅÆ0-9_]+");
        Regex adminReg = new Regex ("admin");
        boolean valid;
        if(name.matches(reg.getPattern())){
            valid = true;
        } else if (name.matches(adminReg.toString())){
            valid = false;
            throw new InvalidNameException("Username admin is reserved.");
        } else {
            valid = false;
            throw new InvalidNameException("Your username contains invalid characters.");
        }
        return valid;
    }

    public static boolean carpartNameTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-ZØÅÆa-zøåæ0-9._]+([ ]([A-ZØÅÆa-zøåæ0-9._]+))*");
        boolean valid;
        if(name.matches(reg.getPattern())){
            valid = true;
        } else {
            valid = false;
            throw new InvalidNameException("Your carpart name is invalid");
        }
        return valid;
    }

    public static boolean uniqueCarNameTest(String newCarName, String user){
        try{
            ArrayList<NewCar> carList = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            boolean isAmatch = false;

            for (NewCar c : carList){
                String username = c.getUser();
                String carname = c.getName();
                if (username.equals(user)){
                    if (carname.equals(newCarName)){
                        isAmatch = true;
                    }
                }
            }
            return !isAmatch;

        }catch (IOException e){
            e.printStackTrace();
            Alert carListProblem = new Alert(Alert.AlertType.ERROR);
            carListProblem.setHeaderText("Program encountered a fatal error.");
            carListProblem.setContentText("Your list of cars is corrupted.\nContact administrator.");
            return false;
        }
    }

    public static boolean carNameTest(boolean unique, String newCarName) throws InvalidNameException{
        boolean valid;
        if (unique){
            boolean isValid = ValidName.carpartNameTest(newCarName);
            valid = isValid;
        }else{
            valid = false;
            throw new InvalidNameException("Your car name is invalid");
        }
        return valid;
    }
}
