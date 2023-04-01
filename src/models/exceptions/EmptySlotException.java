package models.exceptions;

public class EmptySlotException extends Exception {
    public EmptySlotException() {
        super("Error: Player cannot summon a sigil here as the slot is empty!");
    }
}
