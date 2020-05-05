module Gruppe24.OSLOMET {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires annotations;

    opens Gruppe24.OSLOMET.Controllers to javafx.fxml;
    opens Gruppe24.OSLOMET.UserLogin to javafx.base;
    opens Gruppe24.OSLOMET.Car to javafx.base;
    exports Gruppe24.OSLOMET;
}