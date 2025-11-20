package edu.wsu.nova.homework_4_nsmith.model.characters;

import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

public class Zombie extends HorrorCharacter {


    // Transient JavaFX properties
    private transient StringProperty type = new SimpleStringProperty("Zombie");
    private transient IntegerProperty maxHealth;

    // Serializable backing fields
    private String typeValue;
    private int maxHealthValue;

    public Zombie(String name, int health, List<Vulnerability> vulnerabilities, LocalDate dateCreated) {
        super(name, health, vulnerabilities, dateCreated);
        this.maxHealth = new SimpleIntegerProperty(health);

        // Initialize backing fields
        this.typeValue = "Zombie";
        this.maxHealthValue = health;
    }

    // Getters and setters
    public String getTYPE() { return type.get(); }
    public void setType(String type) {
        this.type.set(type);
        this.typeValue = type;
    }
    public StringProperty TYPEProperty() { return type; }

    public int getMaxHealth() { return maxHealth.get(); }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth.set(maxHealth);
        this.maxHealthValue = maxHealth;
    }
    public IntegerProperty maxHealthProperty() { return maxHealth; }

    @Override
    public void attack(HorrorCharacter target) {
        System.out.println("Zombie attacks!");
    }

    @Override
    public void flee() {
        System.out.println("Zombie flees!");
    }

    // Serialization
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        typeValue = type.get();
        maxHealthValue = maxHealth.get();
        oos.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        type = new SimpleStringProperty(typeValue);
        maxHealth = new SimpleIntegerProperty(maxHealthValue);
    }
}
