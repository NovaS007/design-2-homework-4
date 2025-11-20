package edu.wsu.nova.homework_4_nsmith.model.abilities;

/** Interface representing a character that can transform
 */
public interface Transformable {

    /** Method to transform the character
     */
    // default means implementing classes don't have to override it and allows for a common implementation
     default void transform(){
        setTransformed(true);
    }

    /** Method to revert the character back to original form
     */
    default void revertTransform(){
        setTransformed(false);
    }

    /** Getter for transformed state
     * @return true if transformed, false otherwise
     */
    boolean getTransformed();

    /** Setter for transformed state
     * @param transformed true to set as transformed, false otherwise
     */
    void setTransformed(boolean transformed);
}