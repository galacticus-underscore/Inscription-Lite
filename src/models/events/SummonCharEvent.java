package models.events;

import models.interfaces.Event;
import models.enums.EventTypes;

public class SummonCharEvent implements Event {
    private String card_image;
    private int column;

    public SummonCharEvent(String c, int col) {
        this.card_image = c;
        this.column = col;
    }

    public EventTypes getType() {
        return EventTypes.SUMMON_CHAR;
    }

    public String getCardImage() {
        return this.card_image;
    }

    public int getColumn() {
        return this.column;
    }
}
