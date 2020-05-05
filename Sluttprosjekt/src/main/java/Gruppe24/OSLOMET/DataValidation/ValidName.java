package Gruppe24.OSLOMET.DataValidation;

import kotlin.text.Regex;

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
}
