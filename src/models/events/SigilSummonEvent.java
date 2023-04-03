package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class SigilSummonEvent extends Event {
    private String card_image;
    private int blood_change;

    public SigilSummonEvent(EventPointers target, int cost, String image) {
        super(EventTypes.SIGIL_SUMMON, EventPointers.PA, target);
        this.blood_change = -cost;
        this.card_image = image;
    }

    public int getBloodChange() {
        return this.blood_change;
    }

    public String getCardImage() {
        return this.card_image;
    }
}
