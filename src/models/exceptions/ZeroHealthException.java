package models.exceptions;

public class ZeroHealthException extends Exception {
    private char type;

    public ZeroHealthException(String t) {
        super("Error: " + t + " has no more health left!");
        this.type = t.charAt(0);
    }

    public char getType() {
        return this.type;
    }
}
