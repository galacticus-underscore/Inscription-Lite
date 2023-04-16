/*
 * SigilEffectsProcessor.java
 * 
 * This processor is in charge of assigning all sigil cards in the game to their
 * sigil effects.
 */

package models.processors;

import models.enums.SigilCodes;
import models.patterns.SpellEffect;

public class SpellProcessor {
    public static SpellEffect assignEffect(String e) {
        SigilCodes effect_code = SigilCodes.valueOf(e);

        switch (effect_code) {
            default:
                return null;
        }
    }
}
