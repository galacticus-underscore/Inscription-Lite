/*
 * App.java
 * 
 * This model class contains the highest-level methods used in this
 * application. For now, its only task is to start and end sessions, which are
 * objects that represent games that are being played.
 */

package models;

import models.exceptions.NullSessionException;

public class App {
    private static Session session;

    public static Session getSession() throws NullSessionException {
        if (session == null) {
            throw new NullSessionException();
        }
        return session;
    }

    public static void startSession() {
        session = new Session();
    }

    public static void endSession() {
        session = null;
    }
}
