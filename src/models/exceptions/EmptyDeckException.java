package models.exceptions;

public class EmptyDeckException extends Exception {
    public EmptyDeckException() {
        super("Error: The deck is empty!");
    }
}
