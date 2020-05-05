package Gruppe24.OSLOMET.DataValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidPriceTest{
    @Test
    void priceTest(){
        assertTrue(ValidPrice.priceTest(1));
        assertTrue(ValidPrice.priceTest(100));
        assertTrue(ValidPrice.priceTest(100000));
        assertTrue(ValidPrice.priceTest(10000000));

        assertFalse(ValidPrice.priceTest(-0));
        assertFalse(ValidPrice.priceTest(-1));
        assertFalse(ValidPrice.priceTest(0));
        assertFalse(ValidPrice.priceTest(-900));
    }
}