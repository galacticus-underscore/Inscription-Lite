/*
 * Card.java
 * 
 * This model class serves as a template for all sigils that will be used
 * accross the entire session.
 */

package models;

import models.patterns.SpellEffect;
import models.enums.Pointers;
import models.processors.SpellProcessor;

public class Spell extends Card {
    private boolean atc, ata;
    private SpellEffect effect;

    public Spell(String n, String i, int c, boolean atc, boolean ata, String e) {
        super(n, i, c);
        this.atc = atc;
        this.ata = ata;
        this.effect = SpellProcessor.assignEffect(e);
    }

    public boolean appliesToChars() {
        return this.atc;
    }

    public boolean appliesToAvatars() {
        return this.ata;
    }

    public void applyEffect(Pointers pointer) {
        effect.applyEffect(pointer);
    }
}
