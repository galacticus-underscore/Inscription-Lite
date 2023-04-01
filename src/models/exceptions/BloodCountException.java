package models.exceptions;

public class BloodCountException extends Exception {
    public BloodCountException() {
        super("Error: Player does not have enough blood to summon this card!");
    }
}
