package models.events;

import models.enums.EventTypes;
import models.interfaces.Event;

public class SacrificeEvent implements Event {
    private int column, blood_gained;

    public SacrificeEvent(int col, int gain) {
        this.column = col;
        this.blood_gained = gain;
    }

    public EventTypes getType() {
        return EventTypes.SACRIFICE;
    }

    public int getColumn() {
        return this.column;
    }

    public int getBloodGained() {
        return this.blood_gained;
    }
}
