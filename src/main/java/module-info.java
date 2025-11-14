module edu.wsu.nova.homework_4_nsmith {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.wsu.nova.homework_4_nsmith to javafx.fxml;
    exports edu.wsu.nova.homework_4_nsmith;
    exports edu.wsu.nova.homework_4_nsmith.controllers;
    opens edu.wsu.nova.homework_4_nsmith.controllers to javafx.fxml;
}