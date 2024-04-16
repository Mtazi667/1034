module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
}