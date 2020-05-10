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
    ObservableList<NewCar> usersCarList = FXCollections.observableArrayList();
    TableViewCreation createView = new TableViewCreation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) userViewPane.getScene().getWindow();
                stage.setWidth(1081);
                stage.setHeight(500);
            } catch (ExceptionInInitializerError e) {
                tvLabel.setText("Error in setting the proper width and height, resize the window manually.");
            }
        });

        tvLabel.setText("Loading cars...");
        createView.initializeTv(tableView, tvLabel, false);
        addChoiceBoxItems();

        if(tvLabel.getText().equals("Loading cars...")) {
            tvLabel.setText("Cars loaded!");
        } else if(tvLabel.getText().equals("Could not load user base.")) {
            tvLabel.setText("Cars loaded however the userbase isnt loaded. Contact the superUser to restore this.");
            tableView.setDisable(true);
            filterBtn.setVisible(false);
            filterBox.setVisible(false);
            filterText.setVisible(false);
            resetFilterBtn.setVisible(false);
        } else if(tvLabel.getText().equals("Could not load the carlist.")) {
            tvLabel.setText("Cars arent loaded. Contact the superUser to restore this.");
            tableView.setVisible(false);
            filterBtn.setVisible(false);
            filterBox.setVisible(false);
            filterText.setVisible(false);
            resetFilterBtn.setVisible(false);
        } else if(tvLabel.getText().equals("Could not load add-ons.")){
            tvLabel.setText("");
        } else if(tvLabel.getText().equals("Could not load fuel.")) {
            tvLabel.setText("");
        } else if(tvLabel.getText().equals("Could not load wheels.")) {
            tvLabel.setText("");
        } else if(tvLabel.getText().equals("Could not load colors.")) {
            tvLabel.setText("");
        }

        tableView.refresh();
        tableView.setEditable(false);

        /*Filtering table for Users */
        usersCarList.removeAll();
        usersCarList.setAll(tableView.getItems().filtered(newcar -> newcar.getUser().equals(App.car.getUser())));
        tableView.setItems(usersCarList);
        tableView.getColumns().remove(tableView.getColumns().size()-1);
    }


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


    @FXML
    void goBack() {
        try {
            App.setRoot("WelcomeScreen");
        } catch (IOException e) {
            tvLabel.setText("An error has occurred please contact your developer.");
        } catch (IllegalStateException e) {
            tvLabel.setText("There is an error in loading the next screen, please contact your developer.");
        }
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
            tableView.getItems().clear();
            filteredList = Filter.filtering(filteredText, filterType, filteredList, usersCarList);
            tvLabel.setText(Filter.filteringFeedback(filterType, filteredList));
            tableView.setItems(filteredList);
            tableView.refresh();
        }
    }

    private void addChoiceBoxItems() {
        ObservableList<String> choiceBoxList = Filter.choiceBoxList();
        filterBox.getItems().addAll(choiceBoxList);
        filterBox.getItems().remove(choiceBoxList.get(1));
        filterBox.setValue(choiceBoxList.get(0));
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
        tableView.refresh();
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
                try {
                    FileSaverTxt.overwrite(str, selectedFile, tvLabel, username);
                } catch (IOException e){
                    tvLabel.setText(e.getMessage());
                }
            } else if (result.get() == append) {
                File selectedFile = new File(String.valueOf(path));
                String str = FormatCar.formatCar(outputList);
                FileSaverTxt.append(str, selectedFile, tvLabel, username);
            } else {
                tvLabel.setText("Process cancelled. File wasnt saved.");
            }
        }
    }
}
