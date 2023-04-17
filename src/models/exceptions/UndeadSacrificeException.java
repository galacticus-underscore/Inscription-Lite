package models.exceptions;

public class UndeadSacrificeException extends Exception {
    public UndeadSacrificeException() {
        super("Error: You cannot sacrifice this character since it is undead!");
    }
}
