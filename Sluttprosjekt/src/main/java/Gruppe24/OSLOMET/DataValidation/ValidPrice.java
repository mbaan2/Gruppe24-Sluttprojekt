package Gruppe24.OSLOMET.DataValidation;

public class ValidPrice{
    public static boolean priceTest(int price){
        if (price >= 0){
            return true;
        } else {
            return false;
        }
    }
}
