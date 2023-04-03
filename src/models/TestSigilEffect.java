package models;

import models.enums.EventTypes;
import models.enums.Sigils;
import models.patterns.SigilEffect;

public class TestSigilEffect implements SigilEffect {
    private String output = "test successful";

    public boolean applyEffect(EventTypes e) {
        System.out.println(output);
        return true;
    }

    public Sigils getEffectType() {
        return Sigils.DRAW;
    }
}
