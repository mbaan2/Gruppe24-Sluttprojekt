package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.DataValidation.ValidPrice;
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
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            stage.setWidth(620);
            stage.setHeight(550);
        });
        superuserInfo.setShowDelay(Duration.millis(100.0));
        superuserInfo.setHideDelay(Duration.millis(200.0));
    }

    private LoadingValuesThread task;

    @FXML
    private AnchorPane superUserPane;

    @FXML
    private VBox vboxSelectedChoiceBox;

    @FXML
    private ChoiceBox<String> chbCategory;

    @FXML
    private TextField txtName, txtCost;

    @FXML
    private Label superUserLbl, lblNameError, lblCostError;

    @FXML
    private Button addBtn, removeBtn, editBtn, backBtn, btnLoadCars, loadBtn, btnRestoreCarparts, btnLoadUsers, btnRestoreUsers, btnRestoreCars, btnRestoreSecretQ;

    @FXML
    private Tooltip superuserInfo;

    private void loadChoiceBoxStrings(){
        String chooseValue = "Choose a category";
        String fuelCHB = "Fuel type";
        String wheelsCHB = "Wheels";
        String colorCHB = "Color";
        String addOnesCHB = "Addons";

        ObservableList<String> choiceboxStrings = FXCollections.observableArrayList();
        choiceboxStrings.removeAll();
        choiceboxStrings.addAll(chooseValue, fuelCHB, wheelsCHB, colorCHB, addOnesCHB);

        chbCategory.getItems().addAll(choiceboxStrings);
        chbCategory.setValue(chooseValue);
    }

    private void setDisableBtn() {
        addBtn.setDisable(true);
        removeBtn.setDisable(true);
        editBtn.setDisable(true);
    }

    @FXML
    void btnLoadCategory(ActionEvent event) {
        lblCostError.setText("");
        lblNameError.setText("");
        if(chbCategory.getValue().equals("Choose a category")) {
            superUserLbl.setText("Choose a category to load!");
        } else {
            superUserLbl.setText("Loading category...");
            carCategory.clear();
            loadCategory();
        }
    }

    public void loadCategory(){
        carCategory = LoadCategory.loadCategory(chbCategory.getValue());
        createButtonsThread();
    }

    public void createButtonsThread(){
        task = new LoadingValuesThread(carCategory, selectedCategoryButtons);
        task.setOnSucceeded(this::threadDone);
        task.setOnFailed(this::threadFailed);
        Thread th = new Thread(task);
        th.setDaemon(true);
        backBtn.setDisable(true);
        btnLoadCars.setDisable(true);
        btnRestoreCarparts.setDisable(true);
        btnLoadUsers.setDisable(true);
        btnRestoreUsers.setDisable(true);
        btnRestoreCars.setDisable(true);
        loadBtn.setDisable(true);
        btnRestoreSecretQ.setDisable(true);
        setDisableBtn();
        th.start();
    }

    private void threadFailed(WorkerStateEvent event) {
        var e = event.getSource().getException();
        superUserLbl.setText(e.getMessage());
    }

    private void threadDone(WorkerStateEvent event) {
        vboxSelectedChoiceBox.getChildren().clear();
        selectedCategoryButtons.clear();
        selectedCategoryButtons = task.call();
        LoadingValuesOnScreen.returnVbox(selectedCategoryButtons, vboxSelectedChoiceBox);
        //Depending on which button is pressed the label will change value when the thread is done.
        if(superUserLbl.getText().equals("Loading category...")) {
            superUserLbl.setText("Category successfully loaded!");
        }
        if(superUserLbl.getText().equals("Editing carpart...")) {
            superUserLbl.setText("Carpart has been edited!");
        }
        if(superUserLbl.getText().equals("Removing carpart(s)...")) {
            superUserLbl.setText("Carpart(s) removed!");
        }
        if(superUserLbl.getText().equals("Adding carpart...")) {
            superUserLbl.setText("Carpart has been added!");
        }
        //Enabling the buttons again when the thread is done.
        addBtn.setDisable(false);
        removeBtn.setDisable(false);
        editBtn.setDisable(false);
        backBtn.setDisable(false);
        btnLoadCars.setDisable(false);
        btnRestoreCarparts.setDisable(false);
        btnLoadUsers.setDisable(false);
        btnRestoreUsers.setDisable(false);
        btnRestoreCars.setDisable(false);
        loadBtn.setDisable(false);
        btnRestoreSecretQ.setDisable(false);
    }

    @FXML
    void btnEdit(ActionEvent event) {
        lblCostError.setText("");
        lblNameError.setText("");
        superUserLbl.setText("Editing carpart...");
        String name = "";
        if (ValidName.carpartNameTest(txtName.getText())){
            name = txtName.getText();
        } else {
            lblNameError.setText("Enter a valid carpart name!");
        }
        String costString = txtCost.getText();
        int cost = -1;
        try {
            if (ValidPrice.priceTest(Integer.parseInt(costString))){
                cost = Integer.parseInt(costString);
            } else {
                lblCostError.setText("Enter a price above 0!");
                superUserLbl.setText("");
            }
        } catch (NumberFormatException e) {
            lblCostError.setText("Enter a number!");
            superUserLbl.setText("");
        }
        int amountSelected = 0;

        if(!name.isEmpty() && cost != -1) {
            for (CheckBox carpart : selectedCategoryButtons) {
                if (carpart.isSelected()) {
                    amountSelected++;
                }
            }
            if (amountSelected > 1) {
                superUserLbl.setText("You can only choose one carpart to edit!");
            } else if (amountSelected < 1) {
                superUserLbl.setText("Nothing is selected!");
            } else {
                EditCarpart.editedList(carCategory, selectedCategoryButtons, cost, name);
                SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
                loadCategory();
            }
        } else if(name.isEmpty()) {
            lblNameError.setText("Enter a name!");
            superUserLbl.setText("");
        }
    }

    @FXML
    void btnRemove(ActionEvent event) {
        superUserLbl.setText("Removing carpart(s)...");
        lblCostError.setText("");
        lblNameError.setText("");
        int removedItems = 0;

        for(CheckBox carpart : selectedCategoryButtons){
            if(carpart.isSelected()){
                removedItems++;
            }
        }
        if(removedItems < 1){
            superUserLbl.setText("Nothing is selected");
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
        superUserLbl.setText("Adding carpart...");
        String name = "";
        if (ValidName.carpartNameTest(txtName.getText())){
            name = txtName.getText();
        } else {
            lblNameError.setText("Enter a valid carpart name!");
        }
        String costString = txtCost.getText();
        int cost = -1;
        try {
            if (ValidPrice.priceTest(Integer.parseInt(costString))){
                cost = Integer.parseInt(costString);
            } else {
                lblCostError.setText("Enter a price above 0!");
                superUserLbl.setText("");
            }
        } catch (NumberFormatException e) {
            lblCostError.setText("Enter a number!");
            superUserLbl.setText("");
        }

        if(!name.isEmpty() && cost != -1) {
            List<Carparts> specificCategory = LoadCategory.loadCategory(chbCategory.getValue());
            Carparts newCarPart = new Carparts(name, cost);

            if (containsName(specificCategory, name)) {
                carCategory.add(newCarPart);
                SaveCarparts.saveChanges(carCategory, chbCategory.getValue());
                loadCategory();
                txtName.setText("");
                txtCost.setText("");
            } else {
                superUserLbl.setText("A carpart with that name already exists, try editing it instead!");
            }
        } else if(name.isEmpty()) {
            lblNameError.setText("Enter a valid name!");
            superUserLbl.setText("");
        }
    }

    @FXML
    void btnBackToLogin() {
        try {
            App.setRoot("login");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void btnRestoreCarparts(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restore Users");
        alert.setHeaderText("");
        alert.setContentText("Do you want to restore the carparts?");
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yesBtn) {
            SetFuel_Controller restoreFuel = new SetFuel_Controller();
            restoreFuel.createFile();
            SetWheels_Controller restoreWheels = new SetWheels_Controller();
            restoreWheels.createFile();
            SetColor_Controller restoreColor = new SetColor_Controller();
            restoreColor.createFile();
            SetAddons_Controller restoreAddon = new SetAddons_Controller();
            restoreAddon.createFile();
            superUserLbl.setText("Files are restored!");
        } else {
            superUserLbl.setText("Process cancelled, carparts were not restored.");
        }
    }

    @FXML
    void btnRestoreUsers(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restore Users");
        alert.setHeaderText("");
        alert.setContentText("Do you want to restore the users?");
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yesBtn) {
            SignUp_Controller restoreUser = new SignUp_Controller();
            restoreUser.createFile();
            superUserLbl.setText("Users are restored!");
        } else {
            superUserLbl.setText("Process cancelled, users were not restored.");
        }
    }

    @FXML
    void btnRestoreCars(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restore Users");
        alert.setHeaderText("");
        alert.setContentText("Do you want to restore the cars?");
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yesBtn) {
            Summary_Controller restoreCars = new Summary_Controller();
            restoreCars.createFile();
            superUserLbl.setText("Cars are restored!");
        } else {
            superUserLbl.setText("Process cancelled, cars were not restored.");
        }
    }

    @FXML
    void btnRestoreSecretQ(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Restore Secret Questions");
        alert.setHeaderText("");
        alert.setContentText("Do you want to restore the secret questions for the userlist?");
        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == yesBtn) {
            Userlist_Controller restoreSecretQ = new Userlist_Controller();
            restoreSecretQ.createFile();
            superUserLbl.setText("Secret questions are restored!");
        } else {
            superUserLbl.setText("Process cancelled, secret questions were not restored.");
        }
    }

    @FXML
    void switchToCarView() {
        try {
            App.setRoot("SuperUserCarView");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }

    @FXML
    void switchToUserView() {
        try {
            App.setRoot("Userlist");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }
}
