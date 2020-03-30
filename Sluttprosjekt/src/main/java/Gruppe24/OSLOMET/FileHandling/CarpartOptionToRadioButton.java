package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class CarpartOptionToRadioButton{
    public List<RadioButton> optionToButtonList(List<CarpartOption> carpartOptionList){
        List<RadioButton> btnList = new ArrayList<>();
        for (int i=0; i < carpartOptionList.size(); i++){
            RadioButton btn = new RadioButton(carpartOptionList.get(i).getRadioBtnTxt());
            btnList.add(btn);
        }
        return btnList;
    }
}
