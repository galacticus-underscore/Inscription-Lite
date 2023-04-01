package models;
import java.util.ArrayList;

import models.exceptions.ZeroHealthException;

public class Character extends Card implements SigilAffectable {
    private int health, attack;
    private ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    public Character(int h, int a, int c) {
        super(c);
        this.health = h;
        this.attack = a;
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp) {
        this.health += hp;
        if (this.health < 0)
            this.health = 0;
    }

    public ArrayList<SigilEffect> getEffects() {
        return effects;
    }

    public void addEffect(SigilEffect s) {
        this.effects.add(s);
    }

    public void attack(int column) {
        // if (App.getSession().getObservingAvatar().getCharInSlot(column) == null) {
        //     ;
        // }
        // c.changeHealth(-this.attack);
    }

    public void attack(Avatar a) throws ZeroHealthException {
        a.changeHealth(-this.attack);
    }
}
