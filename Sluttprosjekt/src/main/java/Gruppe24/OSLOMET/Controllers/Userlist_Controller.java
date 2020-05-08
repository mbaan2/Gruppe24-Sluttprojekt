package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.Filter;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.TableViewCreation;
import Gruppe24.OSLOMET.UserLogin.ImportUser;
import Gruppe24.OSLOMET.UserLogin.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Userlist_Controller implements Initializable {

    @FXML
    private AnchorPane userListPane;

    @FXML
    private TableView<User> tableView;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField filterTxt;

    @FXML
    private Label lblUserList;

    @FXML
    private Button filterBtn, backBtn, resetFilterBtn;

    TableViewCreation createView = new TableViewCreation();
    ObservableList<String> choiceBoxOptions = FXCollections.observableArrayList();
    ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    void btnFilter() throws IOException {
        ObservableList<User> filteredList = FXCollections.observableArrayList();
        userList = ImportUser.readUser(StandardPaths.usersTXTPath);
        String filteredText = filterTxt.getText();
        String filterType = choiceBox.getValue();

        filteredList.clear();
        lblUserList.setText("");

        if(filteredText.equals("")) {
            lblUserList.setText("You didnt enter anything!");
        }
        else if(filterType.equals("Select a filter!")) {
            lblUserList.setText("You didnt choose a filter!");
        } else {
            filteredList = Filter.filterUser(filterType, filteredText, userList, filteredList);
            lblUserList.setText(Filter.filterUserFeedback(filterType, filteredList));
            tableView.setItems(filteredList);
            tableView.refresh();
        }
    }

    @FXML
    void btnResetFilter() {
        filterTxt.setText("");
        lblUserList.setText("TableView reset.");
        tableView.setItems(userList);
        choiceBox.setValue("Select a filter!");
    }

    @FXML
    void btnBack() {
        try {
            App.setRoot("SuperUser");
        } catch (IOException e) {
            lblUserList.setText(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
        executor.shutdownNow();
    }

    private void addChkBoxItems() {
        choiceBoxOptions.removeAll();
        String chBoxFilter = "Select a filter!";
        String chBoxType = "Username";
        String chBoxType2 = "Location";
        String chBoxType3 = "Gender";
        String chBoxType4 = "Secret Question";

        choiceBoxOptions.addAll(chBoxFilter, chBoxType, chBoxType2, chBoxType3, chBoxType4);
        choiceBox.getItems().addAll(choiceBoxOptions);
        choiceBox.setValue(chBoxFilter);
    }

    // Thread solution based on a comment from https://stackoverflow.com/questions/36593572/javafx-tableview-high-frequent-updates
    public final Runnable setTableView = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            tableView.getItems();
        }
    };

    private final ExecutorService executor = Executors.newSingleThreadScheduledExecutor(runnable -> {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            lblUserList.setText("Error in fetching the table!");
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    public static ObservableList<String> genderList() {
        ObservableList<String> secretQList = FXCollections.observableArrayList();

        String gender = "Male";
        String gender2 = "Female";
        String gender3 = "Other";

        secretQList.addAll(gender, gender2, gender3);
        return secretQList;
    }

    public static void comboBoxList() throws IOException {
        List<String> comboBoxList;
        comboBoxList = FileOpenerJobj.openSecretQList();
        FileSaverJobj.SaveSecretQList(comboBoxList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterBtn.setDisable(true);
        backBtn.setDisable(true);
        resetFilterBtn.setDisable(true);
        tableView.setVisible(false);
        try {
            comboBoxList();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not found.");
            alert.setContentText("Couldn't find the file to load the options for the combobox. Restore it through the button in the superuser homepage.");
            alert.setHeaderText("");
        }
        lblUserList.setText("Loading users...");
        try {
            createView.initializeUserTv(tableView, lblUserList);
        } catch (IOException e) {
            lblUserList.setText(e.getMessage());
        }
        addChkBoxItems();

        tableView.setEditable(true);

        Platform.runLater(() -> {
            Stage stage = (Stage) userListPane.getScene().getWindow();
            stage.setHeight(470);
            stage.setWidth(1000);
            filterBtn.setDisable(false);
            backBtn.setDisable(false);
            resetFilterBtn.setDisable(false);
            tableView.setVisible(true);
            lblUserList.setText("Users loaded!");
            executor.submit(setTableView);
            tableView.refresh();
        });
    }
}
