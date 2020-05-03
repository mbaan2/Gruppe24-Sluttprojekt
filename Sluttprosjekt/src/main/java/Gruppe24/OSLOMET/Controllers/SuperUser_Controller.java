package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.AdaptationsCarCategories.RemoveCarpart;
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
    ObservableList<String> choiceboxStrings = FXCollections.observableArrayList();
    List<CheckBox> selectedCategoryButtons = new ArrayList<>();

    final String fuelCHB = "Fuel type";
    final String wheelsCHB = "Wheels";
    final String colorCHB = "Color";
    final String addOnesCHB = "Addons";

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
        choiceboxStrings.removeAll();
        choiceboxStrings.addAll(fuelCHB, wheelsCHB, colorCHB, addOnesCHB);
        chbCategory.getItems().addAll(choiceboxStrings);
    }

    @FXML
    void btnLoadCategory(ActionEvent event) {
        carCategory.clear();
        LoadCategory();
    }

    public void LoadCategory(){

        switch (chbCategory.getValue()) {
            case fuelCHB:{
                Path path = Paths.get(StandardPaths.fuelPath);
                carCategory = FileOpenerJobj.openFile(path);
                createButtons();
                break;
            }
            case wheelsCHB: {
                Path path = Paths.get(StandardPaths.wheelPath);
                carCategory = FileOpenerJobj.openFile(path);
                createButtons();
                break;
            }
            case colorCHB: {
                Path path = Paths.get(StandardPaths.colorPath);
                carCategory = FileOpenerJobj.openFile(path);
                createButtons();
                break;
            }
            case addOnesCHB: {
                Path path = Paths.get(StandardPaths.addonPath);
                carCategory = FileOpenerJobj.openFile(path);
                createButtons();
                break;
            }
        }
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
        lblError.setText(removedItems);
        RemoveCarpart.remove(carCategory, selectedCategoryButtons);
        saveChanges();
    }

    public void saveChanges(){
        switch (chbCategory.getValue()) {
            case fuelCHB: {
                Path filsti = Paths.get(StandardPaths.fuelPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LoadCategory();
                break;
            }
            case wheelsCHB: {
                Path filsti = Paths.get(StandardPaths.wheelPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LoadCategory();
                break;
            }
            case colorCHB: {
                Path filsti = Paths.get(StandardPaths.colorPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LoadCategory();
                break;
            }
            case addOnesCHB: {
                Path filsti = Paths.get(StandardPaths.addonPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LoadCategory();
                break;
            }
        }

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

        switch (chbCategory.getValue()) {
            case fuelCHB: {
                Path filsti = Paths.get(StandardPaths.fuelPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                LoadCategory();
                break;
            }
            case wheelsCHB: {
                Path filsti = Paths.get(StandardPaths.wheelPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                LoadCategory();
                break;
            }
            case colorCHB: {
                Path filsti = Paths.get(StandardPaths.colorPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                LoadCategory();
                break;
            }
            case addOnesCHB: {
                Path filsti = Paths.get(StandardPaths.addonPath);
                try {
                    FileSaverJobj.SaveCarCategory(filsti, carCategory);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                LoadCategory();
                break;
            }
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
