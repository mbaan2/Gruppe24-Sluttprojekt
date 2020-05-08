package Gruppe24.OSLOMET.ExceptionClasses;

import javafx.fxml.LoadException;

public class ScreenNotFoundException extends LoadException {
    public ScreenNotFoundException(String msg){
        super(msg);
    }
}
