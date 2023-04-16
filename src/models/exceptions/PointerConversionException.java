package models.exceptions;

public class PointerConversionException extends Exception {
    public PointerConversionException(String reason) {
        super("Error: Cannot perform pointer conversion because " + reason + "!");
    }
}
