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
            throw new InvalidNameException("Enter a valid location!");
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
            throw new InvalidNameException("Enter a valid username!");
        }
        return valid;
    }

    public static boolean carpartNameTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-ZØÅÆa-zøåæ0-9._]+([ ]([A-ZØÅÆa-zøåæ0-9._]+))*");
        boolean valid = false;
        if(name.equals("")){
            throw new InvalidNameException("Please insert a name");
        }

        if(name.matches(reg.getPattern())){
            valid = true;
        } else {
            throw new InvalidNameException("Your carpart name is invalid");
        }
        return valid;
    }

    public static boolean uniqueCarNameTest(String newCarName, String user)throws IOException {
        boolean isUnique = true;

        try{
            ArrayList<NewCar> carList = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);

            for (NewCar newCar : carList){
                String username = newCar.getUser();
                String carname = newCar.getName();
                if (username.equals(user)){
                    if (carname.equals(newCarName)){
                        isUnique = false;
                    }
                }
            }
        }catch (IOException e){
            throw new IOException("An error has occurred while opening the cars file, please contact your superUser to restore this file");
        }
        return isUnique;
    }

    public static boolean carNameTest(String newCarName) throws InvalidNameException{
        boolean valid;
        try{
            ValidName.carpartNameTest(newCarName);
            valid = true;
        } catch (InvalidNameException e){
            throw new InvalidNameException(e.getMessage());
        }

        return valid;
    }
}
