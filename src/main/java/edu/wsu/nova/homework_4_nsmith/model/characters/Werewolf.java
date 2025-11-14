package edu.wsu.nova.homework_4_nsmith.model.characters;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable;
import edu.wsu.nova.homework_4_nsmith.model.abilities.Vulnerability;
import java.util.List;

/**
 * Class representing a edu.wsu.nova.homework_4_nsmith.model.characters.Werewolf character in a horror-themed game.
 * Inherits from edu.wsu.nova.homework_4_nsmith.model.characters.HorrorCharacter and implements edu.wsu.nova.homework_4_nsmith.model.abilities.Transformable interface.
 * The werewolf can transform between human and werewolf forms,
 * affecting its attack and flee behaviors.
 */

public class Werewolf extends HorrorCharacter implements Transformable {
    private boolean isTransformed;

    /** Constructor for characters.Werewolf class
     * @param name Name of the werewolf
     * @param health Health of the werewolf
     * @param vulnerabilities Array of vulnerabilities of the werewolf
     */
    public Werewolf(String name, int health, List<Vulnerability> vulnerabilities){
        super(name, health, vulnerabilities);
        isTransformed = false;
    }

    /**
     * Attack method for characters.Werewolf class
     * Prints different attack messages based on transformation state
     */
    @Override
    public void attack(HorrorCharacter target) {
        if (!isTransformed)
            System.out.println("The werewolf scratches!");
        else
            System.out.println("The human punches!");
    }

    /**
     * Flee method for characters.Werewolf class
     * Prints different flee messages based on transformation state
     */
    @Override
    public void flee() {
        if (!isTransformed)
            System.out.println("The werewolf runs!");
        else
            System.out.println("The human runs!");
    }

    /**
     * Transform method for characters.Werewolf class
     * Toggles the transformation state of the werewolf
     */
    @Override
    public void transform() {
        isTransformed = !isTransformed;
    }

    /**
     * toString method for characters.Werewolf class
     * Returns a string representation of the werewolf including transformation state
     */
    @Override
    public String toString() {
        if (!isTransformed) {
            return "The werewolf is not transformed! \n" + super.toString();
        }
        else {
            return "The werewolf transformed into a human! \n" + super.toString();
        }
    }
}
