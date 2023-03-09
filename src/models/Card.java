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

    public void faceUp() {
        rendered_face = Faces.TOP;
    }

    public void faceDown() {
        rendered_face = Faces.BOTTOM;
    }
}
