package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class SigilSummonEvent extends Event {
    private String card_image;

    public SigilSummonEvent(EventPointers target, String c) {
        super(EventTypes.SIGIL_SUMMON, EventPointers.PA, target);
        this.card_image = c;
    }

    public String getCardImage() {
        return this.card_image;
    }
}
