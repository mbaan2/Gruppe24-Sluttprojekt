package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverTxt;
import Gruppe24.OSLOMET.FileTreatment.FormatCar;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.Filter;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.TableViewCreation;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class UserCarView_Controller implements Initializable {

    @FXML
    private AnchorPane userViewPane;

    @FXML
    private TableView<NewCar> tableView;

    @FXML
    private Label tvLabel;

    @FXML
    private TextField filterText;

    @FXML
    private ChoiceBox<String> filterBox;

    @FXML
    private Button saveTable, backBtn, resetFilterBtn, filterBtn;

    @FXML
    private Tooltip superuserInfo;

    ObservableList<NewCar> usersCarList = FXCollections.observableArrayList();

    @FXML
    void goBack() {
        try {
            App.setRoot("WelcomeScreen");
        } catch (IOException e) {
            tvLabel.setText(e.getMessage());
        } catch (IllegalStateException e) {
            tvLabel.setText("There is an error in loading the next screen, please contact your developer.");
        }
        executor.shutdownNow();
    }

    @FXML
    void filterCars() {
        ObservableList<NewCar> filteredList = FXCollections.observableArrayList();

        String filteredText = filterText.getText();
        String filterType = filterBox.getValue();

        filteredList.clear();
        tvLabel.setText("");

        if (filteredText.equals("")) {
            tvLabel.setText("You didn't enter anything.");
        } else if (filterType.equals("Search Filters")) {
            tvLabel.setText("You didn't choose a filter.");
        } else {
            filteredList = Filter.filtering(filteredText, filterType, filteredList, usersCarList);
            tvLabel.setText(Filter.filteringFeedback(filterType, filteredList));
            tableView.setItems(filteredList);
            tableView.refresh();
        }
    }

    @FXML
    private void resetFilter() {
        filterText.setText("");
        tvLabel.setText("Tableview reset.");
        usersCarList.clear();
        createView.initializeTv(tableView, tvLabel, false);
        usersCarList.setAll(tableView.getItems().filtered(newcar -> newcar.getUser().equals(App.car.getUser())));
        tableView.setItems(usersCarList);
        filterBox.setValue("Search Filters");
    }

    private void addChoiceBoxItems() {
        ObservableList<String> choiceBoxList = Filter.choiceBoxList();
        filterBox.getItems().addAll(choiceBoxList);
        filterBox.getItems().remove(choiceBoxList.get(1));
        filterBox.setValue(choiceBoxList.get(0));
    }

    @FXML
    void saveYourTable(ActionEvent event) throws IOException {
        ObservableList<NewCar> outputList = tableView.getItems();
        String username = App.car.getUser();
        Path path = Paths.get("./Car Txt Files/" + username + "sCars.txt");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save your cars to a txt file!");
        alert.setHeaderText("");
        alert.setContentText("Do you want to overwrite your cars or append them to your list?" + "\n\nEither option creates a new file in case you dont have one. Your new file will be named " + username + "sCars.txt");
        ButtonType append = new ButtonType("Append");
        ButtonType overwrite = new ButtonType("Overwrite");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(append, overwrite, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == overwrite) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.overwrite(str, selectedFile, tvLabel, username);
            } else if (result.get() == append) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.append(str, selectedFile, tvLabel, username);
            } else {
                tvLabel.setText("Process cancelled. File wasnt saved.");
            }
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
            tvLabel.setText("Error in fetching the table!");
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    TableViewCreation createView = new TableViewCreation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvLabel.setText("Loading cars...");
        addChoiceBoxItems();
        superuserInfo.setShowDelay(Duration.millis(50));
        superuserInfo.setHideDelay(Duration.millis(1000));
        backBtn.setDisable(true);
        filterBtn.setDisable(true);
        resetFilterBtn.setDisable(true);
        saveTable.setDisable(true);
        tableView.setVisible(false);
        createView.initializeTv(tableView, tvLabel, false);


        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) userViewPane.getScene().getWindow();
                stage.setWidth(1081);
                stage.setHeight(500);
            } catch (ExceptionInInitializerError e) {
                tvLabel.setText("Error in setting the proper width and height, resize the window manually.");
            }

            filterBtn.setDisable(false);
            backBtn.setDisable(false);
            resetFilterBtn.setDisable(false);
            saveTable.setDisable(false);
            tableView.setVisible(true);
            tvLabel.setText("Cars loaded!");
            executor.submit(setTableview);
            tableView.refresh();
            tableView.setEditable(false);
            usersCarList.removeAll();
            usersCarList.setAll(tableView.getItems().filtered(newcar -> newcar.getUser().equals(App.car.getUser())));
            tableView.setItems(usersCarList);
            tableView.getColumns().remove(tableView.getColumns().size()-1);
            /*tableView.getColumns().get(
                    tableView.getColumns().size()-2).getColumns().get(
                            tableView.getColumns().get(tableView.getColumns().size()-1).getColumns().size()
            ).forEach(Button button -> );

             */
        });
    }
}
