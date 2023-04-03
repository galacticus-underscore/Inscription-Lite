package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;
import models.processors.PointerProcessor;

public class CharSummonEvent extends Event {
    private int blood_change;
    private String card_image;

    public CharSummonEvent(int col, int cost, String image) {
        super(EventTypes.CHAR_SUMMON, EventPointers.PA, PointerProcessor.toPointer(col));
        this.card_image = image;
        this.blood_change = -cost;
    }

    public int getBloodChange() {
        return this.blood_change;
    }

    public String getCardImage() {
        return this.card_image;
    }
}
