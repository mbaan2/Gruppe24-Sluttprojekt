package Gruppe24.OSLOMET.DataValidation;

import Gruppe24.OSLOMET.ExceptionClasses.InvalidPriceException;

public class ValidPrice{
    public static boolean priceTest(int price) throws InvalidPriceException{
        boolean valid;
        if (price < 0){
            valid = true;
            throw new InvalidPriceException("You have entered an invalid price");
        } else {
            valid = false;
        }
        return valid;
    }
}
