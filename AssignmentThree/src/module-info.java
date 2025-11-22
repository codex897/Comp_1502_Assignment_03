module AssignmentThree {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens mru.tsc.controller to javafx.fxml;
    
    exports application;
}