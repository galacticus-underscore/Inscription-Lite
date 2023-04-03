/*
 * SigilEffectsProcessor.java
 * 
 * This processor is in charge of assigning all sigil cards in the game to their
 * sigil effects.
 */

package models.processors;

import models.enums.EffectCodes;
import models.patterns.SigilEffect;

public class SigilEffectsProcessor {
    public static SigilEffect assignEffect(String e) {
        EffectCodes effect_code = EffectCodes.valueOf(e);

        switch (effect_code) {
            default:
                return null;
        }
    }
}
