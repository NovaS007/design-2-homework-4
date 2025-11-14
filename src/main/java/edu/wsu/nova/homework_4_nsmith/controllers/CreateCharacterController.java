package edu.wsu.nova.homework_4_nsmith.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;

public class CreateCharacterController {
    @FXML
    private ComboBox<String> monsterTypeComboBox;
    @FXML
    private ListView<String> vulnerabilityListView;

    @FXML
    public void initialize(){
        initializeComboBox();
        initializeListView();
    }

    @FXML
    public void initializeComboBox() {
        ObservableList<String> monsterChoices = FXCollections.observableArrayList(
                "Vampire",
                "Werewolf",
                "Zombie");

        monsterTypeComboBox.setItems(monsterChoices);

        monsterTypeComboBox.getSelectionModel().selectFirst();

    }

    @FXML
    public void initializeListView() {
        ObservableList<String> vulnerabilityOptions = FXCollections.observableArrayList(
                "Fire",
                "Sunlight",
                "Silver",
                "Holy Water",
                "Garlic"
        );

        // Set the items in the ListView
        vulnerabilityListView.setItems(vulnerabilityOptions);

        // Create CheckBox cells
        vulnerabilityListView.setCellFactory(CheckBoxListCell.forListView(item -> {
            // Create a BooleanProperty for each item
            BooleanProperty observable = new SimpleBooleanProperty();

            // Optional: listen for changes
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                System.out.println(item + " selected? " + isNowSelected);
            });

            return observable;
        }));
    }
}
