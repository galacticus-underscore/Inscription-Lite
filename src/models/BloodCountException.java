package models;
public class BloodCountException extends Exception {
    public BloodCountException() {
        super("You do not have enough blood to summon this card");
    }
}
