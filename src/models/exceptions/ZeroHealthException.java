package models.exceptions;

public class ZeroHealthException extends Exception {
    public ZeroHealthException() {
        super("Error: Player has no more health left!");
    }
}
