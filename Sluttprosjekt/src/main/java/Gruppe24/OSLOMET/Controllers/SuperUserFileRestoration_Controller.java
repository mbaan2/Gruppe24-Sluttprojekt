package Gruppe24.OSLOMET.Controllers;

import Gruppe24.OSLOMET.App;
import Gruppe24.OSLOMET.SuperUserClasses.RestoreFiles.CreateJobjFiles;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SuperUserFileRestoration_Controller implements Initializable {

    @FXML
    private AnchorPane fileRestorationPane;

    @FXML
    private Label superUserLbl;

    @FXML
    private Tooltip superuserInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            Stage stage = (Stage) fileRestorationPane.getScene().getWindow();
            stage.setWidth(300);
            stage.setHeight(473);
        });
        superuserInfo.setShowDelay(Duration.millis(100.0));
        superuserInfo.setHideDelay(Duration.millis(200.0));
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
        if(result.isPresent()) {
            if (result.get() == yesBtn) {
                CreateJobjFiles restoreFuel = new CreateJobjFiles();
                restoreFuel.createFuel();
                CreateJobjFiles restoreWheels = new CreateJobjFiles();
                restoreWheels.createWheels();
                CreateJobjFiles restoreColor = new CreateJobjFiles();
                restoreColor.createColors();
                CreateJobjFiles restoreAddon = new CreateJobjFiles();
                restoreAddon.createAddons();
                superUserLbl.setText("Files are restored!");
            } else {
                superUserLbl.setText("Process cancelled, carparts were not restored.");
            }
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
        if(result.isPresent()) {
            if (result.get() == yesBtn) {
                CreateJobjFiles restoreUser = new CreateJobjFiles();
                restoreUser.createUser();
                superUserLbl.setText("Users are restored!");
            } else {
                superUserLbl.setText("Process cancelled, users were not restored.");
            }
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
        if(result.isPresent()) {
            if (result.get() == yesBtn) {
                CreateJobjFiles restoreCars = new CreateJobjFiles();
                restoreCars.createCars();
                superUserLbl.setText("Cars are restored!");
            } else {
                superUserLbl.setText("Process cancelled, cars were not restored.");
            }
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
        if(result.isPresent()) {
            if (result.get() == yesBtn) {
                CreateJobjFiles restoreSecretQ = new CreateJobjFiles();
                restoreSecretQ.createSecretQ();
                superUserLbl.setText("Secret questions are restored!");
            } else {
                superUserLbl.setText("Process cancelled, secret questions were not restored.");
            }
        }
    }

    @FXML
    void btnBackToSuperuser() {
        try {
            App.setRoot("SuperUser");
        } catch (IOException e){
            System.err.println(e.getMessage());
        } catch (IllegalStateException e){
            System.err.println("There is an error in loading the next screen, please contact your developer.");
        }
    }
}
