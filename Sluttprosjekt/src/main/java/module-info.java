module Gruppe24.OSLOMET {
    requires javafx.controls;
    requires javafx.fxml;

    opens Gruppe24.OSLOMET to javafx.fxml;
    exports Gruppe24.OSLOMET;
}