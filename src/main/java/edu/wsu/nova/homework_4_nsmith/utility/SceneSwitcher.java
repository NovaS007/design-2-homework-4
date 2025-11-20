package edu.wsu.nova.homework_4_nsmith.utility;

import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/** Utility class for switching scenes in a JavaFX application.
 */
public class SceneSwitcher {

    public static Window getCurrentWindow() {
        return Stage.getWindows()
                .stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
    }

    /**
     * Switch scenes in the application.
     *
     * @param fxmlPath The path to the FXML file for the new scene.
     * @param node     The UI node used to get the current stage.
     * @param <T>      The type of the controller for the new scene.
     * @return The controller for the new scene.
     * @throws IOException If there is an error loading the FXML file.
     */
    public <T> T switchScenes(String fxmlPath, Node node) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        return loader.getController();
    }
}