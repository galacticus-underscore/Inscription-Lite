package models.events;

import models.enums.EventTypes;
import models.patterns.Event;

public class AvatarDeathEvent implements Event {
    public EventTypes getType() {
        return EventTypes.AVATAR_DEATH;
    }
}
