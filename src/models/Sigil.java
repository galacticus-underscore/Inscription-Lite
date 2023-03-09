package models;
import java.util.ArrayList;

public class Sigil {
    private boolean applies_to_chars, applies_to_avatars;
    private SigilEffect effect;

    public Sigil(boolean atc, boolean atv, SigilEffect e) {
        applies_to_avatars = atc;
        applies_to_avatars = atv;
        effect = e;
    }

    public boolean appliesToChars() {
        return applies_to_chars;
    }

    public boolean appliesToAvatars() {
        return applies_to_avatars;
    }

    public SigilEffect getEffect() {
        return effect;
    }

    // Testing code
    public static void main(String[] args) throws Exception {
        ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

        SigilEffect test = new SigilEffect() {
            String output = "test successful";
            public void applyEffect() {
                System.out.println(output);
            }
        };

        effects.add(test);

        effects.get(0).applyEffect();
    }
}
