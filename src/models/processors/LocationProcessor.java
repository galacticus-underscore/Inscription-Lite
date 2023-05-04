package models.processors;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import models.App;

import models.enums.Locations;
import models.enums.Pointers;

public class LocationProcessor {
    private GridPane grid;

    public LocationProcessor(GridPane g) {
        this.grid = g;
    }

    public int getNodeRow(Node n) {
        try {
            return GridPane.getRowIndex(n);
        }
        catch (NullPointerException e) {
            return 0;
        }
    }

    public int getNodeCol(Node n) {
        try {
            return GridPane.getColumnIndex(n);
        }
        catch (NullPointerException e) {
            return 0;
        }
    }

    public Node toNode(int row, int col) {
        Node result = null;

        for (Node node : this.grid.getChildren()) {
            if (getNodeRow(node) == row && getNodeCol(node) == col) {
                result = node;
                break;
            }
        }

        return result;
    }

    public Node toNode(Locations l) {
        Node result = null;

        switch (l) {
            case A1:
                result = toNode(1, 1);
                break;
            case A2:
                result = toNode(1, 2);
                break;
            case A3:
                result = toNode(1, 3);
                break;
            case A4:
                result = toNode(1, 4);
                break;
            case AA:
                result = ((HBox)toNode(0, 1)).getChildren().get(0);
                break;
            case AD:
                result = ((VBox)toNode(1, 5)).getChildren().get(1);
                break;
            case AG:
                result = ((VBox)toNode(1, 0)).getChildren().get(1);
                break;
            case AH:
                result = ((HBox)toNode(0, 1)).getChildren().get(3);
                break;
            case H1:
                result = toNode(2, 1);
                break;
            case H2:
                result = toNode(2, 2);
                break;
            case H3:
                result = toNode(2, 3);
                break;
            case H4:
                result = toNode(2, 4);
                break;
            case HA:
                result = ((HBox)toNode(3, 1)).getChildren().get(0);
                break;
            case HD:
                result = ((VBox)toNode(2, 5)).getChildren().get(0);
                break;
            case HG:
                result = ((VBox)toNode(2, 0)).getChildren().get(0);
                break;
            case HH:
                result = ((HBox)toNode(3, 1)).getChildren().get(3);
                break;
        }

        return result;
    }

    public Pointers toPointer(Locations l) {
        String location = l.name().toLowerCase();

        if (App.getSession().getPlayingSide() == location.charAt(0))
            return Pointers.valueOf("P" + location.charAt(1));
        else
            return Pointers.valueOf("O" + location.charAt(1));
    }
}
