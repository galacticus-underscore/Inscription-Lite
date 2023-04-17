package models.events;

import models.patterns.Event;
import models.enums.Pointers;
import models.enums.EventTypes;

public class SacrificeEvent extends Event {
    public SacrificeEvent(Pointers pointer) {
        super(EventTypes.SACRIFICE, pointer, Pointers.PG);
    }
}
