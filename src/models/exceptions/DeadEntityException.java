package models.exceptions;

import models.enums.Pointers;

public class DeadEntityException extends Exception {
    private Pointers location;

    public DeadEntityException(Pointers loc) {
        super("Error: The entity at location " + loc.name() + " has died!");
        this.location = loc;
    }

    public Pointers getLocation() {
        return this.location;
    }
}
