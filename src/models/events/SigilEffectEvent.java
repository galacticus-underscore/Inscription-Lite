package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.enums.Sigils;
import models.patterns.Event;

public class SigilEffectEvent extends Event {
    private Sigils effect;
    
    public SigilEffectEvent(EventPointers source, EventPointers target, Sigils e) {
        super(EventTypes.SIGIL_EFFECT, source, target);
        this.effect = e;
    }

    public Sigils getEffect() {
        return this.effect;
    }
}
