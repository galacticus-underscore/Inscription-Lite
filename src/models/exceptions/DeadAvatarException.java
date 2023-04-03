package models.exceptions;

public class DeadAvatarException extends Exception {
    public DeadAvatarException() {
        super("Error: An avatar has died!");
    }
}

// catch this exception in the game controller
