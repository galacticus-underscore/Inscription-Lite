package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class CounterEvent extends Event {
    private int health_change;

    public CounterEvent(EventPointers s, EventPointers t, int d) {
        super(EventTypes.COUNTER, s, t);
        this.health_change = -d;
    }

    public int getHealthChange() {
        return this.health_change;
    }
}
