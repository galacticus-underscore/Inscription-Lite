package models.exceptions;

public class MultipleDrawException extends Exception {
    public MultipleDrawException() {
        super("You cannot draw more than once in a single turn!");
    }
}
