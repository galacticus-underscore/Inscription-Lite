package models.events;

import models.enums.Pointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class AttackEvent extends Event {
    private int health_change;

    public AttackEvent(Pointers s, Pointers t, int d) {
        super(EventTypes.ATTACK, s, t);
        this.health_change = -d;
    }

    
    /** 
     * @return int
     */
    public int getHealthChange() {
        return this.health_change;
    }
}
