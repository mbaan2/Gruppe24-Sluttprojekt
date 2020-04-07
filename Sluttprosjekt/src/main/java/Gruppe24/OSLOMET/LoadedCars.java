package Gruppe24.OSLOMET;

//import Gruppe24.OSLOMET.FileTreatment.FileOpenerTxt;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

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

    @FXML
    private TableView<?> tableCars;

    @FXML
    void btnBackToCarBuild(ActionEvent event) throws IOException{
        App.setRoot("01-primary");
    }

    @FXML
    void btnLogOut(ActionEvent event) throws IOException{
        App.setRoot("login");
    }

}
