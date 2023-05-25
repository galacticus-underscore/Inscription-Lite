/*
 * Event.java
 * 
 * This model class serves as the template for every event that will be
 * generated throughout a session. When an action happens in the game (e.g.,
 * a player draws a card, a sigil is played, a character attacks), an event
 * will be created before that action is executed. Events will be added to the
 * event history directly after creation as anonymous subclasses of this
 * class. We did this to make future development easier, as updates to the
 * game will inevitably add more kinds of events.
 */

package models.patterns;

import models.enums.EventTypes;
import models.enums.Pointers;

public abstract class Event {
    protected EventTypes type;
    protected Pointers source;
    protected Pointers target;

    public Event(EventTypes t, Pointers p1, Pointers p2) {
        this.type = t;
        this.source = p1;
        this.target = p2;
    }

    
    /** 
     * @return EventTypes
     */
    public EventTypes getType() {
        return this.type;
    }

    public Pointers getSource() {
        return this.source;
    }

    public Pointers getTarget() {
        return this.target;
    }
}
