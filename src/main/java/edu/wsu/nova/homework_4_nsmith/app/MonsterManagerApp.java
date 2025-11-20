package edu.wsu.nova.homework_4_nsmith.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the Monster Manager JavaFX application.
 */
public class MonsterManagerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MonsterManagerApp.class.getResource("/edu/wsu/nova/homework_4_nsmith/views/create-character.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Monster Manager");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}