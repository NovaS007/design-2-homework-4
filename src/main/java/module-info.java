module edu.wsu.nova.homework_4_nsmith {
    // JavaFX modules that are required
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    // Export and open controllers for FXML
    exports edu.wsu.nova.homework_4_nsmith.utility.controllers;
    opens edu.wsu.nova.homework_4_nsmith.utility.controllers to javafx.fxml;

    // Export and open app package for FXML (if needed)
    exports edu.wsu.nova.homework_4_nsmith.app;
    opens edu.wsu.nova.homework_4_nsmith.app to javafx.fxml;

    // Open model packages for JavaFX reflection (PropertyValueFactory)
    opens edu.wsu.nova.homework_4_nsmith.model.characters to javafx.base, javafx.fxml;
    opens edu.wsu.nova.homework_4_nsmith.model.data to javafx.base, javafx.fxml;
}
