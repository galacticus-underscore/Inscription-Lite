package models.events;

import models.interfaces.Event;
import models.enums.EventTypes;

public class SummonSigilEvent implements Event {
    private String card_image;
    private int column;
    private char avatar;
    private EventTypes type;

    public SummonSigilEvent(String c, int col) {
        this.card_image = c;
        this.column = col;
        this.type = EventTypes.SUMMON_SIGIL_CHAR;
    }

    public SummonSigilEvent(String c, char a) {
        this.card_image = c;
        this.avatar = a;
        this.type = EventTypes.SUMMON_SIGIL_AVATAR;
    }

    public EventTypes getType() {
        return this.type;
    }

    public String getCardImage() {
        return this.card_image;
    }

    public int getColumn() {
        return this.column;
    }

    public char getAvatar() {
        return this.avatar;
    }
}
