module Gruppe24.OSLOMET {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    opens Gruppe24.OSLOMET to javafx.fxml;
    exports Gruppe24.OSLOMET;
}