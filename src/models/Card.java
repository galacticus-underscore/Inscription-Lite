package models;

public abstract class Card {
    protected int cost;
    protected boolean is_facing_up = true;
    protected boolean is_highlighted = false;

    public Card(int c) {
        this.cost = c;
    }

    public int getCost() {
        return cost;
    }

    public boolean isFacingUp() {
        return this.is_facing_up;
    }

    public boolean isHighlighted() {
        return this.is_highlighted;
    }

    public void flip() {
        this.is_facing_up = !(this.is_facing_up);
    }

    public void highlight() {
        this.is_highlighted = true;
    }

    public void unhighlight() {
        this.is_highlighted = false;
    }
}
