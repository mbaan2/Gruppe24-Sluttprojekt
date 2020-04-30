package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.NewCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SuperUserCarView_Controller implements Initializable {

    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private Label filterLbl;

    TableViewCreation createView = new TableViewCreation();
    ObservableList<NewCar> carList = createView.carList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addChoiceBoxItems();
        createView.initializeTv(tableView);
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        App.setRoot("superUser");
    }

    @FXML
    void filterCars() {
        ObservableList<NewCar> filteredList = FXCollections.observableArrayList();

        String filteredText = filterText.getText();
        String filterType = filterBox.getValue();

        filteredList.clear();
        filterLbl.setText("");

        if(filteredText.equals("")) {
            filterLbl.setText("You didnt enter anything..");
        }
        else if (filterType.equals("Search Filters")) {
            filterLbl.setText("You didnt choose a filter.");
        } else{
            filteredList = Filter.filtering(filteredText, filterType, filteredList, carList);
            filterLbl.setText(Filter.filteringFeedback(filterType, filteredList));
            tableView.setItems(filteredList);
        }
    }

    private void addChoiceBoxItems() {
        ObservableList<String> choiceBoxList = Filter.choiceBoxList();
        filterBox.getItems().addAll(choiceBoxList);
        filterBox.setValue(choiceBoxList.get(0));
    }

    @FXML
    private void resetFilter() {
        filterText.setText("");
        filterLbl.setText("Tableview reset.");
        tableView.setItems(carList);
        filterBox.setValue("Search Filters");
    }


}
