package Gruppe24.OSLOMET.DataValidation;

import kotlin.text.Regex;

public class ValidName{
    public static boolean nameTest(String name){
        Regex reg = new Regex("[A-ZØÅÆ][a-zøåæ]+([ ]([A-ZØÅÆ][a-zøåæ]+))*");
        if(name.matches(reg.getPattern())){
            return true;
        } else {
            return false;
        }
    }

    public static boolean usernameTest(String name){
        Regex reg = new Regex("[A-Za-zøåæØÅÆ1-9_]+");
        if(name.matches(reg.getPattern())){
            return true;
        } else {
            return false;
        }
    }
}
