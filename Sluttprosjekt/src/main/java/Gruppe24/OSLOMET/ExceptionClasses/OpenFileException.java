package Gruppe24.OSLOMET.ExceptionClasses;


import java.io.IOException;

public class OpenFileException extends IOException{
    public OpenFileException(String msg) {
        super(msg);
    }
}
