/*
 * Event.java
 * 
 * This model interface serves as the template for every event that will be
 * generated throughout a session. When an action happens in the game (e.g.,
 * a player draws a card, a sigil is played, a character attacks), an event
 * will be created before that action is executed. Events will be added to the
 * event history directly after creation as anonymous subclasses of this
 * interface. We did this to make future development easier, as updates to the
 * game will inevitably add more kinds of events.
 */

package models.interfaces;

import models.enums.EventTypes;

public interface Event {
    public EventTypes getType();
}
