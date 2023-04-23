/*
 * Card.java
 * 
 * This model class serves as a template for all characters that will be used
 * accross the entire session.
 */

package models;

import java.util.ArrayList;

import models.enums.Pointers;
import models.enums.SigilCodes;

import models.processors.PointerProcessor;

import models.events.AttackEvent;
import models.events.AvatarDeathEvent;
import models.events.CharDeathEvent;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public class Character extends Card implements Entity {
    private int health, attack;
    private ArrayList<SigilCodes> sigils = new ArrayList<SigilCodes>();

    private int def_health, def_attack;
    private ArrayList<SigilCodes> def_sigils = new ArrayList<SigilCodes>();

    public Character(String n, String cn, String i, int c, int h, int a) {
        super(n, cn, i, c);
        this.health = h;
        this.attack = a;

        this.def_health = h;
        this.def_attack = a;
    }

    public Character(String n, String cn, String i, int c, int h, int a, String s) {
        super(n, cn, i, c);
        this.health = h;
        this.attack = a;
        this.sigils.add(SigilCodes.valueOf(s));

        this.def_health = h;
        this.def_attack = a;
        this.def_sigils.add(SigilCodes.valueOf(s));
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp, Pointers source) throws ZeroHealthException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        Pointers target = PointerProcessor.entityToPointer(this);
        Entity source_ety = PointerProcessor.pointerToEntity(source);
        Pointers target_avatar_ptr = PointerProcessor.getAvatarOfPointer(target);
        Avatar target_avatar_ety = (Avatar)PointerProcessor.pointerToEntity(target_avatar_ptr);

        if (source_ety.hasSigil(SigilCodes.TOUCH_OF_DEATH))
            this.health += -1000;
        else
            this.health += hp;
        
        if (source_ety.hasSigil(SigilCodes.SNIPER) && !this.hasSigil(SigilCodes.STONE_OCEAN))
            target_avatar_ety.changeHealth(hp);
        
        App.getSession().addEvent(new AttackEvent(source, target, -hp));
        
        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("character");
        }

        if (this.hasSigil(SigilCodes.SHARP_QUILLS)) {
            source_ety.changeHealth(-1);
        }
    }

    public void changeHealth(int hp) throws ZeroHealthException {
        this.health += hp;

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

        if (this.attack <= 0) 
            this.attack = 0;
    }

    public ArrayList<SigilCodes> getSigils() {
        return this.sigils;
    }

    public boolean hasSigil(SigilCodes c) {
        return this.hasSigil(c);
    }

    public void addSigil(SigilCodes s) {
        this.sigils.add(s);
    }

    public void attack() throws DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException {
        Pointers opposite = PointerProcessor.getOppositePointer(PointerProcessor.entityToPointer(this));
        Entity target = PointerProcessor.pointerToEntity(opposite);

        try {
            target.changeHealth(-this.attack, PointerProcessor.entityToPointer(this));
        }
        catch (ZeroHealthException e) {
            if (target instanceof Avatar)
                App.getSession().addEvent(new AvatarDeathEvent(PointerProcessor.entityToPointer(this), opposite));
            else if (target instanceof Character)
                App.getSession().addEvent(new CharDeathEvent(PointerProcessor.entityToPointer(this), opposite));
        }
    }

    public void reset() {
        this.health = this.def_health;
        this.attack = this.def_attack;
        this.sigils = this.def_sigils;
    }
}
