package Gruppe24.OSLOMET.Car;

import javafx.scene.control.RadioButton;

import java.io.Serializable;
import java.util.ArrayList;

public class CarpartOption extends Carparts implements Serializable{
    private String radioBtnTxt;

    public CarpartOption(String name, int cost, String radioBtnTxt){
        super(name, cost);
        this.radioBtnTxt = radioBtnTxt;
    }

    public String getRadioBtnTxt(){
        return radioBtnTxt;
    }

    public void setRadioBtnTxt(String radioBtnTxt){
        this.radioBtnTxt = radioBtnTxt;
    }

    public RadioButton createRadioBtn(Car carbuild, ArrayList<RadioButton> buttonsArray){
        RadioButton btn = new RadioButton(getRadioBtnTxt());
        buttonsArray.add(btn);
        return btn;
    }
}
