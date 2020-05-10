package Gruppe24.OSLOMET.DataValidation;

import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import kotlin.text.Regex;

import java.io.IOException;
import java.util.ArrayList;

public class ValidName {
    public static void locationTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-ZØÅÆ][a-zøåæ]+([ ]([A-ZØÅÆ][a-zøåæ]+))*");
        if(!name.matches(reg.getPattern())){
            if (!name.matches(".{1,25}")){
                throw new InvalidNameException("Your location name is too long!");
            }
            else {
                throw new InvalidNameException("Enter a valid location!");
            }
        }
    }

    public static boolean validLocation(String name) throws InvalidNameException {
        try {
            validLocation(name);
            return true;
        } catch (InvalidNameException e){
            throw new InvalidNameException(e.getMessage());
        }
    }



        public static void usernameTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-Za-zøåæØÅÆ0-9_]+");
        if (name.matches("admin")){
            throw new InvalidNameException("Username admin is reserved.");
        } else if(name.matches(reg.getPattern())){
            if (name.matches(".{1,20}")){
                /*name is correct */
            } else {
                throw new InvalidNameException("Your username is too long!");
            }
        } else {
            throw new InvalidNameException("Enter a valid username!");
        }
    }

    public static boolean validUsername(String name) throws InvalidNameException {
        try {
            usernameTest(name);
            return true;
        } catch (InvalidNameException e){
            throw new InvalidNameException(e.getMessage());
        }
    }

    public static boolean carpartNameTest(String name) throws InvalidNameException{
        Regex reg = new Regex("[A-ZØÅÆa-zøåæ0-9._]+([ ]([A-ZØÅÆa-zøåæ0-9._]+))*");
        boolean valid = false;
        if(name.equals("")){
            throw new InvalidNameException("Please insert a name");
        }

        if(name.matches(reg.getPattern())){
            if (name.matches(".{1,25}")){
                valid = true;
            }else{
                throw new InvalidNameException("Name is too long");
            }
        } else {
            throw new InvalidNameException("Name is invalid");
        }
        return valid;
    }

    public static boolean uniqueCarNameTest(String newCarName, String user) throws IOException, ClassNotFoundException {
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
