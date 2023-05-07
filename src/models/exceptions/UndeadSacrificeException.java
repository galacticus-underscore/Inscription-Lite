package models.exceptions;

public class UndeadSacrificeException extends Exception {
    public UndeadSacrificeException() {
        super("You cannot sacrifice this character since it is undead!");
    }
}
