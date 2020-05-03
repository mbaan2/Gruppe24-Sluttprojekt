package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import javafx.scene.control.CheckBox;
import java.util.List;

public class EditCarpart {
    public static List<Carparts> editedList (List<Carparts> carpartsList, List<CheckBox> checkBoxList, int cost, String name) {
        for(int i = 0; i < checkBoxList.size(); i++) {
            if(checkBoxList.get(i).isSelected()) {
                carpartsList.get(i).setName(name);
                carpartsList.get(i).setCost(cost);
            }
        }
        return carpartsList;
    }
}
