package models.events;

import models.interfaces.Event;
import models.enums.EventTypes;

public class DrawEvent implements Event {
    public EventTypes getType() {
        return EventTypes.DRAW;
    }
}
