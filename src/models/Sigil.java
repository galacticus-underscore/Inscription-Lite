package models;

import java.util.ArrayList;

public class Sigil {
    private boolean atc, ata;
    private SigilEffect effect;
    private static ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    public Sigil(boolean atc, boolean ata, int e) {
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

    // Testing code
    public static void main(String[] args) throws Exception {
        ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

        effects.add(new SigilEffect() {
            String output = "test successful";
            public void applyEffect() {
                System.out.println(output);
            }
        });

        effects.get(0).applyEffect();
    }
}
