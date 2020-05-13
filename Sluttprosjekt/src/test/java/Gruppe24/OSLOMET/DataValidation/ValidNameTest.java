package Gruppe24.OSLOMET.DataValidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidNameTest{

    @Test
    void locationTest() {
        assertTrue(ValidName.validLocation("Aaa"));
        assertTrue(ValidName.validLocation("Aa Aa Aa"));
        assertTrue(ValidName.validLocation("Åå Åå"));
        assertTrue(ValidName.validLocation("Aa Aa Aa Aa Aa"));

        assertFalse(ValidName.validLocation(" "));
        assertFalse(ValidName.validLocation("A"));
        assertFalse(ValidName.validLocation("AAA"));
        assertFalse(ValidName.validLocation("aaa"));
        assertFalse(ValidName.validLocation("Âa"));
        assertFalse(ValidName.validLocation("A1a"));
        assertFalse(ValidName.validLocation("Aa A"));
        assertFalse(ValidName.validLocation("RuPaul"));
        assertFalse(ValidName.validLocation("@a"));
        assertFalse(ValidName.validLocation("Aa aa"));

    }

    @Test
    void usernameTest() {
        assertTrue(ValidName.validUsername("Aaa"));
        assertTrue(ValidName.validUsername("aaa"));
        assertTrue(ValidName.validUsername("Åå_Åå"));
        assertTrue(ValidName.validUsername("aAa"));
        assertTrue(ValidName.validUsername("A1a"));
        assertTrue(ValidName.validUsername("RuPaul"));
        assertTrue(ValidName.validUsername("A"));
        assertTrue(ValidName.validUsername("123"));

        assertFalse(ValidName.validUsername("admin"));
        assertFalse(ValidName.validUsername("A a"));
        assertFalse(ValidName.validUsername(" "));
        assertFalse(ValidName.validUsername("Âa"));
        assertFalse(ValidName.validUsername("Aa A"));
        assertFalse(ValidName.validUsername("@a"));
        assertFalse(ValidName.validUsername("Aa aa"));
    }

    @Test
    void carpartNameTest() {
        assertTrue(ValidName.carNameTest("GPS"));
        assertTrue(ValidName.carNameTest("GPS1"));
        assertTrue(ValidName.carNameTest("GPS1.0"));
        assertTrue(ValidName.carNameTest("GPS 1.0"));
        assertTrue(ValidName.carNameTest("GPS 1_0"));
        assertTrue(ValidName.carNameTest("Subwoofer"));
        assertTrue(ValidName.carNameTest("Subwøøfer ver. 1.0.3"));
        assertTrue(ValidName.carNameTest("Sub VERS 1.3_245"));
        assertTrue(ValidName.carNameTest("gps"));

        assertFalse(ValidName.carNameTest(" GPS"));
        assertFalse(ValidName.carNameTest(" "));
        assertFalse(ValidName.carNameTest("Âa"));
        assertFalse(ValidName.carNameTest("@a"));
    }
}