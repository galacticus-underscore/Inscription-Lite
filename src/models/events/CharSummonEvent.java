package models.events;

import models.enums.Pointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class CharSummonEvent extends Event {
    private int blood_change;
    private String card_image;

    public CharSummonEvent(Pointers target, int cost, String image) {
        super(EventTypes.CHAR_SUMMON, Pointers.PA, target);
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
