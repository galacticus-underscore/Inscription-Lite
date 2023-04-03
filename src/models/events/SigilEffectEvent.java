package models.events;

import models.enums.EventPointers;
import models.enums.EventTypes;
import models.enums.EffectCodes;
import models.patterns.Event;

public class SigilEffectEvent extends Event {
    private EffectCodes effect;
    
    public SigilEffectEvent(EventPointers source, EventPointers target, EffectCodes e) {
        super(EventTypes.SIGIL_EFFECT, source, target);
        this.effect = e;
    }

    public EffectCodes getEffect() {
        return this.effect;
    }
}
