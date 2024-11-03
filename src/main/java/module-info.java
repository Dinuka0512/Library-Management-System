module edu.ijse.gdse.libarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens edu.ijse.gdse.libarymanagementsystem.dto.tm to javafx.base;
    opens edu.ijse.gdse.libarymanagementsystem.controller to javafx.fxml;
    exports edu.ijse.gdse.libarymanagementsystem;
}