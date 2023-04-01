package models.exceptions;

public class InvalidSummonException extends Exception {
    public InvalidSummonException(String applied_to) {
        super("Error: This sigil does not apply to " + applied_to + "s!");
    }
}
