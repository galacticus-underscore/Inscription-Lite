package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class DrawEvent extends Event {
    public DrawEvent() {
        super(EventTypes.DRAW, EventPointers.PA, EventPointers.PA);
    }
}
