package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;
import models.processors.PointerProcessor;

public class CharSummonEvent extends Event {
    private String card_image;

    public CharSummonEvent(String c, int col) {
        super(EventTypes.CHAR_SUMMON, EventPointers.PA, PointerProcessor.toPointer(col));
        this.card_image = c;
    }

    public String getCardImage() {
        return this.card_image;
    }
}
