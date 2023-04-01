package models.exceptions;

public class FullSlotException extends Exception {
    public FullSlotException() {
        super("Error: Player cannot summon a character here as the slot is taken!");
    }
}
