package models.patterns;

import java.util.Arrays;

import models.enums.EventTypes;
import models.enums.EffectCodes;

public abstract class Ability {
    private EventTypes[] applies_to;
    private EffectCodes effect_code;

    public Ability(EventTypes[] at, EffectCodes ec) {
        this.applies_to = at;
        this.effect_code = ec;
    }

    public boolean appliesToEvent(EventTypes type) {
        return Arrays.asList(this.applies_to).contains(type);
    }

    public EffectCodes getEffectCode() {
        return this.effect_code;
    }

    public abstract void applyEffect(Event e);
}
