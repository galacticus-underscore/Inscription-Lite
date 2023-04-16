package models.events;

import models.enums.Pointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class SpellSummonEvent extends Event {
    private String card_image;
    private int blood_change;

    public SpellSummonEvent(Pointers target, int cost, String image) {
        super(EventTypes.SIGIL_SUMMON, Pointers.PA, target);
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
