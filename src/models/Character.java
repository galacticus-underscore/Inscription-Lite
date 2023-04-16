/*
 * Card.java
 * 
 * This model class serves as a template for all characters that will be used
 * accross the entire session.
 */

package models;

import java.util.ArrayList;

import models.patterns.Sigil;

import models.enums.Pointers;

import models.processors.PointerProcessor;
import models.processors.SigilProcessor;

import models.events.AttackEvent;
import models.events.AvatarDeathEvent;
import models.events.CharDeathEvent;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public class Character extends Card implements Entity {
    private int health, attack;
    private Pointers location;
    private ArrayList<Sigil> sigils = new ArrayList<Sigil>();

    public Character(String n, String i, int c, int h, int a) {
        super(n, i, c);
        this.health = h;
        this.attack = a;
    }

    public Character(String n, String i, int c, int h, int a, String s) {
        super(n, i, c);
        this.health = h;
        this.attack = a;
        this.sigils.add(SigilProcessor.assignSigil(s));
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp, boolean is_attack) throws ZeroHealthException, NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        this.health += hp;

        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("character");
        }
        
        if (is_attack) {
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

    public void setLocation(Pointers pointer) {
        this.location = pointer;
    }

    public ArrayList<Sigil> getSigils() {
        return this.sigils;
    }

    public void addSigil(Sigil a) {
        this.sigils.add(a);
    }

    public void attack() throws NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        Pointers opposite = PointerProcessor.getOppositePointer(this.location);
        Entity target = PointerProcessor.pointerToEntity(opposite);
        App.getSession().addEvent(new AttackEvent(this.location, opposite, this.attack));

        try {
            target.changeHealth(-this.attack, true);
            for (Sigil sigil : this.getSigils()) {
                sigil.activateSigil();
            }
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

    public void counter() throws NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        Pointers opposite = PointerProcessor.getOppositePointer(this.location);
        Entity target = PointerProcessor.pointerToEntity(opposite);
        App.getSession().addEvent(new AttackEvent(this.location, opposite, this.attack));

        try {
            target.changeHealth(-this.attack, false);
            for (Sigil sigil : this.getSigils()) {
                sigil.activateSigil();
            }
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
}
