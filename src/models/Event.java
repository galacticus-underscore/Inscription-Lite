package models;

import models.enums.EventType;

public abstract class Event {
    protected EventType type;
    protected Avatar cause;
    
    public Event(EventType t, Avatar a) {
        this.type = t;
        this.cause = a;
    }

    public EventType getType() {
        return this.type;
    }

    public Avatar getCause() {
        return this.cause;
    }
}
