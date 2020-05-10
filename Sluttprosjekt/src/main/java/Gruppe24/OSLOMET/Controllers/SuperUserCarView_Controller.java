package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.Filter;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.TableViewCreation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SuperUserCarView_Controller implements Initializable {
    TableViewCreation createView = new TableViewCreation();
    ObservableList<NewCar> carList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterLbl.setText("Loading cars...");
        createView.initializeTv(tableView, filterLbl, true);
        filterBtn.setDisable(true);
        backBtn.setDisable(true);
        resetFilterBtn.setDisable(true);
        tableView.setVisible(false);
        addChoiceBoxItems();

        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) superUserViewPane.getScene().getWindow();
                stage.setWidth(1210);
                stage.setHeight(500);
            } catch (ExceptionInInitializerError e) {
                filterLbl.setText("Error in setting the proper width and height, resize the window manually.");
            }

            filterBtn.setDisable(false);
            backBtn.setDisable(false);
            resetFilterBtn.setDisable(false);
            tableView.setVisible(true);
            if(filterLbl.getText().equals("Loading cars...")) {
                filterLbl.setText("Cars loaded!");
            } else if(filterLbl.getText().equals("Could not load user base.")) {
                filterLbl.setText("Cars loaded however the userbase isnt loaded. This affects the rest of the program so full functionality will be restored when you restore users.");
                tableView.setDisable(true);
                filterBtn.setVisible(false);
                filterBox.setVisible(false);
                filterText.setVisible(false);
                resetFilterBtn.setVisible(false);
            } else if(filterLbl.getText().equals("Could not load add-ons.")){
                filterLbl.setText("Cars loaded however addons all show up as deprecated, full functionality will be restored when you restore the addons.jobj file.");
                tableView.setDisable(true);
                filterBtn.setVisible(false);
                filterBox.setVisible(false);
                filterText.setVisible(false);
                resetFilterBtn.setVisible(false);
            } else if(filterLbl.getText().equals("Could not load fuel.")) {
                filterLbl.setText("Cars loaded however fuel options arent loaded, full functionality will be restored when you restore the fuel.jobj file.");
                tableView.setDisable(true);
                filterBtn.setVisible(false);
                filterBox.setVisible(false);
                filterText.setVisible(false);
                resetFilterBtn.setVisible(false);
            } else if(filterLbl.getText().equals("Could not load wheels.")) {
                filterLbl.setText("Cars loaded however wheel options arent loaded, full functionality will be restored when you restore the wheels.jobj file.");
                tableView.setDisable(true);
                filterBtn.setVisible(false);
                filterBox.setVisible(false);
                filterText.setVisible(false);
                resetFilterBtn.setVisible(false);
            } else if(filterLbl.getText().equals("Could not load colors.")) {
                filterLbl.setText("Cars loaded however colors arent loaded, full functionality will be restored when you restore the color.jobj file.");
                tableView.setDisable(true);
                filterBtn.setVisible(false);
                filterBox.setVisible(false);
                filterText.setVisible(false);
                resetFilterBtn.setVisible(false);
            } else if(filterLbl.getText().equals("Could not load the carlist.")) {
                filterLbl.setText("Cars arent loaded, there is an issue with the cars.jobj file. Restore it to restore functionality to the page.");
                tableView.setVisible(false);
                filterBtn.setVisible(false);
                filterBox.setVisible(false);
                filterText.setVisible(false);
                resetFilterBtn.setVisible(false);
            }
            executor.submit(setTableview);
            tableView.refresh();
        });
    }

    @FXML
    private AnchorPane superUserViewPane;

    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private TextField filterText;

    @FXML
    private Label filterLbl;

    @FXML
    private Button filterBtn, backBtn, resetFilterBtn;

    @FXML
    void btnBack(ActionEvent event) {
        try {
            App.setRoot("superUser");
        } catch (IOException e){
            filterLbl.setText(e.getMessage());
        } catch (IllegalStateException e){
            filterLbl.setText("There is an error in loading the next screen, please contact your developer.");
        }
        executor.shutdownNow();
    }

    void openCars() {
        carList.clear();

        ArrayList<NewCar> list2 = new ArrayList<>();
        try{
            list2 = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
        } catch (IOException e){
            filterLbl.setText("Error in opening the list of cars, tableview will be empty.");
        }
        carList.addAll(list2);
    }

    @FXML
    void filterCars() throws IOException {
        ObservableList<NewCar> filteredList = FXCollections.observableArrayList();
        openCars();

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
            tableView.getItems().clear();
            filteredList = Filter.filtering(filteredText, filterType, filteredList, carList);
            filterLbl.setText(Filter.filteringFeedback(filterType, filteredList));
            tableView.setItems(filteredList);
            tableView.refresh();
        }
    }

    // Thread solution based on a comment from https://stackoverflow.com/questions/36593572/javafx-tableview-high-frequent-updates
    public final Runnable setTableview = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            tableView.getItems();
        }
    };

    private final ExecutorService executor = Executors.newSingleThreadScheduledExecutor(runnable -> {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            filterLbl.setText("Error in fetching the table!");
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    private void addChoiceBoxItems() {
        ObservableList<String> choiceBoxList = Filter.choiceBoxList();
        filterBox.getItems().addAll(choiceBoxList);
        filterBox.setValue(choiceBoxList.get(0));
    }

    @FXML
    private void resetFilter() {
        filterText.setText("");
        filterLbl.setText("Tableview reset.");
        tableView.getItems().clear();
        openCars();
        tableView.setItems(carList);
        filterBox.setValue("Search Filters");
        tableView.refresh();
    }
}
