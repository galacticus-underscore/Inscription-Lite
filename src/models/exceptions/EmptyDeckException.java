package models.exceptions;

public class EmptyDeckException extends Exception {
    public EmptyDeckException() {
        super("Error: Player does not have enough blood to summon this card!");
    }
}
