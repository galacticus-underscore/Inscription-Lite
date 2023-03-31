package models;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

public class Avatar implements SigilAffectable {
    private int health = 20;
    private int blood_count = 0;
    private ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

    private String card_data_path;
    private Stack<Card> deck = new Stack<Card>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    private Card[] slots = new Card[4];
    private Stack<Card> pile = new Stack<Card>();

    private ArrayList<Card> shuffler = new ArrayList<Card>();
    private Card summoned;

    public Avatar(String p) {
        this.card_data_path = p;
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

    public Card getCardInSlot(int slot_pos) {
        return slots[slot_pos];
    }

    public void readCardData() {
        System.out.println("Rendered card data from " + this.card_data_path);
    }

    public void shuffle() {
        for (int i = 0; i < 40; i++)
            shuffler.add(deck.pop());

        Collections.shuffle(shuffler);

        for (int i = 0; i < 40; i++)
            deck.push(shuffler.get(i));

        shuffler = new ArrayList<Card>();
    }

    public void draw() {
        this.hand.add(this.deck.pop());
    }

    public void summonChar(int hand_pos, int slot_pos) throws BloodCountException {
        summoned = this.hand.get(hand_pos);
        if (summoned.getCost() > this.blood_count) {
            throw new BloodCountException();
        }
        else {
            this.slots[slot_pos] = summoned;
            this.hand.remove(hand_pos);
            this.blood_count -= summoned.getCost();   
        }
    }

    public void summonSigil(int hand_pos, int slot_pos) throws BloodCountException {
        summoned = this.hand.get(hand_pos);
        if (summoned.getCost() > this.blood_count) {
            throw new BloodCountException();
        }
        else {
            this.slots[slot_pos] = summoned;
            this.hand.remove(hand_pos);
            this.blood_count -= summoned.getCost();
        }
    }

    public void discard(int slot_pos) {
        this.pile.push(this.slots[slot_pos]);
        this.slots[slot_pos] = null;
    }

    public void sacrifice(int slot_pos) {
        this.discard(slot_pos);
        this.blood_count += 1;
    }

    public void concede() {
        this.health = 0;
    }

    public void reset() {
        health = 20;
        blood_count = 0;
        effects = new ArrayList<SigilEffect>();
        
        deck = new Stack<Card>();
        hand = new ArrayList<Card>();
        slots = new Card[5];
        pile = new Stack<Card>();
    }
}
