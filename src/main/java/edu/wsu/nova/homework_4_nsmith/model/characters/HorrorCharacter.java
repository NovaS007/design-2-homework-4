package edu.wsu.nova.homework_4_nsmith.model.characters;

import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;

import javafx.beans.property.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public abstract class HorrorCharacter implements Serializable {

    // Transient JavaFX properties (won't be serialized directly)
    /** Transient JavaFX properties */
    private transient StringProperty name = new SimpleStringProperty();
    private transient IntegerProperty health = new SimpleIntegerProperty();
    private transient ObjectProperty<List<Vulnerability>> vulnerabilities = new SimpleObjectProperty<>();
    private transient ObjectProperty<LocalDate> dateCreated = new SimpleObjectProperty<>();

    // Serializable backing fields (will be serialized)
    /** Serializable backing fields */
    private String nameValue;
    private int healthValue;
    private List<Vulnerability> vulnerabilitiesValue;
    private LocalDate dateCreatedValue;

    // Constructor

    /**
     * Constructor for HorrorCharacter
     * @param name
     * @param health
     * @param vulnerabilities
     * @param dateCreated
     */
    public HorrorCharacter(String name, int health, List<Vulnerability> vulnerabilities, LocalDate dateCreated) {
        this.name.set(name);
        this.health.set(health);
        this.vulnerabilities.set(vulnerabilities);
        this.dateCreated.set(dateCreated);

        this.nameValue = name;
        this.healthValue = health;
        this.vulnerabilitiesValue = vulnerabilities;
        this.dateCreatedValue = dateCreated;
    }

    // Abstract methods
    /** Abstract methods for attack and flee actions */
    public abstract void attack(HorrorCharacter target);
    public abstract void flee();

    // Getters and setters
    public String getName() { return name.get(); }
    public void setName(String name) {
        this.name.set(name);
        this.nameValue = name;
    }
    public StringProperty nameProperty() { return name; }

    public int getHealth() { return health.get(); }
    public void setHealth(int health) {
        this.health.set(health);
        this.healthValue = health;
    }
    public IntegerProperty healthProperty() { return health; }

    public List<Vulnerability> getVulnerabilities() { return vulnerabilities.get(); }
    public void setVulnerabilities(List<Vulnerability> vulnerabilities) {
        this.vulnerabilities.set(vulnerabilities);
        this.vulnerabilitiesValue = vulnerabilities;
    }
    public ObjectProperty<List<Vulnerability>> vulnerabilitiesProperty() { return vulnerabilities; }

    public LocalDate getDateCreated() { return dateCreated.get(); }
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated.set(dateCreated);
        this.dateCreatedValue = dateCreated;
    }
    public ObjectProperty<LocalDate> dateCreatedProperty() { return dateCreated; }

    private final transient StringProperty TYPE = new SimpleStringProperty("HorrorCharacter");
    public String getTYPE() { return TYPE.get(); }
    public StringProperty TYPEProperty() { return TYPE; }


    @Override
    public String toString() {
        return "characters.HorrorCharacter\n" +
                "Name: " + getName() + "\n" +
                "Health: " + getHealth() + "\n" +
                "Vulnerabilities: " + getVulnerabilities();
    }

    // Custom serialization
    /** Custom serialization methods to handle transient JavaFX properties */
    @Serial
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        // Update backing fields
        nameValue = getName();
        healthValue = getHealth();
        vulnerabilitiesValue = getVulnerabilities();
        dateCreatedValue = getDateCreated();

        objectOutputStream.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        // Default deserialization
        objectInputStream.defaultReadObject();
        // Restore transient properties from backing fields
        name = new SimpleStringProperty(nameValue);
        health = new SimpleIntegerProperty(healthValue);
        vulnerabilities = new SimpleObjectProperty<>(vulnerabilitiesValue);
        dateCreated = new SimpleObjectProperty<>(dateCreatedValue);
    }
}
