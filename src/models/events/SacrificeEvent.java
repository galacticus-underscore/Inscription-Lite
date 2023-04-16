package models.events;

import models.patterns.Event;
import models.enums.Pointers;
import models.enums.EventTypes;

public class SacrificeEvent extends Event {
    private int blood_change;

    public SacrificeEvent(Pointers pointer, int gain) {
        super(EventTypes.SACRIFICE, pointer, Pointers.PG);
        this.blood_change = gain;
    }

    public int getBloodChange() {
        return this.blood_change;
    }
}
