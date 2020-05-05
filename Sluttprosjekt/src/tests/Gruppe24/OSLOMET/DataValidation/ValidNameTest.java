package Gruppe24.OSLOMET.DataValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidNameTest{

    @Test
    void locationTest(){
        assertTrue(ValidName.locationTest("Aaa"));
        assertTrue(ValidName.locationTest("Aa Aa Aa"));
        assertTrue(ValidName.locationTest("Åå Åå"));
        assertTrue(ValidName.locationTest("Aa Aa Aa Aa Aa"));

        assertFalse(ValidName.locationTest(" "));
        assertFalse(ValidName.locationTest("A"));
        assertFalse(ValidName.locationTest("AAA"));
        assertFalse(ValidName.locationTest("aaa"));
        assertFalse(ValidName.locationTest("Âa"));
        assertFalse(ValidName.locationTest("A1a"));
        assertFalse(ValidName.locationTest("Aa A"));
        assertFalse(ValidName.locationTest("RuPaul"));
        assertFalse(ValidName.locationTest("@a"));
        assertFalse(ValidName.locationTest("Aa aa"));

    }

    @Test
    void usernameTest(){
        assertTrue(ValidName.usernameTest("Aaa"));
        assertTrue(ValidName.usernameTest("aaa"));
        assertTrue(ValidName.usernameTest("Åå_Åå"));
        assertTrue(ValidName.usernameTest("aAa"));
        assertTrue(ValidName.usernameTest("A1a"));
        assertTrue(ValidName.usernameTest("RuPaul"));
        assertTrue(ValidName.usernameTest("A"));
        assertTrue(ValidName.usernameTest("123"));

        assertFalse(ValidName.usernameTest("A a"));
        assertFalse(ValidName.usernameTest(" "));
        assertFalse(ValidName.usernameTest("Âa"));
        assertFalse(ValidName.usernameTest("Aa A"));
        assertFalse(ValidName.usernameTest("@a"));
        assertFalse(ValidName.usernameTest("Aa aa"));
    }

    @Test
    void carpartNameTest(){
        assertTrue(ValidName.carpartNameTest("GPS"));
        assertTrue(ValidName.carpartNameTest("GPS1"));
        assertTrue(ValidName.carpartNameTest("GPS1.0"));
        assertTrue(ValidName.carpartNameTest("GPS 1.0"));
        assertTrue(ValidName.carpartNameTest("GPS 1_0"));
        assertTrue(ValidName.carpartNameTest("Subwoofer"));
        assertTrue(ValidName.carpartNameTest("Subwøøfer ver. 1.0.3"));
        assertTrue(ValidName.carpartNameTest("Sub VERS 1.3_245"));
        assertTrue(ValidName.carpartNameTest("gps"));

        assertFalse(ValidName.carpartNameTest(" GPS"));
        assertFalse(ValidName.carpartNameTest(" "));
        assertFalse(ValidName.carpartNameTest("Âa"));
        assertFalse(ValidName.carpartNameTest("@a"));
    }
}