package Gruppe24.OSLOMET.ExceptionClasses;

import java.io.IOException;

public class SaveFileException extends IOException {
    public SaveFileException(String msg){
        super(msg);
    }
}
