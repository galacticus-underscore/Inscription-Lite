/*
 * Card.java
 * 
 * This model class serves as a template for all characters that will be used
 * accross the entire session.
 */

package models;

import java.util.ArrayList;

import models.interfaces.SigilEffect;
import models.events.AttackEvent;
import models.exceptions.NullSessionException;
import models.exceptions.ZeroHealthException;

public class Character extends Card {
    private int health, attack;
    private ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    public Character(int c, String i, int h, int a) {
        super(c, i);
        this.health = h;
        this.attack = a;
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp) throws ZeroHealthException {
        this.health += hp;

        this.effects.forEach((e) -> {
            e.applyEffect();
        });

        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("character");
        }
    }

    public int getAttack() {
        return this.attack;
    }

    public void changeAttack(int a) {
        this.attack += a;

        this.effects.forEach((e) -> {
            e.applyEffect();
        });

        if (this.attack <= 0) {
            this.attack = 0;
        }
    }

    public ArrayList<SigilEffect> getEffects() {
        return effects;
    }

    public void addEffect(SigilEffect s) {
        this.effects.add(s);
    }

    public void attack(int column) throws NullSessionException, ZeroHealthException {
        Avatar playing_avatar = App.getSession().getPlayingAvatar();
        Avatar observing_avatar = App.getSession().getObservingAvatar();
        Character attacking_char = playing_avatar.getCharInSlot(column);
        Character defending_char = playing_avatar.getCharInSlot(column);

        if (observing_avatar.getCharInSlot(column) == null) {
            App.getSession().addEvent(new AttackEvent(column, this.attack));
            observing_avatar.changeHealth(-this.attack);
        }
        else {
            App.getSession().addEvent(new AttackEvent(column, this.attack, defending_char.getAttack()));
            attacking_char.changeHealth(-defending_char.getAttack());
            defending_char.changeHealth(-attacking_char.getAttack());
        }
    }
}
