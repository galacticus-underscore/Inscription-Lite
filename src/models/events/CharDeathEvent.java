package models.events;

import models.enums.EventTypes;
import models.patterns.Event;

public class CharDeathEvent implements Event {
    private int column;

    public CharDeathEvent(int col) {
        this.column = col;
    }

    public EventTypes getType() {
        return EventTypes.CHAR_DEATH;
    }

    public int getColumn() {
        return this.column;
    }
}
