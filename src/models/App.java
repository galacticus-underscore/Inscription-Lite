/*
 * App.java
 * 
 * This model class contains the highest-level methods used in this
 * application. For now, its only task is to start and end sessions, which are
 * objects that represent games that are being played.
 */

package models;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.EmptyDeckException;
import models.exceptions.NullSessionException;

public class App {
    private static Session session = new Session();

    public static Session getSession() throws NullSessionException {
        if (session == null) {
            throw new NullSessionException();
        }
        
        return session;
    }

    public static void startSession() throws NullSessionException, EmptyDeckException, DeadAvatarException, DeadCharacterException {
        session.initialize(0, 1);
    }

    public static void endSession() {
        session = null;
    }
}

/*
 * Notes to self for CS:
 * - UI cards will not be binded to their counterparts in the model classes
 * 
 * - Create a helper class that will store information on which item on the
 *   screen is selected
 * 
 * - Use this helper class to direct attacks when events are being replayed
 * 
 * - Deaths will be detected by try-catch statements in the controller but will
 *   be dealt with by the model classes
 * 
 * - Check sigil effects at the addEvent method of the session class
 * 
 * - Events will now have a source and a target of the form "xn", where x
 *   indicates which avatar the source/target is from ('p' for playing, 'o' for
 *   observing) and n indicates which character the source/event came from (1-4
 *   represents slots 1-4, while 0 represents the avatar itself)
 */
