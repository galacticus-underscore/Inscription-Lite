package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;
import models.processors.PointerProcessor;

public class SacrificeEvent extends Event {
    private int blood_change;

    public SacrificeEvent(int col, int gain) {
        super(EventTypes.SACRIFICE, PointerProcessor.toPointer(col), EventPointers.PA);
        this.blood_change = gain;
    }

    public int getBloodChange() {
        return this.blood_change;
    }
}
