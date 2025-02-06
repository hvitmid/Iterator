module com.example.iterator_task4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.iterator_task4 to javafx.fxml;
    exports com.example.iterator_task4;
}