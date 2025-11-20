package edu.wsu.nova.homework_4_nsmith.utility.controllers;

import edu.wsu.nova.homework_4_nsmith.io.FileHandler;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable;
import edu.wsu.nova.homework_4_nsmith.model.characters.HorrorCharacter;
import edu.wsu.nova.homework_4_nsmith.model.data.AppData;
import edu.wsu.nova.homework_4_nsmith.utility.SceneSwitcher;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Controller class for viewing horror characters.
 */
public class ViewCharacterController {

    // ----------------- FXML UI Components ----------------- //
    @FXML private Button loadMonsterButton;
    @FXML private Button saveMonsterButton;
    @FXML private Button createMonsterButton;

    @FXML private TableView<HorrorCharacter> monsterTable;
    @FXML private TableColumn<HorrorCharacter, String> transformedColumn;
    @FXML private TableColumn<HorrorCharacter, String> monsterTypeColumn;
    @FXML private TableColumn<HorrorCharacter, String> monsterNameColumn;
    @FXML private TableColumn<HorrorCharacter, Integer> monsterHealthColumn;
    @FXML private TableColumn<HorrorCharacter, String> monsterVulnerabilitiesColumn;
    @FXML private TableColumn<HorrorCharacter, LocalDate> dateCreatedColumn;

    // ----------------- Initialization ----------------- //

    @FXML
    public void initialize() {
        setupTable();
        populateTable();
    }

    private void setupTable() {
        monsterTable.setEditable(true);
        monsterTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        monsterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // ----------------- Cell Value Factories ----------------- //
        monsterTypeColumn.setCellValueFactory(cellData -> cellData.getValue().TYPEProperty());
        monsterNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        monsterHealthColumn.setCellValueFactory(cellData ->
                cellData.getValue().healthProperty().asObject());
        dateCreatedColumn.setCellValueFactory(cellData -> cellData.getValue().dateCreatedProperty());

        monsterVulnerabilitiesColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getVulnerabilities()
                                .stream()
                                .map(Enum::name)
                                .collect(Collectors.joining("; "))
                )
        );

        transformedColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Transformable t) {
                return new SimpleStringProperty(t.getTransformed() ? "Yes" : "No");
            }
            return new SimpleStringProperty("N/A");
        });

        // ----------------- Editable Columns ----------------- //
        monsterNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        monsterNameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        monsterHealthColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        monsterHealthColumn.setOnEditCommit(event -> {
            int newHealth = event.getNewValue() != null ? event.getNewValue() : 0;
            if (newHealth < 0) newHealth = 0;
            event.getRowValue().setHealth(newHealth);
        });

        // ----------------- Column Widths ----------------- //
        // These are relative; JavaFX will proportionally adjust with table width
        monsterTypeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15);         // 15%
        transformedColumn.setMaxWidth(1f * Integer.MAX_VALUE * 10);         // 10%
        monsterNameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 20);         // 20%
        monsterHealthColumn.setMaxWidth(1f * Integer.MAX_VALUE * 10);       // 10%
        monsterVulnerabilitiesColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 30%
        dateCreatedColumn.setMaxWidth(1f * Integer.MAX_VALUE * 15);         // 15%
    }

    // ----------------- Table Population ----------------- //
    @FXML
    public void populateTable() {
        ObservableList<HorrorCharacter> allMonsters = AppData.getAllCharacters();
        monsterTable.setItems(allMonsters);
    }

    // ----------------- Scene Switching ----------------- //
    @FXML
    public void changeToMonsterCreatorView() {
        try {
            SceneSwitcher sceneSwitcher = new SceneSwitcher();
            sceneSwitcher.switchScenes("/edu/wsu/nova/homework_4_nsmith/views/create-character.fxml",
                    createMonsterButton);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------- File I/O ----------------- //
    @FXML
    public void saveMonsters() {
        FileHandler.saveAsFile();
    }

    @FXML
    public void loadMonsters() {
        FileHandler.loadFromFile();
        populateTable(); // Refresh table after loading
    }
}
