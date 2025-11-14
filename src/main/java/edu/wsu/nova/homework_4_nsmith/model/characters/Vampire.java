package edu.wsu.nova.homework_4_nsmith.model.characters;

import edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;
import java.util.List;

/**
 * edu.wsu.nova.homework_4_nsmith.model.characters.Vampire class representing a vampire character in a horror-themed game.
 * Inherits from edu.wsu.nova.homework_4_nsmith.model.characters.HorrorCharacter and implements edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable interface.
 * The vampire can transform into a bat, affecting its attack and flee behaviors.
 */
public class Vampire extends HorrorCharacter implements Transformable {
    private boolean isTransformed;

    /** Constructor for characters.Vampire class
     * @param name Name of the vampire
     * @param health Health of the vampire
     * @param vulnerabilities List of vulnerabilities of the vampire
     */
    public Vampire(String name, int health, List<Vulnerability> vulnerabilities){
        super(name, health, vulnerabilities);
        isTransformed = false;
    }

    /**
     * Attack method for characters.Vampire class
     * Prints different attack messages based on transformation state
     */
    @Override
    public void attack(HorrorCharacter target) {
        if(!isTransformed)
            System.out.println("The vampire bites!");
        else
            System.out.println("The bat bites!");
    }

    /**
     * Flee method for characters.Vampire class
     * Prints different flee messages based on transformation state
     */
    @Override
    public void flee(){
        if(!isTransformed)
            System.out.println("The vampire disappears!");
        else
            System.out.println("The bat flies away!");
    }

    /**
     * Transform method for characters.Vampire class
     * Toggles the transformation state of the vampire
     */
    @Override
    public void transform() {
        isTransformed = !isTransformed;
    }

    /**
     * toString method for characters.Vampire class
     * Returns a string representation of the vampire including transformation state
     */
    @Override
    public String toString() {
        if (!isTransformed) {
            return "The vampire is not transformed! \n" + super.toString();
        }
        else {
            return "The vampire transformed into a bat! \n" + super.toString();
        }
    }
}
