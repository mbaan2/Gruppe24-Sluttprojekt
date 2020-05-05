package Gruppe24.OSLOMET.DataValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidNameTest{

    @Test
    void nameTest(){
        assertTrue(ValidName.nameTest("Aaa"));
        assertTrue(ValidName.nameTest("Aa Aa Aa"));
        assertTrue(ValidName.nameTest("Åå Åå"));
        assertTrue(ValidName.nameTest("Aa Aa Aa Aa Aa"));

        assertFalse(ValidName.nameTest(" "));
        assertFalse(ValidName.nameTest("A"));
        assertFalse(ValidName.nameTest("AAA"));
        assertFalse(ValidName.nameTest("aaa"));
        assertFalse(ValidName.nameTest("Âa"));
        assertFalse(ValidName.nameTest("A1a"));
        assertFalse(ValidName.nameTest("Aa A"));
        assertFalse(ValidName.nameTest("RuPaul"));
        assertFalse(ValidName.nameTest("@a"));
        assertFalse(ValidName.nameTest("Aa aa"));

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
}