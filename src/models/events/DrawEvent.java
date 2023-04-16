package models.events;

import models.enums.Pointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class DrawEvent extends Event {
    public DrawEvent() {
        super(EventTypes.DRAW, Pointers.PD, Pointers.PH);
    }
}
