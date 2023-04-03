/*
 * Session.java
 * 
 * This model class represents the game the users will be playing. It dictates
 * the general flow of the game by initializing the avatars, tracking the moves
 * made in the game, and facilitating the transfer of turns from one player to
 * another.
 */

package models;

import java.util.PriorityQueue;

import models.exceptions.EmptyDeckException;
import models.exceptions.NullSessionException;
import models.exceptions.ZeroHealthException;
import models.patterns.Event;

public class Session {
    private static Avatar[] avatars = {
        new Avatar("path_1"),
        new Avatar("path_2"),
    };

    private int p1_index, p2_index, turn_number = 0;
    private Avatar playing_avatar, observing_avatar;
    private PriorityQueue<Event> event_history = new PriorityQueue<Event>();

    public Session(int p1, int p2) throws NullSessionException, EmptyDeckException {
        this.p1_index = p1;
        this.p2_index = p2;
        this.playing_avatar = avatars[p1];
        this.observing_avatar = avatars[p2];

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

    public void addEvent(Event e) {
        this.event_history.add(e);
    }

    public void nextPlayer() throws NullSessionException, ZeroHealthException {
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
}
