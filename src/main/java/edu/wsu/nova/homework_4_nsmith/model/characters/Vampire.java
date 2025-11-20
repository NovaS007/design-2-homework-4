package edu.wsu.nova.homework_4_nsmith.model.characters;

import edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

public class Vampire extends HorrorCharacter implements Transformable {



    // Transient JavaFX properties
    private transient StringProperty type = new SimpleStringProperty("Vampire");
    private transient IntegerProperty maxHealth;
    private transient BooleanProperty isTransformed = new SimpleBooleanProperty(false);

    // Serializable backing fields
    private String typeValue;
    private int maxHealthValue;
    private boolean transformedValue;

    public Vampire(String name, int health, List<Vulnerability> vulnerabilities, LocalDate dateCreated) {
        super(name, health, vulnerabilities, dateCreated);
        this.maxHealth = new SimpleIntegerProperty(health);
        this.isTransformed.set(false);

        // Set backing fields
        this.typeValue = "Vampire";
        this.maxHealthValue = health;
        this.transformedValue = false;
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
    public boolean getTransformed() { return isTransformed.get(); }
    @Override
    public void setTransformed(boolean transformed) {
        this.isTransformed.set(transformed);
        this.transformedValue = transformed;
    }
    public BooleanProperty transformedProperty() { return isTransformed; }

    @Override
    public void attack(HorrorCharacter target) {
        System.out.println(isTransformed.get() ? "The bat bites!" : "The vampire bites!");
    }

    @Override
    public void flee() {
        System.out.println(isTransformed.get() ? "The bat flies away!" : "The vampire disappears!");
    }

    @Override
    public void transform() {
        setTransformed(!getTransformed());
    }

    @Override
    public String toString() {
        return (isTransformed.get() ? "The vampire transformed into a bat!" : "The vampire is not transformed!")
                + "\n" + super.toString();
    }

    // Serialization
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        typeValue = type.get();
        maxHealthValue = maxHealth.get();
        transformedValue = isTransformed.get();
        oos.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        type = new SimpleStringProperty(typeValue);
        maxHealth = new SimpleIntegerProperty(maxHealthValue);
        isTransformed = new SimpleBooleanProperty(transformedValue);
    }
}
