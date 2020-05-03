package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.LoadCategory;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.RemoveCarpart;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.SaveCarparts;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SuperUser_Controller implements Initializable {
    List<Carparts> carCategory = new ArrayList<>();
    List<CheckBox> selectedCategoryButtons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbCategory.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> addBtn.setDisable(true));
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

    @FXML
    private Button addBtn;

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
        addBtn.setDisable(false);
    }

    public void loadCategory(){
        carCategory = LoadCategory.loadCategory(chbCategory.getValue());
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

    public boolean containsName(List<Carparts> list, String name) {
        return list.stream().noneMatch(carpart -> carpart.getName().equals(name));
    }


    @FXML
    void btnAdd(ActionEvent event) {
        lblError.setText("");
        String name = txfInputFieldName.getText();
        String costString = txfInputFieldCost.getText();
        int cost = Integer.parseInt(costString);

        List<Carparts> specificCategory = LoadCategory.loadCategory(chbCategory.getValue());

        Carparts newCarPart = new Carparts(name, cost);
        if(containsName(specificCategory, name)) {
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