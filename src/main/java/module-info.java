module com.example.workshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.workshop to javafx.fxml;
    exports com.example.workshop;
    exports com.example.workshop.model.entities;
    opens com.example.workshop.model.entities to javafx.fxml;
}