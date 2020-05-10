package Gruppe24.OSLOMET.DataValidation;

import Gruppe24.OSLOMET.ExceptionClasses.InvalidPriceException;

public class ValidPrice{
    public static void priceTest(int price) throws InvalidPriceException{
        if (price < 0){
            throw new InvalidPriceException("You have entered an invalid price");
        }
    }

    public static boolean validPrice(int price) throws InvalidPriceException {
        try {
            priceTest(price);
            return true;
        } catch (InvalidPriceException e){
            throw new InvalidPriceException(e.getMessage());
        }
    }
}
