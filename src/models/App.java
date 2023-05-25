package models;

/*
 * This class represents the running instance of this application. Currently, it only contains one <code>Session</code> object, but you can expand this class to include more attributes and methods to give the application new functions, such as a catalogue of cards, tutorial stages, user profiles, etc.
 */
public class App {
    private static Session session = new Session();
    
    /** 
     * Get the session assosciated with the currently running instance of this application
     * 
     * @return      The <code>Session</code> object associated with the currently running instance of this application
     */
    public static Session getSession() {
        return session;
    }
}
