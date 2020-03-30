package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Carparts;

import java.io.Serializable;
import java.util.ArrayList;

public class OptionBtn extends Carparts implements Serializable{
    int btnType;

    public OptionBtn(int btnType, String carpartName, int carpartPrice){
        super(carpartName, carpartPrice);
        this.btnType = btnType;
    }

    public int getBtnType(){
        return btnType;
    }

    public void setBtnType(int btnType){
        this.btnType = btnType;
    }
}
