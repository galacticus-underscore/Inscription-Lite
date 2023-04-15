/*
 * Session.java
 * 
 * This model class represents the game the users will be playing. It dictates
 * the general flow of the game by initializing the avatars, tracking the moves
 * made in the game, and facilitating the transfer of turns from one player to
 * another.
 */

package models;

import java.util.ArrayList;
import java.util.LinkedList;

import models.patterns.Event;
import models.patterns.SigilEffect;

import models.enums.EventTypes;

import models.processors.PointerProcessor;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.EmptyDeckException;
import models.exceptions.NullSessionException;
import models.exceptions.ZeroHealthException;

public class Session {
    private static Avatar[] avatars = {
        new Avatar("src/static/data/card_data.csv"),
        new Avatar("src/static/data/card_data.csv"),
    };

    private int p1_index, p2_index, turn_number = 0;
    private Avatar playing_avatar, observing_avatar;
    private LinkedList<Event> event_history = new LinkedList<Event>();

    public void initialize() throws NullSessionException, EmptyDeckException, DeadAvatarException, DeadCharacterException {
        this.playing_avatar = avatars[this.p1_index];
        this.observing_avatar = avatars[this.p2_index];
        this.playing_avatar.initialize();
        this.observing_avatar.initialize();
    }

    public Avatar getPlayingAvatar() {
        return this.playing_avatar;
    }

    public Avatar getObservingAvatar() {
        return this.observing_avatar;
    }

    public Event peekLastEvent() {
        return this.event_history.peek();
    }

    public Event pullLastEvent() {
        return this.event_history.poll();
    }

    public void addEvent(Event e) throws DeadAvatarException, DeadCharacterException, NullSessionException {
        this.event_history.add(e);

        if (e.getType().equals(EventTypes.AVATAR_DEATH)) {
            throw new DeadAvatarException();
        }
        else if (e.getType().equals(EventTypes.CHAR_DEATH)) {
            throw new DeadCharacterException();
        }
        else if (e.getType().equals(EventTypes.SIGIL_EFFECT)) {
            ;
        }
        else {
            ArrayList<SigilEffect> effects = PointerProcessor.fromPointer(e.getSource()).getEffects();
            SigilEffect effect;

            for (int i = 0; i < effects.size(); i++) {
                effect = effects.get(i);
                if (effect.appliesToEvent(e.getType()))
                    effect.applyEffect(e);
            }
            
            effects = PointerProcessor.fromPointer(e.getTarget()).getEffects();

            for (int i = 0; i < effects.size(); i++) {
                effect = effects.get(i);
                if (effect.appliesToEvent(e.getType()))
                    effect.applyEffect(e);
            }
        }
    }

    public void nextPlayer() throws NullSessionException, ZeroHealthException, DeadAvatarException, DeadCharacterException {
        if (turn_number > 0) {
            this.playing_avatar.attack();
        }

        if (this.playing_avatar == avatars[this.p1_index]) {
            this.playing_avatar = avatars[this.p2_index];
            this.observing_avatar = avatars[this.p1_index];
        }
        else {
            this.playing_avatar = avatars[this.p1_index];
            this.observing_avatar = avatars[this.p2_index];
            this.turn_number += 1;
        }

        this.playing_avatar.flip();
        this.observing_avatar.flip();
    }

    public void close() {
        this.playing_avatar.reset();
        this.observing_avatar.reset();
    }
}
