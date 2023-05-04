package models.engines;

import javafx.scene.Node;
import javafx.scene.text.Text;

import models.App;
import models.processors.LocationProcessor;

public class DisplayEngine {
    private LocationProcessor location_processor;
    private Node highlighted;
    private Text home_error;
    private Text away_error;

    public DisplayEngine(LocationProcessor l) {
        this.location_processor = l;
        this.home_error = (Text)this.location_processor.toNode(3, 0);
        this.away_error = (Text)this.location_processor.toNode(0, 0);
    }

    public Node getHighlighted() {
        return this.highlighted;
    }
    
    public void highlight(Node n) {
        this.highlighted = n;
        this.highlighted.getStyleClass().add("pane");
    }

    public void unhighlight() {
        try {
            this.highlighted.getStyleClass().remove("pane");
            this.highlighted = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayError(Exception e) {
        if (highlighted != null)
            unhighlight();

        switch (App.getSession().getPlayingSide()) {
            case 'h':
                home_error.setText(e.getMessage());
                break;
            case 'a':
                away_error.setText(e.getMessage());
                break;
        }
    }

    public void removeErrors() {
        try {
            if (!home_error.getText().isEmpty())
                home_error.setText("");
            if (!away_error.getText().isEmpty())
                away_error.setText("");
        }
        catch (NullPointerException e) {
            ;
        }
    }
}
