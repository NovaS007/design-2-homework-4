package edu.wsu.nova.homework_4_nsmith.utility.controllers;

import edu.wsu.nova.homework_4_nsmith.io.FileHandler;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;

import edu.wsu.nova.homework_4_nsmith.model.characters.HorrorCharacter;
import edu.wsu.nova.homework_4_nsmith.model.characters.Vampire;
import edu.wsu.nova.homework_4_nsmith.model.characters.Werewolf;
import edu.wsu.nova.homework_4_nsmith.model.characters.Zombie;

import edu.wsu.nova.homework_4_nsmith.model.data.AppData;

import edu.wsu.nova.homework_4_nsmith.utility.SceneSwitcher;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;

import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;

/**
 * Controller class for creating horror characters.
 */
public class CreateCharacterController {
    // List to track vulnerability selections
    /** An observable list of BooleanProperties to track the selection state of vulnerabilities
     * in the ListView. Each BooleanProperty corresponds to an item in the ListView, allowing
     * for easy binding and state management.
     */
    private final ObservableList<BooleanProperty> vulnerabilitySelections = FXCollections.observableArrayList();

    // FXML UI Components
    @FXML
    private Button loadMonsterButton;

    @FXML
    private Button addMonsterButton;

    @FXML
    private Button saveMonsterButton;

    @FXML
    private Button viewMonsterButton;

    @FXML
    private DatePicker creationDatePicker;

    @FXML
    private TextField healthField;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> monsterTypeComboBox;

    @FXML
    private ListView<String> vulnerabilityListView;

    @FXML
    private CheckBox transformCheckBox;

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        initializeComboBox();
        initializeListView();

        monsterTypeComboBox.getSelectionModel().selectedItemProperty().addListener((_, _, newVal) ->
                transformCheckBox.setDisable("Zombie".equals(newVal)));
    }

    /**
     * Initialize the monster type ComboBox with options.
     */
    @FXML
    public void initializeComboBox() {
        ObservableList<String> monsterChoices = FXCollections.observableArrayList(
                "Vampire",
                "Werewolf",
                "Zombie");

        monsterTypeComboBox.setItems(monsterChoices);
        monsterTypeComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Initialize the vulnerability ListView with options and checkboxes.
     */
    @FXML
    public void initializeListView() {
        ObservableList<String> vulnerabilityOptions = FXCollections.observableArrayList(
                "Fire",
                "Sunlight",
                "Silver",
                "Holy Water",
                "Garlic"
        );

        // Set items in ListView
        vulnerabilityListView.setItems(vulnerabilityOptions);

        // Build parallel list of BooleanProperties
        for (int vulnerability = 0; vulnerability < vulnerabilityOptions.size(); vulnerability++) {
            vulnerabilitySelections.add(new SimpleBooleanProperty(false));
        }

        // Set cell factory to use CheckBoxListCell
        vulnerabilityListView.setCellFactory(CheckBoxListCell.forListView(item -> {
            int index = vulnerabilityListView.getItems().indexOf(item);
            return vulnerabilitySelections.get(index);
        }));
    }

    /**
     * Switch to the monster viewer scene.
     */
    @FXML
    public void changeToMonsterViewer() {
        try {
            System.out.println("Switching scenes...");
            SceneSwitcher switcher = new SceneSwitcher();
            ViewCharacterController controller = switcher.switchScenes(
                    "/edu/wsu/nova/homework_4_nsmith/views/view-character.fxml",
                    viewMonsterButton
            );
            if (controller != null) {
                System.out.println("Controller loaded successfully");
                controller.populateTable();
            } else {
                System.out.println("Controller is null");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a new monster to the collection based on user input.
     */
    @FXML
    public void addMonster() {
        // Validate required fields
        if (nameField.getText().isEmpty() ||
                healthField.getText().isEmpty() ||
                creationDatePicker.getValue() == null) {

            showError("Missing Required Fields",
                    "Please ensure all fields are filled out before adding a monster.");
            return;
        }

        // Validate health value
        int maxHealth;
        try {
            maxHealth = Integer.parseInt(healthField.getText());
        } catch (NumberFormatException e) {
            showError("Invalid Health Value", "Please enter a valid integer for health.");
            return;
        }

        // Collect vulnerabilities
        ArrayList<Vulnerability> vulnerabilities = new ArrayList<>();
        ObservableList<String> items = vulnerabilityListView.getItems();

        for (int i = 0; i < items.size(); i++) {
            if (vulnerabilitySelections.get(i).get()) {
                vulnerabilities.add(
                        Vulnerability.valueOf(items.get(i).toUpperCase().replace(" ", "_"))
                );
            }
        }

        // Create the monster
        HorrorCharacter newCharacter = makeHorrorCharacter(maxHealth, vulnerabilities);

        if (newCharacter instanceof Transformable t) {
            t.setTransformed(transformCheckBox.isSelected());
        }

        // Add to AppData
        AppData.addCharacter(newCharacter);

        showConfirmation();

        clearForm();
    }

    // Helper method to create a HorrorCharacter based on user input
    private HorrorCharacter makeHorrorCharacter(int maxHealth, ArrayList<Vulnerability> vulnerabilities) {
        String selectedMonsterType = monsterTypeComboBox.getSelectionModel().getSelectedItem();
        LocalDate dateCreated = creationDatePicker.getValue();
        String name = nameField.getText();

        return switch (selectedMonsterType) {
            case "Vampire" -> new Vampire(name, maxHealth, vulnerabilities, dateCreated);
            case "Werewolf" -> new Werewolf(name, maxHealth, vulnerabilities, dateCreated);
            case "Zombie" -> new Zombie(name, maxHealth, vulnerabilities, dateCreated);
            default -> null;
        };
    }

    // Helper method to clear the form after adding a monster
    private void clearForm() {
        monsterTypeComboBox.getSelectionModel().selectFirst();
        nameField.clear();
        healthField.clear();
        creationDatePicker.setValue(null);
        vulnerabilitySelections.forEach(prop -> prop.set(false));
        transformCheckBox.setSelected(false);
    }

    // Helper method to show error alerts
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show confirmation alerts
    private void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Monster Added");
        alert.setHeaderText("The monster has been added successfully!");
        alert.showAndWait();
    }

    public void saveMonster() {
        FileHandler.saveAsFile();
    }

    public void loadMonster() {
        FileHandler.loadFromFile();
    }
}