package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.LoadingValuesOnScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPage implements Initializable {
    List<Car> carCategory = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxStrings();
    }

    @FXML
    private VBox vboxSelectedChoiceBox;

    @FXML
    private ChoiceBox<String> chbCategory;

    @FXML
    private TextField txfInputFieldName;

    @FXML
    private TextField txfInputFieldCost;

    ObservableList<String> choiceboxStrings = FXCollections.observableArrayList();
    private void loadChoiceBoxStrings(){
        choiceboxStrings.removeAll();
        String wheelsCHB = "Wheels";
        String colorCHB = "Color";
        String addOnesCHB = "Addons";
        choiceboxStrings.addAll(wheelsCHB, colorCHB, addOnesCHB);
        chbCategory.getItems().addAll(choiceboxStrings);
    }

    @FXML
    void btnLoadCategory(ActionEvent event) {
        carCategory.clear();
        LoadCategory();
    }

    public void LoadCategory(){
        if(chbCategory.getValue().equals("Wheels")){
            Path path = Paths.get("wheels.jobj");
            carCategory = FileOpenerJobj.openFile(path);
            createButtons();
        } else if(chbCategory.getValue().equals("Color")){
            Path path = Paths.get("color.jobj");
            carCategory = FileOpenerJobj.openFile(path);
            createButtons();
        } else if(chbCategory.getValue().equals("Addons")){
            Path path = Paths.get("AddOnes.jobj");
            carCategory = FileOpenerJobj.openFile(path);
            createButtons();
        }
    }

    List<CheckBox> selectedCategoryButtons = new ArrayList<>();

    public void createButtons(){
        selectedCategoryButtons.clear();
        selectedCategoryButtons = LoadingValuesOnScreen.creatingList(selectedCategoryButtons, carCategory);

        vboxSelectedChoiceBox.getChildren().clear();
        vboxSelectedChoiceBox = LoadingValuesOnScreen.returnVbox(selectedCategoryButtons, vboxSelectedChoiceBox);


    }


    @FXML
    void btnRemove(ActionEvent event) {
        //HAVE TO ADD THAT YOU CANNOT MAKE DUPLICATE VALUES!
        for(int i = 0; i < selectedCategoryButtons.size(); i++){
            if(selectedCategoryButtons.get(i).isSelected()){
                for(int j =0; j<carCategory.size(); j++){
                    if (selectedCategoryButtons.get(i).getText().equals(carCategory.get(j).getName())){
                        System.out.println(selectedCategoryButtons.get(i).getText() + " is removed");
                        carCategory.remove(j);
                    }
                }
            }
        }

        SaveChanges();
    }

    public void SaveChanges(){
        if(chbCategory.getValue().equals("Wheels")){
            Path filsti = Paths.get("wheels.jobj");
            try {
                FileSaverJobj.SaveCarCategory(filsti, carCategory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LoadCategory();
        } else if(chbCategory.getValue().equals("Color")){
            Path filsti = Paths.get("color.jobj");
            try {
                FileSaverJobj.SaveCarCategory(filsti, carCategory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LoadCategory();
        } else if(chbCategory.getValue().equals("Addons")){
            Path filsti = Paths.get("AddOnes.jobj");
            try {
                FileSaverJobj.SaveCarCategory(filsti, carCategory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LoadCategory();
        }

    }


    @FXML
    void btnAdd(ActionEvent event) {
        String name = txfInputFieldName.getText();
        String costString = txfInputFieldCost.getText();
        int cost = Integer.parseInt(costString);

        Car newCarPart = new Carparts(name, cost);
        carCategory.add(newCarPart);

        if (chbCategory.getValue().equals("Wheels")) {
            Path filsti = Paths.get("wheels.jobj");
            try {
                FileSaverJobj.SaveCarCategory(filsti, carCategory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LoadCategory();
        } else if (chbCategory.getValue().equals("Color")) {
            Path filsti = Paths.get("color.jobj");
            try {
                FileSaverJobj.SaveCarCategory(filsti, carCategory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LoadCategory();
        } else if (chbCategory.getValue().equals("Addons")) {
            Path filsti = Paths.get("AddOnes.jobj");
            try {
                FileSaverJobj.SaveCarCategory(filsti, carCategory);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LoadCategory();
        }

    }

    @FXML
    void btnBackToLoggInn(ActionEvent event) throws IOException {
        App.setRoot("login");

    }

}
