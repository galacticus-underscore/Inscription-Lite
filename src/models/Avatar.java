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

import models.enums.Pointers;
import models.enums.SigilCodes;

import models.processors.CardDataProcessor;
import models.processors.PointerProcessor;

import models.events.DrawEvent;
import models.events.SacrificeEvent;
import models.events.CharSummonEvent;
import models.events.SpellSummonEvent;
import models.events.AttackEvent;

import models.exceptions.ZeroHealthException;
import models.exceptions.BloodCountException;
import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.EmptyDeckException;
import models.exceptions.FullSlotException;
import models.exceptions.InvalidSummonException;
import models.exceptions.MultipleDrawException;
import models.exceptions.PointerConversionException;
import models.exceptions.UndeadSacrificeException;

public class Avatar implements Entity {
    private int health = 20;
    private int blood_count = 5;
    private ArrayList<SigilCodes> sigils = new ArrayList<SigilCodes>();
    private boolean has_drawn = false;

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
        for (int i = 0; i < 5; i++)
            this.hand.add(this.deck.pop());
    }

    public int getHealth() {
        return this.health;
    }

    public void changeHealth(int hp, Pointers source) throws ZeroHealthException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        this.health += hp;
        App.getSession().addEvent(new AttackEvent(source, PointerProcessor.entityToPointer(this), -hp));
        
        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("avatar");
        }
        // for concedes: use this method with hp = -1000
    }

    public void changeHealth(int hp) throws ZeroHealthException {
        this.health += hp;
        
        if (this.health <= 0) {
            this.health = 0;
            throw new ZeroHealthException("avatar");
        }
    }

    public void changeBloodCount(int b) throws BloodCountException {
        this.blood_count += b;
        if (this.blood_count < 0) {
            this.health -= b;
            throw new BloodCountException();
        }
    }

    public ArrayList<SigilCodes> getSigils() {
        return this.sigils;
    }

    public boolean hasSigil(SigilCodes c) {
        return this.sigils.contains(c);
    }

    public void allowDraw() {
        this.has_drawn = false;
    }

    public void addSigil(SigilCodes c) {
        this.sigils.add(c);
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public Character getCharInSlot(int column) {
        return slots[column];
    }

    public void flip() {
        this.hand.forEach((c) -> {
            c.flip();
        });
    }

    public Card draw() throws EmptyDeckException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException, MultipleDrawException {
        if (this.deck.empty())
            throw new EmptyDeckException();
        else if (this.has_drawn)
            throw new MultipleDrawException();

        Card out = this.deck.pop();
        this.hand.add(out);
        this.has_drawn = true;
        App.getSession().addEvent(new DrawEvent());
        return out;
    }

    public Card summon(int source, Pointers target) throws FullSlotException, BloodCountException, DeadCharacterException, DeadAvatarException, InvalidSummonException, PointerConversionException, ZeroHealthException {
        Card summoned = this.hand.get(source);

        if (summoned instanceof Character) {
            if (this.slots[PointerProcessor.pointerToInt(target) - 1] != null)
                throw new FullSlotException();

            this.changeBloodCount(-summoned.getCost());
            this.slots[PointerProcessor.pointerToInt(target) - 1] = (Character)summoned;
            this.hand.remove(source);
            App.getSession().addEvent(new CharSummonEvent(target, summoned.getCost(), summoned.getImage()));
        }
        else {
            this.changeBloodCount(-summoned.getCost());

            switch (target) {
                case PA: 
                case OA:
                    if (!((Spell)summoned).appliesToAvatars())
                        throw new InvalidSummonException("avatar");
                    else
                        break;
                case P1:
                case P2:
                case P3:
                case P4:
                case O1:
                case O2:
                case O3:
                case O4:
                    if (!((Spell)summoned).appliesToChars())
                        throw new InvalidSummonException("character");
                    else
                        break;
                default:
                    throw new InvalidSummonException("non-entity");
            }

            this.changeBloodCount(-summoned.getCost());
            ((Spell)summoned).applyEffect(target);
            this.discardSpell(source);
            App.getSession().addEvent(new SpellSummonEvent(target, summoned.getCost(), summoned.getImage()));
        }

        return summoned;
    }

    public void discardSpell(int hand_pos) {
        this.pile.push(this.hand.get(hand_pos));
        this.hand.remove(hand_pos);
    }

    public void killChar(Pointers pointer) {
        int column = PointerProcessor.pointerToInt(pointer);
        Character dead_char = this.slots[column];
        this.slots[column] = null;
        dead_char.reset();

        if (dead_char.hasSigil(SigilCodes.RESURRECTION) && !dead_char.hasSigil(SigilCodes.NINE_LIVES))
            this.hand.add(dead_char);
        else
            this.pile.push(this.slots[column]);
    }

    public void sacrifice(Pointers pointer) throws BloodCountException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException, UndeadSacrificeException {
        Character sacrifice = (Character)PointerProcessor.pointerToEntity(pointer);

        if (sacrifice.hasSigil(SigilCodes.WORTHY_SACRIFICE))
            this.changeBloodCount(3);
        else
            this.changeBloodCount(1);

        if (sacrifice.hasSigil(SigilCodes.UNDEAD))
            throw new UndeadSacrificeException();

        if (sacrifice.hasSigil(SigilCodes.NINE_LIVES))
            sacrifice.addSigil(SigilCodes.UNDEAD);
        else
            this.killChar(pointer);
        
        App.getSession().addEvent(new SacrificeEvent(pointer));
    }

    public void attack() throws DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException {
        for (int i = 0; i < 4; i++) {
            try {
                this.slots[i].attack();
            }
            catch (NullPointerException e) {
                continue;
            }
        }
    }

    public void reset() {
        this.health = 20;
        this.blood_count = 5;
        this.sigils = new ArrayList<SigilCodes>();
        
        this.deck = new Stack<Card>();
        this.hand = new ArrayList<Card>();
        this.slots = new Character[4];
        this.pile = new Stack<Card>();
    }
}
