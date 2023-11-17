module com.restaurinoinc.restaurinopos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.restaurinoinc.restaurinopos to javafx.fxml;
    exports com.restaurinoinc.restaurinopos;
}