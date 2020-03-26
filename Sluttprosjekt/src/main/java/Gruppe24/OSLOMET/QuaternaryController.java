package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarBuild;
import Gruppe24.OSLOMET.Car.Carparts;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.io.IOException;

public class QuaternaryController{

    @FXML
    private CheckBox cbxGps;

    @FXML
    private CheckBox cbxSoundsystem;

    @FXML
    private CheckBox cbxSpoiler;

    @FXML
    private CheckBox cbxpolicesiren;


    @FXML
    private void switchToQuinary() throws IOException {
        if(cbxGps.isSelected()){
            Carparts gps = new Carparts("GPS", 2000);
            CarBuild.add(gps);
            System.out.println("Added gps");
        }

        if(cbxSoundsystem.isSelected()){
            Carparts soundsystem = new Carparts("Soundsystem", 10000);
            CarBuild.add(soundsystem);
            System.out.println("Added soundsystem");
        }

        if(cbxSpoiler.isSelected()){
            Carparts spoiler = new Carparts("Spoiler", 2000);
            CarBuild.add(spoiler);
            System.out.println("Added spoiler");
        }

        if(cbxpolicesiren.isSelected()){
            Carparts policesiren = new Carparts("Police siren", 9999);
            CarBuild.add(policesiren);
            System.out.println("Added a police siren");
        }

        App.setRoot("05-quinary");
    }
}