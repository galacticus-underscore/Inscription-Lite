package models.events;

import models.interfaces.Event;
import models.enums.EventTypes;

public class AttackEvent implements Event {
    private int column, attack, counter;

    public AttackEvent(int col, int a) {
        this.column = col;
        this.attack = a;
    }

    public AttackEvent(int col, int a, int c) {
        this.column = col;
        this.attack = a;
        this.counter = c;
    }

    public EventTypes getType() {
        return EventTypes.ATTACK;
    }

    public int getColumn() {
        return this.column;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getCounter() {
        return this.counter;
    }
}
