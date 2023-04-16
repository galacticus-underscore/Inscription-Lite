/*
 * Session.java
 * 
 * This model class represents the game the users will be playing. It dictates
 * the general flow of the game by initializing the avatars, tracking the moves
 * made in the game, and facilitating the transfer of turns from one player to
 * another.
 */

package models;

import java.util.LinkedList;

import models.patterns.Event;
import models.enums.EventTypes;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.NullSessionException;
import models.exceptions.PointerConversionException;

public class Session {
    private static Avatar[] avatars = {
        new Avatar("src/static/data/card_data.csv"),
        new Avatar("src/static/data/card_data.csv"),
    };

    private int p1_index, p2_index, turn_number = 0;
    private Avatar playing_avatar, observing_avatar;
    private LinkedList<Event> event_history = new LinkedList<Event>();

    public void open(int p1, int p2) {
        this.p1_index = p1;
        this.p2_index = p2;
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

    public void addEvent(Event e) throws DeadCharacterException, DeadAvatarException {
        this.event_history.add(e);

        if (e.getType().equals(EventTypes.AVATAR_DEATH)) {
            throw new DeadAvatarException();
        }
        
        if (e.getType().equals(EventTypes.CHAR_DEATH)) {
            throw new DeadCharacterException();
        }
    }

    // call after a player finishes their turn
    public void endTurn() throws NullSessionException, DeadAvatarException, DeadCharacterException, PointerConversionException {
        if (turn_number > 0) {
            this.playing_avatar.attack();
        }

        this.playing_avatar.flip();
        this.observing_avatar.flip();
    }

    // call during the start of the next player's turn, after the moves of the last player are replayed
    public void nextPlayer() {
        if (this.playing_avatar == avatars[this.p1_index]) {
            this.playing_avatar = avatars[this.p2_index];
            this.observing_avatar = avatars[this.p1_index];
        }
        else {
            this.playing_avatar = avatars[this.p1_index];
            this.observing_avatar = avatars[this.p2_index];
            this.turn_number += 1;
        }
    }

    public void close() {
        this.playing_avatar.reset();
        this.observing_avatar.reset();
    }
}
