package models.exceptions;

public class WrongSlotException extends Exception {
    public WrongSlotException() {
        super("You cannot summon a character in your enemy's slots!");
    }
}
