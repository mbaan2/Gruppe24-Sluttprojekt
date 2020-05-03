package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.LoadCategory;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.RemoveCarpart;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.SaveCarparts;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SuperUser_Controller implements Initializable {
    List<Carparts> carCategory = new ArrayList<>();
    List<CheckBox> selectedCategoryButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxStrings();
        Platform.runLater(() -> {
            Stage stage = (Stage) superUserPane.getScene().getWindow();
            stage.setWidth(600);
        });
    }

    @FXML
    private AnchorPane superUserPane;

    @FXML
    private VBox vboxSelectedChoiceBox;

    @FXML
    private ChoiceBox<String> chbCategory;

    @FXML
    private TextField txfInputFieldName;

    @FXML
    private TextField txfInputFieldCost;

    @FXML
    private Label lblError;

    private void loadChoiceBoxStrings(){
        String fuelCHB = "Fuel type";
        String wheelsCHB = "Wheels";
        String colorCHB = "Color";
        String addOnesCHB = "Addons";

        ObservableList<String> choiceboxStrings = FXCollections.observableArrayList();
        choiceboxStrings.removeAll();
        choiceboxStrings.addAll(fuelCHB, wheelsCHB, colorCHB, addOnesCHB);

        chbCategory.getItems().addAll(choiceboxStrings);
    }

    @FXML
    void btnLoadCategory(ActionEvent event) {
        carCategory.clear();
        loadCategory();
    }

    public void loadCategory(){
        LoadCategory.loadCategory(chbCategory.getValue());
        createButtons();
    }

    public void createButtons(){
        selectedCategoryButtons.clear();
        LoadingValuesOnScreen.creatingList(selectedCategoryButtons, carCategory);

        vboxSelectedChoiceBox.getChildren().clear();
        LoadingValuesOnScreen.returnVbox(selectedCategoryButtons, vboxSelectedChoiceBox);
    }


    @FXML
    void btnRemove(ActionEvent event) {
        String removedItems = "";

        for(CheckBox carpart : selectedCategoryButtons){
            if(carpart.isSelected()){
                removedItems += (carpart.getText() + " has been removed." + "\n");
            }
        }
        if(removedItems.equals("")){
            lblError.setText("Nothing is selected");
        } else{
            lblError.setText(removedItems);
        }
        RemoveCarpart.remove(carCategory, selectedCategoryButtons);
        SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
        loadCategory();
    }

    public List<Carparts> allCarParts() {
        List<Carparts> fuelList = FileOpenerJobj.openFile(Paths.get(StandardPaths.fuelPath));
        List<Carparts> wheelsList = FileOpenerJobj.openFile(Paths.get(StandardPaths.wheelPath));
        List<Carparts> colorList = FileOpenerJobj.openFile(Paths.get(StandardPaths.colorPath));
        List<Carparts> addonsList = FileOpenerJobj.openFile(Paths.get(StandardPaths.addonPath));

        List<Carparts> allCarparts = new ArrayList<>();
        allCarparts.addAll(fuelList);
        allCarparts.addAll(wheelsList);
        allCarparts.addAll(colorList);
        allCarparts.addAll(addonsList);

        return allCarparts;
    }

    public boolean containsName(List<Carparts> list, String name) {
        return list.stream().noneMatch(carpart -> carpart.getName().equals(name));
    }


    @FXML
    void btnAdd(ActionEvent event) {
        //So here we go through all the categories but we only need to go through the selected one.
        lblError.setText("");
        String name = txfInputFieldName.getText();
        String costString = txfInputFieldCost.getText();
        int cost = Integer.parseInt(costString);

        List<Carparts> allCarparts = allCarParts();

        Carparts newCarPart = new Carparts(name, cost);
        if(containsName(allCarparts, name)) {
            carCategory.add(newCarPart);
        } else {
            lblError.setText("A carpart with that name already exists, try editing it instead!");
        }

        SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
        loadCategory();
    }

    @FXML
    void btnBackToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    void switchToCarView() throws IOException {
        App.setRoot("superUserView");
    }

}
