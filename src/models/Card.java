/*
 * Card.java
 * 
 * This model class serves as a template for all cards that will be used
 * accross the entire session.
 */

package models;

public abstract class Card {
    protected int cost;
    protected String image;
    protected boolean is_facing_up = true;
    protected boolean is_highlighted = false;

    public Card(int c, String i) {
        this.cost = c;
        this.image = i;
    }

    public int getCost() {
        return this.cost;
    }

    public String getImage() {
        return this.image;
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
