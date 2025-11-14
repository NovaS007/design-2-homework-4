package edu.wsu.nova.homework_4_nsmith.model.characters;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;
import java.util.List;

/**
 * edu.wsu.nova.homework_4_nsmith.model.characters.Zombie class representing a zombie character in a horror game
 * Inherits from edu.wsu.nova.homework_4_nsmith.model.characters.HorrorCharacter class
 * Implements attack and flee methods
 * Overrides toString method
 */
public class Zombie extends HorrorCharacter{

    /** Constructor for characters.Zombie class
     * @param name Name of the zombie
     * @param health Health of the zombie
     * @param vulnerabilities Array of vulnerabilities of the zombie
     */
    public Zombie(String name, int health, List<Vulnerability> vulnerabilities){
        super(name, health, vulnerabilities);
    }

    /**
     * Attack method for characters.Zombie class
     * Prints attack message
     */
    @Override
    public void attack(HorrorCharacter target) {
        System.out.println("The zombie bites!");
    }

    /**
     * Flee method for characters.Zombie class
     * Prints flee message
     */
    @Override
    public void flee() {
        System.out.println("The zombie stumbles away!");
    }

    /**
     * toString method for characters.Zombie class
     * Returns a string representation of the zombie
     */
    @Override
    public String toString() {
        return "This is a zombie! \n" +
                super.toString();
    }
}
