package edu.wsu.nova.homework_4_nsmith.model.characters;

import java.util.List;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;

/**
 * Abstract class representing a generic horror character.
 * Contains common attributes and methods for all horror characters.
 */
public abstract class HorrorCharacter {
    private String name;
    private int health;
    private List<Vulnerability> vulnerabilities;

    /**
     * Constructor to initializeComboBox a horror character with a name, health, and vulnerabilities.
     * @param name The name of the horror character.
     * @param health The health points of the horror character.
     * @param vulnerabilities A list of vulnerabilities specific to the horror character.
     */
    public HorrorCharacter(String name, int health, List<Vulnerability> vulnerabilities){
        this.name = name;
        this.health = health;
        this.vulnerabilities = vulnerabilities;
    }

    /**
     * Abstract method for the character to perform an attack.
     */
    public abstract void attack(HorrorCharacter target);

    /**
     * Abstract method for the character to flee.
     */
    public abstract void flee();

    /**
     * Getter for the name of the horror character.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the horror character.
     * Ensures the name is not null or empty.
     * @param name The new name for the horror character.
     */
    public void setName(String name) {
        if (name != null && !name.isEmpty())
            this.name = name;
    }

    /**
     * Getter for the health of the horror character.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter for the health of the horror character.
     * Ensures health is non-negative.
     * @param health The new health value for the horror character.
     */
    public void setHealth(int health) {
        if (health >= 0)
            this.health = health;
    }

    /**
     * Getter for the vulnerabilities of the horror character.
     */
    public List<Vulnerability> getVulnerabilities(){
        return vulnerabilities;
    }

    /**
     * Setter for the vulnerabilities of the horror character.
     * Ensures the vulnerabilities array is not null.
     * @param vulnerabilities The new list of vulnerabilities for the horror character.
     */
    public void setVulnerabilities(List<Vulnerability> vulnerabilities) {
        if (vulnerabilities != null)
            this.vulnerabilities = vulnerabilities;
    }

    /**
     * Overridden toString method to provide a string representation of the horror character.
     */
    @Override
    public String toString() {
        return "characters.HorrorCharacter" + '\n' +
                "Name: " + getName() + '\n' +
                "Health: " + getHealth() + '\n' +
                "Vulnerabilities: " + getVulnerabilities();
    }
}
