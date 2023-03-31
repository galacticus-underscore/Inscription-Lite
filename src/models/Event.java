// package models;

// import java.util.HashMap;

// public class Event {
//     private HashMap<String, String> event_data;

//     public Event(HashMap<String, String> d) {
//         this.event_data = d;
//         Session.addEvent(this);
//     }

//     public Event(Types t, String d, SigilAffectable s, SigilAffectable tg) {
//         this.type = t;
//         this.description = d;
//         this.source = s;
//         this.target = tg;
//         Session.addEvent(this);
//     }

//     public Types getType() {
//         return this.type;
//     }

//     public String getDescription() {
//         return this.description;
//     }

//     public SigilAffectable getSource() {
//         return this.source;
//     }

//     public SigilAffectable getTarget() {
//         return this.target;
//     }
// }
