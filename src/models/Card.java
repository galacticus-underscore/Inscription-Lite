/*
 * Card.java
 * 
 * This model class serves as a template for all cards that will be used
 * accross the entire session.
 */

package models;

public abstract class Card {
    protected String name, image;
    protected int cost;
    protected boolean is_facing_up = true;

    public Card(String n, String i, int c) {
        this.name = n;
        this.image = i;
        this.cost = c;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean isFacingUp() {
        return this.is_facing_up;
    }

    public void flip() {
        this.is_facing_up = !(this.is_facing_up);
    }
}
