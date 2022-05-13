module com.example.workshop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.workshop to javafx.fxml;
    exports com.example.workshop;
}