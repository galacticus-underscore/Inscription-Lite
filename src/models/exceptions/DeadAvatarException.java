package models.exceptions;

public class DeadAvatarException extends Exception {
    public DeadAvatarException() {
        super("You died!");
    }
}

// catch this exception in the game controller
