/*
 * Card.java
 * 
 * This model class serves as a template for all characters that will be used
 * accross the entire session.
 */

package models;

import java.util.ArrayList;

import models.patterns.Entity;
import models.patterns.SigilEffect;

import models.enums.EventPointers;

import models.processors.PointerProcessor;

import models.events.AttackEvent;
import models.events.AvatarDeathEvent;
import models.events.CharDeathEvent;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.ZeroHealthException;

public class Character extends Card implements Entity {
    private int health, attack;
    private EventPointers location;
    private ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    public Character(String n, String i, int c, int h, int a) {
        super(n, i, c);
        this.health = h;
        this.attack = a;
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp, boolean is_counter) throws ZeroHealthException, NullSessionException, DeadAvatarException, DeadCharacterException {
        this.health += hp;

        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("character");
        }
        
        if (!is_counter) {
            this.counter();
        }
    }

    public int getAttack() {
        return this.attack;
    }

    public void changeAttack(int a) {
        this.attack += a;

        if (this.attack <= 0) {
            this.attack = 0;
        }
    }

    public void setLocation(int l) {
        this.location = PointerProcessor.toPointer(l);
    }

    public ArrayList<SigilEffect> getEffects() {
        return this.effects;
    }

    public void addEffect(SigilEffect s) {
        this.effects.add(s);
    }

    public void attack() throws NullSessionException, ZeroHealthException, DeadAvatarException, DeadCharacterException {
        EventPointers opposite = PointerProcessor.getOpposite(this.location);
        Entity target = PointerProcessor.fromPointer(opposite);
        App.getSession().addEvent(new AttackEvent(this.location, opposite, this.attack));

        try {
            target.changeHealth(-this.attack, false);
        }
        catch (ZeroHealthException e) {
            if (target instanceof Avatar) {
                App.getSession().addEvent(new AvatarDeathEvent(this.location, opposite));
            }
            else if (target instanceof Character) {
                App.getSession().addEvent(new CharDeathEvent(this.location, opposite));
            }
        }
    }

    public void counter() throws NullSessionException, ZeroHealthException, DeadAvatarException, DeadCharacterException {
        EventPointers opposite = PointerProcessor.getOpposite(this.location);
        Entity target = PointerProcessor.fromPointer(opposite);
        App.getSession().addEvent(new AttackEvent(this.location, opposite, this.attack));
        target.changeHealth(-this.attack, true);
    }
}
