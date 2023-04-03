package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class AttackEvent extends Event {
    private int damage;

    public AttackEvent(EventPointers s, EventPointers t, int d) {
        super(EventTypes.ATTACK, s, t);
        this.damage = d;
    }

    public int getDamage() {
        return this.damage;
    }
}
