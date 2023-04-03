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
import java.util.Collections;

import models.patterns.Entity;
import models.patterns.SigilEffect;

import models.enums.EventPointers;

import models.processors.CardDataProcessor;
import models.processors.PointerProcessor;

import models.events.DrawEvent;
import models.events.SacrificeEvent;
import models.events.SigilSummonEvent;
import models.events.CharSummonEvent;

import models.exceptions.ZeroHealthException;
import models.exceptions.BloodCountException;
import models.exceptions.EmptyDeckException;
import models.exceptions.EmptySlotException;
import models.exceptions.FullSlotException;
import models.exceptions.InvalidSummonException;
import models.exceptions.NullSessionException;

public class Avatar implements Entity {
    private int health = 20;
    private int blood_count = 0;
    private ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();
    protected boolean is_highlighted = false;

    private File data;
    private Stack<Card> deck = new Stack<Card>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private Character[] slots = new Character[4];
    private Stack<Card> pile = new Stack<Card>();

    public Avatar(String d) {
        this.data = new File(d);
    }

    public void initialize() throws NullSessionException, EmptyDeckException {
        this.deck = CardDataProcessor.read(this.data);
        
        // Shuffle the cards in the deck
        ArrayList<Card> shuffler = new ArrayList<Card>();

        for (int i = 0; i < 40; i++)
            shuffler.add(this.deck.pop());

        Collections.shuffle(shuffler);

        for (int i = 0; i < 40; i++)
            this.deck.push(shuffler.get(i));

        // Draw 5 cards from the deck
        for (int j = 0; j < 5; j++) {
            this.draw();
        }
    }

    public void changeHealth(int hp) throws ZeroHealthException, NullSessionException {
        this.health += hp;

        this.effects.forEach((e) -> {
            e.applyEffect();
        });

        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("avatar");
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

    public boolean isHighlighted() {
        return this.is_highlighted;
    }

    public void highlight() {
        this.is_highlighted = true;
    }

    public void unhighlight() {
        this.is_highlighted = false;
    }

    public ArrayList<SigilEffect> getEffects() {
        return this.effects;
    }

    public void addEffect(SigilEffect s) {
        this.effects.add(s);
    }

    public Character getCharInSlot(int column) {
        return slots[column];
    }

    public void flip() {
        this.hand.forEach((c) -> {
            c.flip();
        });
    }

    public void draw() throws NullSessionException, EmptyDeckException {
        if (this.deck.empty()) {
            throw new EmptyDeckException();
        }
        
        App.getSession().addEvent(new DrawEvent());
        this.hand.add(this.deck.pop());
    }

    public void summonChar(int hand_pos, int column) throws FullSlotException, BloodCountException, NullSessionException {
        Card summoned = this.hand.get(hand_pos);

        if (this.slots[column] != null) {
            throw new FullSlotException();
        }
        else {
            App.getSession().addEvent(new CharSummonEvent(summoned.getImage(), column));
            this.changeBloodCount(-summoned.getCost());
            this.slots[column] = (Character)summoned;
            this.hand.remove(hand_pos);
        }
    }

    public void summonSigil(int hand_pos, EventPointers t) throws BloodCountException, NullSessionException, InvalidSummonException, EmptySlotException {
        Card summoned = this.hand.get(hand_pos);
        this.changeBloodCount(-summoned.getCost());
        Entity target = PointerProcessor.fromPointer(t);

        switch (t) {
            case PA: 
            case OA:
                if (!((Sigil)summoned).appliesToAvatars()) {
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
                if (!((Sigil)summoned).appliesToChars()) {
                    throw new InvalidSummonException("character");
                }
                else if (target == null) {
                    throw new EmptySlotException();
                }
                else {
                    break;
                }
        }

        App.getSession().addEvent(new SigilSummonEvent(t, summoned.getImage()));
        this.changeBloodCount(-summoned.getCost());
        target.addEffect(((Sigil)summoned).getEffect());
        this.discardSigil(hand_pos);
        this.hand.remove(hand_pos);
    }

    public void discardChar(int column) {
        this.pile.push(this.slots[column]);
        this.slots[column] = null;

        ((Character)(this.pile.peek())).getEffects().forEach((e) -> {
            e.applyEffect();
        });
    }

    public void discardSigil(int hand_pos) {
        this.pile.push(this.hand.get(hand_pos));
        this.hand.remove(hand_pos);
    }

    public void sacrifice(int column) throws BloodCountException, NullSessionException {
        App.getSession().addEvent(new SacrificeEvent(column, this.slots[column].getHealth()));
        this.changeBloodCount(this.slots[column].getHealth());
        this.discardChar(column);
    }

    public void attack() throws NullSessionException, ZeroHealthException {
        for (int i = 0; i < 4; i++) {
            this.slots[i].attack();
        }
    }

    public void reset() {
        this.health = 20;
        this.blood_count = 0;
        this.effects = new ArrayList<SigilEffect>();
        
        this.deck = new Stack<Card>();
        this.hand = new ArrayList<Card>();
        this.slots = new Character[4];
        this.pile = new Stack<Card>();
    }
}
