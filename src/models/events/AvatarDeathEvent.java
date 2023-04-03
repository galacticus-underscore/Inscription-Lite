package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class AvatarDeathEvent extends Event {
    public AvatarDeathEvent(EventPointers killer, EventPointers avatar) {
        super(EventTypes.AVATAR_DEATH, killer, avatar);
    }
}
