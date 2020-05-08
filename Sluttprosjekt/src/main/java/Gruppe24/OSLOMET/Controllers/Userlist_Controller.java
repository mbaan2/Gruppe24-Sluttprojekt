package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.Filter;
import Gruppe24.OSLOMET.UserLogin.FormatUser;
import Gruppe24.OSLOMET.UserLogin.ImportUser;
import Gruppe24.OSLOMET.UserLogin.User;
import Gruppe24.OSLOMET.UserLogin.WriteUser;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
    private TableColumn<User, String> user, pw, location, gender, secretQ, secretQA;

    @FXML
    private Button filterBtn, backBtn, resetFilterBtn;

    ObservableList<String> choiceBoxOptions = FXCollections.observableArrayList();
    ObservableList<User> userList = FXCollections.observableArrayList();
    HashMap<String, String> userBase = new HashMap<>();
    ObservableList<String> secretQObsList = FXCollections.observableArrayList();
    List<String> secretQList = new ArrayList<>();

    @FXML
    void btnFilter() {
        ObservableList<User> filteredList = FXCollections.observableArrayList();

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

    void deleteUser(ObservableList<User> userList, User user, HashMap<String, String> userBase, TableView<User> tv){
        userList.remove(user);
        userBase.remove(user.getUsername(), user.getPassword());
        Path path = Paths.get(user.getUsername() + "sCars.txt");
        if(!path.toString().isEmpty()) {
            File selectedFile = new File(String.valueOf(path));
            selectedFile.delete();
        }
        lblUserList.setText(user.getUsername() + " deleted. Its associated files are also deleted.");
        btnSaveChanges();
        tv.refresh();
    }

    private void btnSaveChanges() {
        List<User> newList = new ArrayList<>(userList);
        try {
            String str = FormatUser.formatUsers(newList);
            Path path = Paths.get(StandardPaths.usersTXTPath);
            File selectedFile = new File(String.valueOf(path));
            WriteUser.writeString(selectedFile, str);
        } catch (IOException e) {
            lblUserList.setText(e.getLocalizedMessage());
        }

        HashMap<String, String> newMap = new HashMap<>(userBase);
        try {
            FileSaverJobj.SaveUser(newMap);
        } catch (IOException e) {
            lblUserList.setText(e.getLocalizedMessage());
        }
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

    public ObservableList<String> genderList() {
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
        try {
            comboBoxList();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not found.");
            alert.setContentText("Couldn't find the file to load the options for the combobox. Restore it through the button in the superuser homepage.");
            alert.setHeaderText("");
        }
        lblUserList.setText("Loading users...");

        tableView.setVisible(false);
        tableView.setEditable(true);

        addChkBoxItems();

        try {
            userList = ImportUser.readUser(StandardPaths.usersTXTPath);
        } catch (IOException e) {
            lblUserList.setText(e.getLocalizedMessage());
        }
        userBase = FileOpenerJobj.openFileHashMap();
        user.setCellFactory(TextFieldTableCell.forTableColumn());
        user.setOnEditCommit(user -> {
            if(user.getRowValue().getUsername().equals(user.getNewValue()) && userBase.containsKey(user.getRowValue().getUsername())) {
                lblUserList.setText("That username is already in use.");
            } else {
                lblUserList.setText("Username changed from " + user.getRowValue().getUsername() + " to " + user.getNewValue() + ".");
                user.getRowValue().setUsername(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        pw.setCellFactory(TextFieldTableCell.forTableColumn());
        pw.setOnEditCommit(user -> {
            if(user.getRowValue().getPassword().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that password.");
            } else {
                lblUserList.setText("The password for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getPassword() + " to " + user.getNewValue() + ".");
                user.getRowValue().setPassword(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        location.setCellFactory(TextFieldTableCell.forTableColumn());
        location.setOnEditCommit(user -> {
            if(user.getRowValue().getLocation().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that location.");
            } else {
                lblUserList.setText("The location for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getLocation() + " to " + user.getNewValue() + ".");
                user.getRowValue().setLocation(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        gender.setCellFactory(ComboBoxTableCell.forTableColumn(genderList()));
        gender.setOnEditCommit(user -> {
            if(user.getRowValue().getGender().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that gender.");
            } else {
                lblUserList.setText("The gender for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getGender() + " to " + user.getNewValue() + ".");
                user.getRowValue().setGender(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        secretQList = FileOpenerJobj.openSecretQList();
        secretQObsList.addAll(secretQList);
        secretQ.setCellFactory(ComboBoxTableCell.forTableColumn(secretQObsList));
        secretQ.setOnEditCommit(user -> {
            if(user.getRowValue().getSecretQ().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that secret question.");
            } else {
                lblUserList.setText("The secret question for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getSecretQ() + " to " + user.getNewValue());
                user.getRowValue().setSecretQ(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        secretQA.setCellFactory(TextFieldTableCell.forTableColumn());
        secretQA.setOnEditCommit(user -> {
            if(user.getRowValue().getSecretQAnswer().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that answer.");
            } else {
                lblUserList.setText("The secret questions answer for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getSecretQAnswer() + " to " + user.getNewValue() + ".");
                user.getRowValue().setSecretQAnswer(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });

        TableColumn<User, Button> deleteBtn = new TableColumn<>();
        tableView.getColumns().add(deleteBtn);

        deleteBtn.setCellValueFactory(user -> {
            Button delete = new Button();
            delete.setText("Delete");
            delete.getStyleClass().add("delete-button");
            delete.setAlignment(Pos.CENTER);
            delete.setOnAction(event -> deleteUser(userList, user.getValue(), userBase, tableView));

            ObservableValue<Button> btn = new ObservableValueBase<Button>() {
                @Override
                public Button getValue() {
                    return delete;
                }
            };
            return btn;
        });
        tableView.setItems(userList);
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
