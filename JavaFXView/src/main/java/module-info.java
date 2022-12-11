module com.example.javafxview {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;


    


    opens com.example.javafxview to javafx.fxml;
    exports com.example.javafxview;
}