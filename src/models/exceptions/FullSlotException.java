package models.exceptions;

public class FullSlotException extends Exception {
    public FullSlotException() {
        super("That slot is taken!");
    }
}
