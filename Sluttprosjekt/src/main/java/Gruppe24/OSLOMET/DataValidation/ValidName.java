package Gruppe24.OSLOMET.DataValidation;

import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.UserLogin.User;
import javafx.scene.control.Alert;
import kotlin.text.Regex;

import java.io.IOException;
import java.util.ArrayList;

public class ValidName{
    public static boolean locationTest(String name){
        Regex reg = new Regex("[A-ZØÅÆ][a-zøåæ]+([ ]([A-ZØÅÆ][a-zøåæ]+))*");
        if(name.matches(reg.getPattern())){
            return true;
        } else {
            return false;
        }
    }

    public static boolean usernameTest(String name){
        Regex reg = new Regex("[A-Za-zøåæØÅÆ0-9_]+");
        if(name.matches(reg.getPattern())){
            return true;
        } else {
            return false;
        }
    }

    public static boolean carpartNameTest(String name){
        Regex reg = new Regex("[A-ZØÅÆa-zøåæ0-9._]+([ ]([A-ZØÅÆa-zøåæ0-9._]+))*");
        if(name.matches(reg.getPattern())){
            return true;
        } else {
            return false;
        }
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

    public static boolean carNameTest(boolean unique, String newCarName){
        if (unique){
            boolean isValid = ValidName.carpartNameTest(newCarName);
            return isValid;
        }else{
            return false;
        }
    }
}
