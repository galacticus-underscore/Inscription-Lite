package models;
public class Card {
    protected int cost;
    protected Faces rendered_face = Faces.TOP;

    private enum Faces {
        TOP, BOTTOM;
    }

    public Card(int c) {
        this.cost = c;
    }

    public int getCost() {
        return cost;
    }
}
