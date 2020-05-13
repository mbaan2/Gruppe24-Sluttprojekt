package Gruppe24.OSLOMET.DataValidation;

import Gruppe24.OSLOMET.ExceptionClasses.InvalidPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidPriceTest{
    @Test
    void priceTest() {
        assertTrue(ValidPrice.validPrice(1));
        assertTrue(ValidPrice.validPrice(100));
        assertTrue(ValidPrice.validPrice(100000));
        assertTrue(ValidPrice.validPrice(10000000));
        assertTrue(ValidPrice.validPrice(0));
        assertTrue(ValidPrice.validPrice(0));

        assertFalse(ValidPrice.validPrice(-1));
        assertFalse(ValidPrice.validPrice(-900));
    }
}