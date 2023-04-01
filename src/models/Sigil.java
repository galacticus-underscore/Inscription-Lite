package models;

import java.util.ArrayList;

public class Sigil extends Card {
    private boolean atc, ata;
    private SigilEffect effect;
    private static ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    public Sigil(int c, boolean atc, boolean ata, int e) {
        super(c);
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
