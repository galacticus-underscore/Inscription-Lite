package models.events;

import models.interfaces.Event;
import models.enums.EventTypes;

public class SigilEffectEvent implements Event {
    private char avatar;
    private int column;

    public SigilEffectEvent(char a, int col) {
        this.avatar = a;
        this.column = col;
    }

    public EventTypes getType() {
        return EventTypes.SIGIL_EFFECT;
    }

    public char getAvatar() {
        return this.avatar;
    }

    public int getColumn() {
        return this.column;
    }
}
