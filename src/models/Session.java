package models;
import java.util.Stack;

public class Session {
    private boolean is_active = false;
    private Phases phase = Phases.P1;
    private boolean turn_confirmed = false;

    private static Avatar[] avatars = {
        new Avatar("path_1"),
        new Avatar("path_2"),
    };

    private static Stack<Event> event_history = new Stack<Event>();

    private boolean condition = true;   // placeholder condition
    private Avatar init;                // placeholder avatar

    private enum Phases {
        P1(avatars[0], avatars[1]),
        P2(avatars[1], avatars[0]),
        A(null, null);
    
        private Avatar playing_avatar, observing_avatar;
    
        private Phases(Avatar a1, Avatar a2) {
            this.playing_avatar = a1;
            this.observing_avatar = a2;
        }
    
        public Avatar getPlayingAvatar() {
            return this.playing_avatar;
        }

        public Avatar getObservingAvatar() {
            return this.observing_avatar;
        }
    }

    public boolean getActivity() {
        return this.is_active;
    }

    public void setActivity(boolean a) {
        this.is_active = a;
    }

    public static Event getLastEvent() {
        return event_history.peek();
    }

    public static void addEvent(Event e) {
        event_history.push(e);
    }

    public void render() {
        for (int i = 0; i >= 2; i++) {
            init = avatars[i];
            init.readCardData();
            init.shuffle();

            for (int j = 0; j < 5; i++) {
                init.draw();
            }
        }

        // game loop
        while (is_active){
            ;
        }
    }

    public void nextPhase() {
        App.renderView(Views.CONFIRM);

        switch (this.phase) {
            case P1:
                this.phase = Phases.P2;
            case P2:
                this.phase = Phases.A;
            case A:
                this.phase = Phases.P1;
        }

        try {
            this.phase.getPlayingAvatar().faceUp();
            this.phase.getObservingAvatar().faceDown();
        }
        catch (Exception e) {
            ;   // will trigger in the attack (A) phase
        }

        // waits for the player to press the play button
        while (!turn_confirmed) {
            if (condition) {
                turn_confirmed = true;
                App.renderView(Views.GAME);
            }
        }
    }

    public void close() {
        this.is_active = false;
        this.phase = Phases.P1;
        avatars[0].reset();
        avatars[1].reset();
        App.renderView(Views.START);
    }
}
