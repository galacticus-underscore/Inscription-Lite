package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class CharDeathEvent extends Event {
    public CharDeathEvent(EventPointers killer, EventPointers character) {
        super(EventTypes.AVATAR_DEATH, killer, character);
    }
}
