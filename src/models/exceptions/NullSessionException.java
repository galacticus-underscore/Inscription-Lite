package models.exceptions;

public class NullSessionException extends Exception {
    public NullSessionException() {
        super("Error: No session currently exists as no game is being played!");
    }
}
