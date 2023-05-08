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
import models.enums.Pointers;
import models.exceptions.BloodCountException;
import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;

public class Session {
    private Avatar[] avatars = {
        new Avatar("src/static/data/card_data.csv"),
        new Avatar("src/static/data/card_data.csv"),
    };

    private boolean new_init = true;
    private int home_index, away_index, turn_number = 0;
    private char playing_side = 'h';
    private Avatar playing_avatar, observing_avatar;
    private LinkedList<Event> event_history = new LinkedList<Event>();

    public void open(int p1, int p2) {
        this.home_index = p1;
        this.away_index = p2;
        this.playing_avatar = avatars[this.home_index];
        this.observing_avatar = avatars[this.away_index];

        this.playing_avatar.initialize();
        this.observing_avatar.initialize();
    }

    public boolean isNewInit() {
        return this.new_init;
    }

    public char getPlayingSide() {
        return this.playing_side;
    }

    public Avatar getPlayingAvatar() {
        return this.playing_avatar;
    }

    public Avatar getObservingAvatar() {
        return this.observing_avatar;
    }

    public Avatar getHome() {
        return avatars[this.home_index];
    }

    public Avatar getAway() {
        return avatars[this.away_index];
    }

    public int getTurnNumber() {
        return this.turn_number;
    }

    public Event peekLastEvent() {
        return this.event_history.getLast();
    }

    public Pointers getLastTarget() {
        return this.event_history.getLast().getTarget();
    }

    public Event pullFirstEvent() {
        return this.event_history.poll();
    }

    public void addEvent(Event e) throws DeadCharacterException, DeadAvatarException, PointerConversionException {
        this.event_history.add(e);

        if (e.getType().equals(EventTypes.AVATAR_DEATH))
            throw new DeadAvatarException();
        
        if (e.getType().equals(EventTypes.CHAR_DEATH))
            throw new DeadCharacterException();
    }

    public char nextPlayer() throws BloodCountException {

        if (!this.new_init) {
            if (this.playing_avatar == avatars[this.home_index]) {
                this.playing_avatar = avatars[this.away_index];
                this.observing_avatar = avatars[this.home_index];
                this.playing_side = 'a';
            }
            else {
                this.playing_avatar = avatars[this.home_index];
                this.observing_avatar = avatars[this.away_index];
                this.turn_number += 1;
                this.playing_avatar.changeBloodCount(1);
                this.observing_avatar.changeBloodCount(1);
                this.playing_side = 'h';
            }

            this.playing_avatar.allowDraw();
            this.playing_avatar.flip();
        }
        else {
            this.new_init = false;
            this.observing_avatar.flip();
        }
        
        return this.playing_side;
    }

    public void close() {
        this.playing_avatar.reset();
        this.observing_avatar.reset();

        this.new_init = true;
        this.turn_number = 0;
        this.event_history.clear();;
    }
}
