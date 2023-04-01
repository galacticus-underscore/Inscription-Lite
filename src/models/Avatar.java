/*
 * Avatar.java
 * 
 * This model class represents the a player in the game. All the actions a
 * player can do is covered here: drawing, playing, discarding, etc. This class
 * is also in charge of reading and writing game data to and from the CSV files
 * that serve as the game's database.
 */

package models;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;

import models.exceptions.ZeroHealthException;
import models.exceptions.BloodCountException;
import models.exceptions.EmptySlotException;
import models.exceptions.FullSlotException;

public class Avatar implements SigilAffectable {
    private int health = 20;
    private int blood_count = 0;
    private ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    private String data;
    private Stack<Card> deck = new Stack<Card>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private Character[] slots = new Character[4];
    private Stack<Card> pile = new Stack<Card>();

    // placeholder variables
    private ArrayList<Card> shuffler = new ArrayList<Card>();
    private Card summoned;

    public Avatar(String d) {
        this.data = d;
    }

    public void initialize() {
        // TODO: Read file referenced by this.data
        // TODO: Assign values stored in file to their respective fields
        
        // shuffle the cards in the deck
        for (int i = 0; i < 40; i++)
            shuffler.add(deck.pop());

        Collections.shuffle(shuffler);

        for (int i = 0; i < 40; i++)
            deck.push(shuffler.get(i));

        shuffler = new ArrayList<Card>();

        // draw 5 cards from the deck
        for (int j = 0; j < 5; j++) {
            this.draw();
        }
    }

    public void changeHealth(int hp) throws ZeroHealthException {
        this.health += hp;

        this.effects.forEach((e) -> {
            e.applyEffect();
        });

        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException();
        }

        //for concedes: use this method with hp = -1000
    }

    public void changeBloodCount(int b) throws BloodCountException {
        this.blood_count += b;
        if (this.blood_count < 0) {
            this.health -= b;
            throw new BloodCountException();
        }
    }

    public void addEffect(SigilEffect s) {
        this.effects.add(s);
    }

    public Card getCharInSlot(int slot_pos) {
        return slots[slot_pos];
    }

    public void draw() {
        this.hand.add(this.deck.pop());
    }

    public void summonChar(int hand_pos, int slot_pos) throws FullSlotException, BloodCountException {
        summoned = this.hand.get(hand_pos);

        if (this.slots[slot_pos] != null) {
            throw new FullSlotException();
        }
        else {
            this.changeBloodCount(summoned.getCost() * -1);
            this.slots[slot_pos] = summoned;
            this.hand.remove(hand_pos);
        }        
    }

    public void summonSigil(int hand_pos, int slot_pos) throws EmptySlotException, BloodCountException {
        summoned = this.hand.get(hand_pos);

        if (this.slots[slot_pos] == null) {
            throw new EmptySlotException();
        }
        else {
            this.changeBloodCount(summoned.getCost() * -1);
            this.getCharInSlot(slot_pos).addEffect(summoned.getEffect());
            this.discardSigil(hand_pos);
            this.hand.remove(hand_pos);
        }
    }

    public void discardChar(int slot_pos) {
        this.pile.push(this.slots[slot_pos]);
        this.slots[slot_pos] = null;
    }

    public void discardSigil(int hand_pos) {
        this.pile.push(this.hand.get(hand_pos));
        this.hand.remove(hand_pos);
    }

    public void sacrifice(int slot_pos) throws BloodCountException {
        this.changeBloodCount(this.slots[slot_pos].getHealth());
        this.discardChar(slot_pos);
    }

    public void attack() {
        for (int i = 0; i < 4; i++) {
            this.slots[i].attack(i);
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

    public void saveData() {
        // TODO: Write avatar data to file referenced by this.data
    }
}
