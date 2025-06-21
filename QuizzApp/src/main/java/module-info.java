module com.pkadung.quizzapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;
    requires java.sql;

    opens com.pkadung.quizzapp to javafx.fxml;
    exports com.pkadung.quizzapp;
}
