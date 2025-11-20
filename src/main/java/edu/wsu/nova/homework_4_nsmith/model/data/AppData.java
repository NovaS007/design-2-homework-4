package edu.wsu.nova.homework_4_nsmith.model.data;

import edu.wsu.nova.homework_4_nsmith.model.characters.HorrorCharacter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * AppData class to manage the collection of horror characters.
 */
public class AppData {
    // ObservableList to store horror characters
    // Using ObservableList to allow UI components to observe changes and update accordingly

    /** The list of horror characters in the application.
     */
    private static final ObservableList<HorrorCharacter> monsters =
            FXCollections.observableArrayList();

    /**
     * Get all horror characters.
     * @return ObservableList of all horror characters.
     */
    public static ObservableList<HorrorCharacter> getAllCharacters() {
        return monsters;
    }

    /**
     * Add a new horror character to the collection.
     * @param horrorCharacter The horror character to add.
     */
    public static void addCharacter(HorrorCharacter horrorCharacter) {
        monsters.add(horrorCharacter);
    }


    public static void clearCharacters() {
        monsters.clear();
    }
}

