package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import javafx.scene.control.CheckBox;
import java.util.ArrayList;
import java.util.List;

public class RemoveCarpart {
    static List<Integer> list = new ArrayList<>();

    public static void remove(List<Carparts> carCategory, List<CheckBox> selectedCategoryButtons){
        list.clear();
        for(int i = 0; i < selectedCategoryButtons.size(); i++){
            if(selectedCategoryButtons.get(i).isSelected()){
                addElementToBeRemoved(i);
                }
            }
        removeElements(carCategory);
    }

    public static void addElementToBeRemoved(int index){
        list.add(index);
    }

    public static List<Carparts> removeElements(List<Carparts> carCategory){
        for(int i = (list.size() -1 ); i >= 0; i--){
            carCategory.remove((int) list.get(i));
        }
        return carCategory;
    }


}
