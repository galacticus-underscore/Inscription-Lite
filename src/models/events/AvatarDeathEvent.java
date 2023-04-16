package models.events;

import models.enums.Pointers;
import models.enums.EventTypes;
import models.patterns.Event;

public class AvatarDeathEvent extends Event {
    public AvatarDeathEvent(Pointers killer, Pointers avatar) {
        super(EventTypes.AVATAR_DEATH, killer, avatar);
    }
}
