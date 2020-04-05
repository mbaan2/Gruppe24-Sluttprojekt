package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.FileTreatment.FileOpenerTxt;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadedCars implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            openTxtFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void openTxtFile() throws IOException{
        //FileOpenerTxt fot = new FileOpenerTxt();
        //fot.open();
    }
}
