module com.pkadung.quizzapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;

    opens com.pkadung.quizzapp to javafx.fxml;
    exports com.pkadung.quizzapp;
    exports com.pkadung.pojo;
}
