package models.events;

import models.enums.EventTypes;
import models.interfaces.Event;

public class AvatarDeathEvent implements Event {
    public EventTypes getType() {
        return EventTypes.AVATAR_DEATH;
    }
}
