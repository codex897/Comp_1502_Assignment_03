module as3 {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens mru.tsc.application to javafx.graphics, javafx.fxml;
    opens mru.tsc.controller to javafx.fxml;
    opens mru.tsc.view to javafx.fxml;
    
    exports mru.tsc.application;
}