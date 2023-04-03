/*
 * Card.java
 * 
 * This model class serves as a template for all sigils that will be used
 * accross the entire session.
 */

package models;

import models.patterns.SigilEffect;
import models.processors.SigilEffectsProcessor;

public class Sigil extends Card {
    private boolean atc, ata;
    private SigilEffect effect;

    public Sigil(String n, String i, int c, boolean atc, boolean ata, String e) {
        super(n, i, c);
        this.atc = atc;
        this.ata = ata;
        this.effect = SigilEffectsProcessor.assignEffect(e);
    }

    public boolean appliesToChars() {
        return this.atc;
    }

    public boolean appliesToAvatars() {
        return this.ata;
    }

    public SigilEffect getEffect() {
        return effect;
    }
}
