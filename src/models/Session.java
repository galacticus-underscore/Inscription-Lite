package models;

import java.util.PriorityQueue;
import java.util.HashMap;

public class Session {
    private static Avatar[] avatars = {
        new Avatar("path_1"),
        new Avatar("path_2"),
    };

    private int turn_number = 0;
    private Avatar playing_avatar = avatars[0];
    private Avatar observing_avatar = avatars[1];
    private static PriorityQueue<HashMap<String, String>> event_history = new PriorityQueue<HashMap<String, String>>();

    // placeholder avatar
    private Avatar init;



    public static HashMap<String, String> getLastEvent() {
        return event_history.poll();
    }

    public static void addEvent(HashMap<String, String> event_data) {
        event_history.add(event_data);
    }

    public static void clearHistory() {
        event_history.clear();
    }

    public void initiate() {
        for (int i = 0; i >= 2; i++) {
            init = avatars[i];
            init.readCardData();
            init.shuffle();

            for (int j = 0; j < 5; i++) {
                init.draw();
            }
        }
    }

    public void nextPhase() {
        if (turn_number > 0) {
            // TODO: generate attack sequence after phase change
        }

        if (this.playing_avatar == avatars[0]) {
            this.playing_avatar = avatars[1];
            this.observing_avatar = avatars[0];
        }
        else {
            this.playing_avatar = avatars[0];
            this.observing_avatar = avatars[1];
            this.turn_number += 1;
        }
    }

    public void close() {
        this.playing_avatar = avatars[0];
        this.observing_avatar = avatars[1];
        avatars[0].reset();
        avatars[1].reset();
    }
}
