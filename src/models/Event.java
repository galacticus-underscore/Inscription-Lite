package models;
public class Event {
    private Types type;
    private String description;
    private SigilAffectable source;
    private SigilAffectable target;

    public Event(Types t, String d) {
        this.type = t;
        this.description = d;
        Session.addEvent(this);
    }

    public Event(Types t, String d, SigilAffectable s, SigilAffectable tg) {
        this.type = t;
        this.description = d;
        this.source = s;
        this.target = tg;
        Session.addEvent(this);
    }

    public Types getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public SigilAffectable getSource() {
        return this.source;
    }

    public SigilAffectable getTarget() {
        return this.target;
    }
}
