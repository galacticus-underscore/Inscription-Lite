package models.exceptions;

public class MultipleDrawException extends Exception {
    public MultipleDrawException() {
        super("Error: You cannot draw more than once in a single turn!");
    }
}
