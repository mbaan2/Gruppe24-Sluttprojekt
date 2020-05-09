package Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import java.util.List;

public class LoadingValuesThread extends Task<List<CheckBox>> {
    private List<Carparts> carCategory;
    private List<CheckBox> selectedCategoryButtons;

    public LoadingValuesThread(List<Carparts> carCategory, List<CheckBox> selectedCategoryButtons) {
        this.carCategory = carCategory;
        this.selectedCategoryButtons = selectedCategoryButtons;
    }

    @Override
    public List<CheckBox> call() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            Alert failed = new Alert(Alert.AlertType.ERROR);
            failed.setHeaderText("");
            failed.setTitle("Thread failed.");
            failed.setContentText("Thread interrupted, list not loaded!");
            Thread.currentThread().interrupt();
        }
        return LoadingValuesOnScreen.creatingList(selectedCategoryButtons, carCategory);
    }
}
