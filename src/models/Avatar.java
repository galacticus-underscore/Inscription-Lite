/*
 * Avatar.java
 * 
 * This model class represents the a player in the game. All the actions a
 * player can do is covered here: drawing, playing, discarding, etc. This class
 * is also in charge of reading and writing session data to and from the CSV
 * files that serve as the session's database.
 */

package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import models.patterns.Sigil;
import models.enums.Pointers;

import models.processors.CardDataProcessor;
import models.processors.PointerProcessor;
import models.events.DrawEvent;
import models.events.SacrificeEvent;
import models.events.SpellSummonEvent;
import models.events.CharSummonEvent;

import models.exceptions.ZeroHealthException;
import models.exceptions.BloodCountException;
import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.EmptyDeckException;
import models.exceptions.FullSlotException;
import models.exceptions.InvalidSummonException;
import models.exceptions.NullSessionException;
import models.exceptions.PointerConversionException;

public class Avatar implements Entity {
    private int health = 20;
    private int blood_count = 0;
    private ArrayList<Sigil> sigils = new ArrayList<Sigil>();

    private File data;
    private Stack<Card> deck = new Stack<Card>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private Character[] slots = new Character[4];
    private Stack<Card> pile = new Stack<Card>();

    public Avatar(String d) {
        this.data = new File(d);
    }

    public void initialize() {
        this.deck = CardDataProcessor.read(this.data);

        for (int i = 0; i < 5; i++) {
            this.hand.add(this.deck.pop());
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp, boolean is_attack) throws NullSessionException, ZeroHealthException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        this.health += hp;

        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("avatar");
        }

        if (is_attack) {
            this.counter();
        }

        // for concedes: use this method with hp = -1000
    }

    public void changeBloodCount(int b) throws BloodCountException {
        this.blood_count += b;
        if (this.blood_count < 0) {
            this.health -= b;
            throw new BloodCountException();
        }
    }

    public ArrayList<Sigil> getSigils() {
        return this.sigils;
    }

    public void addSigil(Sigil a) {
        this.sigils.add(a);
    }

    public Character getCharInSlot(int column) {
        return slots[column];
    }

    public void flip() {
        this.hand.forEach((c) -> {
            c.flip();
        });
    }

    public void draw() throws NullSessionException, EmptyDeckException, DeadAvatarException, DeadCharacterException {
        if (this.deck.empty()) {
            throw new EmptyDeckException();
        }

        this.hand.add(this.deck.pop());
        App.getSession().addEvent(new DrawEvent());
    }

    public void summon(int source, Pointers target) throws FullSlotException, BloodCountException, DeadCharacterException, DeadAvatarException, NullSessionException, InvalidSummonException {
        Card summoned = this.hand.get(source);
        String card_type = summoned.getClass().getSimpleName();

        if (card_type.equals("Character")) {
            if (this.slots[PointerProcessor.pointerToInt(target)] != null) {
                throw new FullSlotException();
            }

            this.changeBloodCount(-summoned.getCost());
            this.slots[PointerProcessor.pointerToInt(target)] = (Character)summoned;
            ((Character) summoned).setLocation(target);
            this.hand.remove(source);
            App.getSession().addEvent(new CharSummonEvent(target, summoned.getCost(), summoned.getImage()));
        }
        else {
            this.changeBloodCount(-summoned.getCost());

            switch (target) {
                case PA: 
                case OA:
                    if (!((Spell)summoned).appliesToAvatars()) {
                        throw new InvalidSummonException("avatar");
                    }
                    else {
                        break;
                    }
                case P1:
                case P2:
                case P3:
                case P4:
                case O1:
                case O2:
                case O3:
                case O4:
                    if (!((Spell)summoned).appliesToChars()) {
                        throw new InvalidSummonException("character");
                    } else {
                        break;
                    }
                default:
                    throw new InvalidSummonException("non-entity");
            }

            this.changeBloodCount(-summoned.getCost());
            ((Spell)summoned).applyEffect(target);
            this.discardSpell(source);
            App.getSession().addEvent(new SpellSummonEvent(target, summoned.getCost(), summoned.getImage()));
        }
    }

    public void discardChar(Pointers pointer) {
        int column = PointerProcessor.pointerToInt(pointer); 
        this.pile.push(this.slots[column]);
        this.slots[column] = null;
    }

    public void discardSpell(int hand_pos) {
        this.pile.push(this.hand.get(hand_pos));
        this.hand.remove(hand_pos);
    }

    public void sacrifice(Pointers pointer) throws BloodCountException, NullSessionException, DeadAvatarException, DeadCharacterException {
        int column = PointerProcessor.pointerToInt(pointer); 
        this.changeBloodCount(this.slots[column].getHealth());
        this.discardChar(pointer);
        App.getSession().addEvent(new SacrificeEvent(pointer, this.slots[column].getHealth()));
    }

    public void attack() throws NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        for (int i = 0; i < 4; i++) {
            this.slots[i].attack();
        }
    }

    public void counter() throws NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException {
        for (Sigil sigil : this.getSigils()) {
            sigil.activateSigil();
        }  
    }

    public void reset() {
        this.health = 20;
        this.blood_count = 0;
        this.sigils = new ArrayList<Sigil>();
        
        this.deck = new Stack<Card>();
        this.hand = new ArrayList<Card>();
        this.slots = new Character[4];
        this.pile = new Stack<Card>();
    }
}
