package models.processors;

import models.patterns.Sigil;

import models.enums.SigilCodes;

import models.sigils.Sniper;

public class SigilProcessor {
    public static Sigil assignSigil(String s) {
        SigilCodes code = SigilCodes.valueOf(s);

        switch (code) {
            case SNIPER:
                return new Sniper();
            default:
                return null;
        }
    }
}
