package models.events;

import models.enums.Pointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class CharDeathEvent extends Event {
    public CharDeathEvent(Pointers killer, Pointers character) {
        super(EventTypes.CHAR_DEATH, killer, character);
    }
}
