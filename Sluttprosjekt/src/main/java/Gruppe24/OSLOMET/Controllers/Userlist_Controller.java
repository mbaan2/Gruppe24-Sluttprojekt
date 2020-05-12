package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.DataValidation.ValidName;
import Gruppe24.OSLOMET.ExceptionClasses.InvalidNameException;
import Gruppe24.OSLOMET.ExceptionClasses.OpenFileException;
import Gruppe24.OSLOMET.ExceptionClasses.SaveFileException;
import Gruppe24.OSLOMET.FileTreatment.FileOpenerJobj;
import Gruppe24.OSLOMET.FileTreatment.FileSaverJobj;
import Gruppe24.OSLOMET.FileTreatment.StandardPaths;
import Gruppe24.OSLOMET.SuperUserClasses.RestoreFiles.CreateJobjFiles;
import Gruppe24.OSLOMET.SuperUserClasses.TableView.Filter;
import Gruppe24.OSLOMET.UserLogin.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Userlist_Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblSecondaryUserList.setText("");
        lblSecondaryUserList.setVisible(false);
        filterBtn.setDisable(true);
        backBtn.setDisable(true);
        resetFilterBtn.setDisable(true);
        lblUserList.setText("Loading users...");

        tableView.setVisible(false);
        tableView.setEditable(true);

        addChkBoxItems();

        try {
            userList = FileOpenerJobj.openUserList();
            userList1.addAll(userList);
        } catch (IOException | ClassNotFoundException e) {
            lblUserList.setText("Could not to open the user list file. Restore it through the button in the superuser homepage.");
        }
        try {
            userBase = FileOpenerJobj.openFileHashMap();
        } catch (IOException | ClassNotFoundException e){
            lblUserList.setText("Could not find the user list file. Restore it through the button in the superuser homepage.");
        }

        // Adding the columns for the tableview
        TableColumn<User, String> username = new TableColumn<>("Username");
        tableView.getColumns().add(username);
        username.setMinWidth(100);
        TableColumn<User, String> pw = new TableColumn<>("Password");
        tableView.getColumns().add(pw);
        pw.setMinWidth(100);
        TableColumn<User, String> location = new TableColumn<>("Location");
        tableView.getColumns().add(location);
        location.setMinWidth(100);
        TableColumn<User, String> gender = new TableColumn<>("Gender");
        tableView.getColumns().add(gender);
        gender.setMinWidth(60);
        TableColumn<User, String> secretQ = new TableColumn<>("Secret Question");
        tableView.getColumns().add(secretQ);
        secretQ.setMinWidth(170);
        TableColumn<User, String> secretQA = new TableColumn<>("Secret Question \n      Answer");
        tableView.getColumns().add(secretQA);
        secretQA.setMinWidth(75);

        // Setting the data for the tableview

        //Setting the data for the username and how editing works for the column.
        username.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getUsername()));
        username.setCellFactory(TextFieldTableCell.forTableColumn());
        username.setOnEditCommit(user -> {
            // If the username youre trying to change to is already in the list you cant update it.
            if(userBase.containsKey(user.getNewValue())) {
                lblUserList.setText("That username is already in use.");
            } else {
                String username1 = user.getRowValue().getUsername();
                String password1 = user.getRowValue().getPassword();
                try {
                    // Testing to see if its a valid username.
                    ValidName.usernameTest(user.getNewValue());
                    lblUserList.setText("Username changed from " + user.getRowValue().getUsername() + " to " + user.getNewValue() + ".");
                    user.getRowValue().setUsername(user.getNewValue());
                    // Adding the new values to the userbase while removing the previous values.
                    userBase.put(user.getNewValue(), user.getRowValue().getPassword());
                    userBase.remove(username1, password1);
                    // Saving the new values to the userlist.jobj and to users.jobj.
                    btnSaveChanges();
                } catch (InvalidNameException e) {
                    lblUserList.setText(e.getMessage());
                }
            }
            tableView.refresh();
        });
        // Setting the data for the password and how editing works for the column.
        pw.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getPassword()));
        pw.setCellFactory(TextFieldTableCell.forTableColumn());
        pw.setOnEditCommit(user -> {
            // If the user already has that password it will let you know that in the label.
            if(user.getRowValue().getPassword().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that password.");
            } else {
                lblUserList.setText("The password for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getPassword() + " to " + user.getNewValue() + ".");
                user.getRowValue().setPassword(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        //Setting the data for the location and how editing works for that column.
        location.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getLocation()));
        location.setCellFactory(TextFieldTableCell.forTableColumn());
        location.setOnEditCommit(user -> {
            // If the user already has that location it will let you know that in the label.
            if(user.getRowValue().getLocation().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that location.");
            } else {
                try {
                    // Testing to see if its a valid location.
                    ValidName.locationTest(user.getNewValue());
                    lblUserList.setText("The location for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getLocation() + " to " + user.getNewValue() + ".");
                    user.getRowValue().setLocation(user.getNewValue());
                    btnSaveChanges();
                } catch (InvalidNameException e) {
                    lblUserList.setText(e.getMessage());
                }
            }
            tableView.refresh();
        });
        // Setting the data for the gender and how editing works for that column.
        gender.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getGender()));
        // Adding a combobox with the gender options from the signup page in the column.
        gender.setCellFactory(ComboBoxTableCell.forTableColumn(genderList()));
        gender.setOnEditCommit(user -> {
            // If the user already has that gender it will let you know that in the label.
            if(user.getRowValue().getGender().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that gender.");
            } else {
                lblUserList.setText("The gender for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getGender() + " to " + user.getNewValue() + ".");
                user.getRowValue().setGender(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        // Opening the list for the secret questions to be added to the combobox in the column.
        try {
            secretQList = FileOpenerJobj.openSecretQList();
            secretQ.setEditable(true);
        } catch (IOException e) {
            // If the secretq.jobj isnt loaded because its either deleted or somehow broken you will get an alert with the ability to restore it.
            Alert notEditable = new Alert(Alert.AlertType.WARNING);
            notEditable.setTitle("Error loading secret questions");
            notEditable.setHeaderText("Could not load your secret questions file.");
            notEditable.setHeight(300);
            notEditable.setContentText("Press \"Restore\" to reset your questions file to factory settings. \nPress \"OK\" to continue. This will disable the editing of user questions. Reload the page for full functionality.");
            ButtonType ok = new ButtonType("OK");
            ButtonType restore = new ButtonType("Restore");
            notEditable.getButtonTypes().setAll(ok, restore);
            Optional<ButtonType> choice = notEditable.showAndWait();
            if (choice.get() == restore){
                CreateJobjFiles restoreSecretQ = new CreateJobjFiles();
                try {
                    restoreSecretQ.createSecretQ();
                    secretQ.setEditable(false);
                    notEditable.close();
                } catch (SaveFileException e1) {
                    Alert fail = new Alert(Alert.AlertType.INFORMATION);
                    fail.setHeaderText("Failed to save file.");
                    fail.setTitle("Saving failed.");
                    fail.setContentText("There was an error saving the file. You will now see your users, but will not be allowed to edit their questions.");
                    secretQ.setEditable(false);
                    notEditable.close();
                }
            } else {
                secretQ.setEditable(false);
            }
        } catch (ClassNotFoundException e) {
            lblUserList.setText("Class not found when loading secret questions.");
            //secretQ.setEditable(false);
        }
        secretQObsList.addAll(secretQList);
        // Setting the data for the secret question and how editing works for that column.
        secretQ.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSecretQ()));
        secretQ.setCellFactory(ComboBoxTableCell.forTableColumn(secretQObsList));
        secretQ.setOnEditCommit(user -> {
            // If the user already has that secret question it will let you know that in the label.
            if(user.getRowValue().getSecretQ().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that secret question.");
            } else {
                lblUserList.setText("The secret question for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getSecretQ() + " to " + user.getNewValue());
                user.getRowValue().setSecretQ(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });
        // Setting the data for the the secret questions answer and how editing works for that column.
        secretQA.setCellValueFactory(user -> new SimpleStringProperty(user.getValue().getSecretQAnswer()));
        secretQA.setCellFactory(TextFieldTableCell.forTableColumn());
        secretQA.setOnEditCommit(user -> {
            // If the user already has that answer it will let you know that in the label.
            if(user.getRowValue().getSecretQAnswer().equals(user.getNewValue())) {
                lblUserList.setText("The user already has that answer.");
            } else {
                lblUserList.setText("The secret questions answer for user " + user.getRowValue().getUsername() + " changed from " + user.getRowValue().getSecretQAnswer() + " to " + user.getNewValue() + ".");
                user.getRowValue().setSecretQAnswer(user.getNewValue());
                btnSaveChanges();
            }
            tableView.refresh();
        });

        // Adding the delete buttons column.
        TableColumn<User, Button> deleteBtn = new TableColumn<>();
        tableView.getColumns().add(deleteBtn);

        // Setting the value of the delete button.
        deleteBtn.setCellValueFactory(user -> {
            Button delete = new Button();
            delete.setText("Delete");
            delete.getStyleClass().add("delete-button");
            delete.setAlignment(Pos.CENTER);
            delete.setOnAction(event -> {
                try {
                    deleteUser(userList1, user.getValue(), userBase, tableView, filteredList);
                } catch (IOException e) {
                    lblUserList.setText("Something went wrong with deleting. Try restoring files.");
                }
            });

            ObservableValue<Button> btn = new ObservableValueBase<Button>() {
                @Override
                public Button getValue() {
                    return delete;
                }
            };
            return btn;
        });

        tableView.setItems(userList1);

        // Height and width changes are happening in the runLater method.
        // The tableviews functionality changes depending on the error messages from the exceptions that are being thrown.
        // A thread solution also happening here which makes the changing of the label seem like the tableview is being loaded.
        Platform.runLater(() -> {
            try{
                Stage stage = (Stage) userListPane.getScene().getWindow();
                stage.setHeight(470);
                stage.setWidth(1000);
            }catch (ExceptionInInitializerError e){
                lblUserList.setText("Error in setting the proper width and height, resize the window manually.");
            }
            backBtn.setDisable(false);
            tableView.setVisible(true);
            lblSecondaryUserList.setVisible(true);
            if (lblUserList.getText().equals("Loading users...")){
                lblUserList.setText("Users loaded!");
                executor.submit(setTableView);
                tableView.refresh();
                filterBtn.setDisable(false);
                resetFilterBtn.setDisable(false);
                choiceBox.setDisable(false);
                filterTxt.setDisable(false);
            } else if (lblUserList.getText().equals("Error in setting the proper width and height, resize the window manually.")){
                lblSecondaryUserList.setText("Users loaded!");
                executor.submit(setTableView);
                tableView.refresh();
                filterBtn.setDisable(false);
                resetFilterBtn.setDisable(false);
                choiceBox.setDisable(false);
                filterTxt.setDisable(false);
                lblUserList.setText("Users loaded!");
            } else {
                lblSecondaryUserList.setText("");
                choiceBox.setDisable(true);
                filterTxt.setDisable(true);
                tableView.setDisable(true);
            }
        });
    }

    @FXML
    private AnchorPane userListPane;

    @FXML
    private TableView<User> tableView;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField filterTxt;

    @FXML
    private Label lblUserList, lblSecondaryUserList;

    @FXML
    private Button filterBtn, backBtn, resetFilterBtn;

    ObservableList<String> choiceBoxOptions = FXCollections.observableArrayList();
    List<User> userList = new ArrayList<>();
    ObservableList<User> userList1 = FXCollections.observableArrayList();
    HashMap<String, String> userBase = new HashMap<>();
    ObservableList<String> secretQObsList = FXCollections.observableArrayList();
    List<String> secretQList = new ArrayList<>();
    ObservableList<User> filteredList = FXCollections.observableArrayList();

    @FXML
    void btnFilter() {
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
            filteredList = Filter.filterUser(filterType, filteredText, userList1, filteredList);
            lblUserList.setText(Filter.filterUserFeedback(filterType, filteredList));
            tableView.setItems(filteredList);
            tableView.refresh();
        }
    }

    @FXML
    void btnResetFilter() {
        filterTxt.setText("");
        lblUserList.setText("TableView reset.");
        tableView.setItems(userList1);
        choiceBox.setValue("Select a filter!");
    }

    @FXML
    void btnBack() {
        try {
            App.setRoot("SuperUser");
            lblSecondaryUserList.setText("");
        } catch (IOException e) {
            lblUserList.setText("Program encountered an error. Please contact your developer.");
        } catch (IllegalStateException e){
            lblUserList.setText("There is an error in loading the next screen.");
        }
        executor.shutdownNow();
    }

    // Adding strings to the filter choicebox
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

    // The method for deleting the user - it will delete it from both the users.jobj and the userlist.jobj file.
    void deleteUser(ObservableList<User> userList, User user, HashMap<String, String> userBase, TableView<User> tv, ObservableList<User> filteredList) throws IOException {
        List<NewCar> carList = new ArrayList<>();
        try {
            carList = FileOpenerJobj.openingCarArray(StandardPaths.carsPath);
            for (int i = 0; i < carList.size(); i++) {
                if (carList.get(i).getUser().equals(user.getUsername())) {
                    carList.remove(i);
                    if(carList.get(i).getUser().equals(user.getUsername())) {
                        carList.remove(i);
                    }
                }
            }
        } catch (OpenFileException e) {
            lblUserList.setText("Couldnt delete the users cars because the cars file is corrupted. The user will not be deleted.");
        }

        try {
            FileSaverJobj.SavingCarArray(StandardPaths.carsPath, carList);
        } catch (SaveFileException e) {
            lblUserList.setText("Couldnt delete the users cars because the cars file is corrupted. The user will not be deleted.");
        }
        userList.remove(user);
        filteredList.remove(user);
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

    // The method to save the changes when editing / deleting users. It will save the updated or removed user to both users.jobj and userlist.jobj.
    private void btnSaveChanges() {
        List<User> newList = new ArrayList<>(userList1);
        try {
            FileSaverJobj.SaveUserList(newList);
        } catch (SaveFileException e) {
            lblUserList.setText("Could not save the file, something went wrong! Try restoring the initial file through the superuser homepage.");
        }

        HashMap<String, String> newMap = new HashMap<>(userBase);
        try {
            FileSaverJobj.SaveUser(newMap);
        } catch (SaveFileException e) {
            lblUserList.setText("Could not save the file, something went wrong! Try restoring the initial file through the superuser homepage.");
        }
    }

    // Thread solution based on a comment from https://stackoverflow.com/questions/36593572/javafx-tableview-high-frequent-updates
    public final Runnable setTableView = () -> {
        while (!Thread.currentThread().isInterrupted()) {
            tableView.getItems();
        }
    };

    // This is the thread delay that happens when loading the page.
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

    // The values for the combobox in the gender column.
    public ObservableList<String> genderList() {
        ObservableList<String> secretQList = FXCollections.observableArrayList();

        String gender = "Male";
        String gender2 = "Female";
        String gender3 = "Other";

        secretQList.addAll(gender, gender2, gender3);
        return secretQList;
    }
}
