package models;

import java.util.LinkedList;

import models.patterns.Event;
import models.enums.EventTypes;
import models.enums.Pointers;
import models.exceptions.BloodCountException;
import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;

/*
 * This class represents all the games the users will play while this application is running. It dictates the general flow of a game by initializing the avatars, logging the players' moves, and facilitating the transfer of turns from one player to another.
 * 
 * This class contains:
 * <li>
 *  <ul>An array of all avatars the players can play as</ul>
 *  <ul>An indicator as to whether the session was just initialized or not</ul>
 *  <ul>Integers representing the index of the players' avatars in the avatar array</ul>
 *  <ul>A turn counter</ul>
 *  <ul>A character to determine which side is playing (h - home or a - away)</ul>
 *  <ul>An <code>Avatar</code> variable to store the avatar of the player taking their turn</ul>
 *  <ul>An <code>Avatar</code> variable to store the avatar of the player not taking their turn</ul>
 *  <ul>A linked list of all important events that happened in the game</ul>
 * </li>
 */
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

    /** 
     * Starts a new game
     * 
     * @param p1    index of the first player's avatar in the avatar array
     * @param p2    index of the second player's avatar in the avatar array
     */
    public void open(int p1, int p2) {
        this.home_index = p1;
        this.away_index = p2;
        this.playing_avatar = avatars[this.home_index];
        this.observing_avatar = avatars[this.away_index];

        this.playing_avatar.initialize();
        this.observing_avatar.initialize();
    }

    /** 
     * Determines whether the session is newly initialized or not
     * 
     * @return      <code>true</code> if <code>open()</code> was just called and no moves have been made; <code>false</code> otherwise
     */
    public boolean isNewInit() {
        return this.new_init;
    }

    /** 
     * Returns a character corresponding to which side is playing
     * 
     * @return      'h' is the home side (the side at the bottom of the screen) is playing, 'a' otherwise
     */
    public char getPlayingSide() {
        return this.playing_side;
    }

    /** 
     * Returns a reference to the avatar of the currently playing player
     * 
     * @return      An <code>Avatar</code> object representing the avatar of the currently playing player
     */
    public Avatar getPlayingAvatar() {
        return this.playing_avatar;
    }

    /** 
     * Returns a reference to the avatar of the currently idling player
     * 
     * @return      An <code>Avatar</code> object representing the avatar of the currently idling player
     */
    public Avatar getObservingAvatar() {
        return this.observing_avatar;
    }

    /** 
     * Returns a reference to the avatar of the player in the home side (the bottom of the screen)
     * 
     * @return      An <code>Avatar</code> object representing the avatar of the player in the home side
     */
    public Avatar getHome() {
        return avatars[this.home_index];
    }

    /** 
     * Returns a reference to the avatar of the player in the away side (the top of the screen)
     * 
     * @return      An <code>Avatar</code> object representing the avatar of the player in the away side
     */
    public Avatar getAway() {
        return avatars[this.away_index];
    }

    /** 
     * Returns the current turn number
     * 
     * @return      An integer representing the current turn number
     */
    public int getTurnNumber() {
        return this.turn_number;
    }

    /** 
     * Returns the last recorded event that occured in the game
     * 
     * @return      The last <code>Event</code> object in <code>event_history</code>
     */
    public Event peekLastEvent() {
        return this.event_history.getLast();
    }

    /** 
     * Returns the target of the last recorded event that occured in the game
     * 
     * @return      A <code>Pointer</code> object representing the target of the last recorded event that occured in the game
     */
    public Pointers getLastTarget() {
        return this.event_history.getLast().getTarget();
    }

    /** 
     * Returns the first event in <code>event_history</code> and removes it from the linked list
     * 
     * @return      The first <code>Event</code> object in <code>event_history</code>
     */
    public Event pullFirstEvent() {
        return this.event_history.poll();
    }

    /** 
     * Adds an event to <code>event_history</code>
     * 
     * @param e     The event to add to <code>event_history</code>
     * @throws      DeadAvatarException     if the event added is of type <code>EventTypes.AVATAR_DEATH</code>
     * @throws      DeadCharacterException  if the event added is of type <code>EventTypes.CHAR_DEATH</code>
     */
    public void addEvent(Event e) throws DeadCharacterException, DeadAvatarException, PointerConversionException {
        this.event_history.add(e);

        if (e.getType().equals(EventTypes.AVATAR_DEATH))
            throw new DeadAvatarException();
        
        if (e.getType().equals(EventTypes.CHAR_DEATH))
            throw new DeadCharacterException();
    }

    /** 
     * Transitions the game from one player to another by switching the values of <code>playing_avatar</code> and <code>observing_avatar</code>
     * 
     * @return      the character representing the playing side (home - h or away - a) <b>after the switch</b>.
     */
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

    /** 
     * Ends the game and resets all values of this session and the avatars in the avatar array
     */
    public void close() {
        this.playing_avatar.reset();
        this.observing_avatar.reset();

        this.new_init = true;
        this.turn_number = 0;
        this.event_history.clear();;
    }
}
