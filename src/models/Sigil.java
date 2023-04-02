/*
 * Card.java
 * 
 * This model class serves as a template for all sigils that will be used
 * accross the entire session.
 */

package models;

import java.util.ArrayList;

import models.interfaces.SigilEffect;

public class Sigil extends Card {
    private boolean atc, ata;
    private SigilEffect effect;
    private static ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    public Sigil(int c, String i, boolean atc, boolean ata, int e) {
        super(c, i);
        this.atc = atc;
        this.ata = ata;
        this.effect = effects.get(e);
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

    public static void addEffect(SigilEffect s) {
        effects.add(s);
    }
}
