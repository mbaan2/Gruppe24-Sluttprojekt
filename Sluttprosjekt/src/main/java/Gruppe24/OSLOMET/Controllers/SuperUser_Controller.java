package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories.EditCarpart;
import Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories.LoadCategory;
import Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories.RemoveCarpart;
import Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories.SaveCarparts;
import Gruppe24.OSLOMET.SuperUserClasses.EditCarCategories.LoadingValuesThread;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
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
        //Disabling the buttons on load
        setDisableBtn();
        //Disabling the buttons when changing the value of the choicebox - so that you cant add/edit/remove carparts to/from the wrong carcategory.
        chbCategory.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> setDisableBtn());
        loadChoiceBoxStrings();
        Platform.runLater(() -> {
            Stage stage = (Stage) superUserPane.getScene().getWindow();
            stage.setWidth(600);
            stage.setHeight(520);
        });
    }

    private LoadingValuesThread task;

    @FXML
    private AnchorPane superUserPane;

    @FXML
    private VBox vboxSelectedChoiceBox;

    @FXML
    private ChoiceBox<String> chbCategory;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCost;

    @FXML
    private Label lblError;

    @FXML
    private Button addBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private Button editBtn;

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

    private void setDisableBtn() {
        addBtn.setDisable(true);
        removeBtn.setDisable(true);
        editBtn.setDisable(true);
    }

    @FXML
    void btnLoadCategory(ActionEvent event) {
        lblError.setText("Loading category...");
        carCategory.clear();
        loadCategory();
    }

    public void loadCategory(){
        carCategory = LoadCategory.loadCategory(chbCategory.getValue());
        createButtons();
    }

    public void createButtons(){
        task = new LoadingValuesThread(carCategory, selectedCategoryButtons);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        Thread th = new Thread(task);
        th.setDaemon(true);
        setDisableBtn();
        th.start();
    }

    private void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        lblError.setText(e.getMessage());
    }

    private void threadDone(WorkerStateEvent event) {
        vboxSelectedChoiceBox.getChildren().clear();
        selectedCategoryButtons.clear();
        selectedCategoryButtons = task.call();
        LoadingValuesOnScreen.returnVbox(selectedCategoryButtons, vboxSelectedChoiceBox);
        if(lblError.getText().equals("Loading category...")) {
            lblError.setText("Category successfully loaded!");
        }
        if(lblError.getText().equals("Editing carpart...")) {
            lblError.setText("Carpart has been edited!");
        }
        if(lblError.getText().equals("Removing carpart(s)...")) {
            lblError.setText("Carpart(s) removed!");
        }
        if(lblError.getText().equals("Adding carpart...")) {
            lblError.setText("Carpart has been added!");
        }
        //Enabling the buttons again when the thread is done.
        addBtn.setDisable(false);
        removeBtn.setDisable(false);
        editBtn.setDisable(false);
    }

    @FXML
    void btnEdit(ActionEvent event) {
        lblError.setText("Editing carpart...");
        String name = txtName.getText();
        String costString = txtCost.getText();
        int cost = Integer.parseInt(costString);
        int amountSelected = 0;


        for(CheckBox carpart : selectedCategoryButtons) {
            if (carpart.isSelected()) {
                amountSelected++;
            }
        }
        if(amountSelected > 1) {
            lblError.setText("You can only choose one carpart to edit!");
        } else if(amountSelected < 1) {
            lblError.setText("Nothing is selected!");
        } else {
            EditCarpart.editedList(carCategory, selectedCategoryButtons, cost, name);
            SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
            loadCategory();
        }
    }

    @FXML
    void btnRemove(ActionEvent event) {
        lblError.setText("Removing carpart(s)...");
        int removedItems = 0;

        for(CheckBox carpart : selectedCategoryButtons){
            if(carpart.isSelected()){
                removedItems++;
            }
        }
        if(removedItems < 1){
            lblError.setText("Nothing is selected");
        } else{
            RemoveCarpart.remove(carCategory, selectedCategoryButtons);
            SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
            loadCategory();
        }
    }

    public boolean containsName(List<Carparts> list, String name) {
        return list.stream().noneMatch(carpart -> carpart.getName().equals(name));
    }

    @FXML
    void btnAdd(ActionEvent event) {
        lblError.setText("Adding carpart...");
        String name = txtName.getText();
        String costString = txtCost.getText();
        int cost = Integer.parseInt(costString);

        List<Carparts> specificCategory = LoadCategory.loadCategory(chbCategory.getValue());

        Carparts newCarPart = new Carparts(name, cost);
        if(containsName(specificCategory, name)) {
            carCategory.add(newCarPart);
            SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
            loadCategory();
        } else {
            lblError.setText("A carpart with that name already exists, try editing it instead!");
        }
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