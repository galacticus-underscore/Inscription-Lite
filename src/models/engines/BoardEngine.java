package models.engines;

import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import models.App;
import models.enums.Locations;
import models.processors.LocationProcessor;

public class BoardEngine {
    LocationProcessor location_processor;

    public BoardEngine(LocationProcessor l) {
        this.location_processor = l;
    }
    
    public void setProperties() {
        for (Locations l : Locations.values())
            location_processor.toNode(l).getProperties().put("location", l);
    }

    public void setDisables() {
        if (App.getSession().getPlayingSide() == 'h') {
            location_processor.toNode(Locations.HD).setDisable(false);
            location_processor.toNode(Locations.HH).setDisable(false);
            location_processor.toNode(Locations.HG).setDisable(false);

            location_processor.toNode(Locations.AD).setDisable(true);
            location_processor.toNode(Locations.AH).setDisable(true);
            location_processor.toNode(Locations.AG).setDisable(true);
        }
        else if (App.getSession().getPlayingSide() == 'a') {
            location_processor.toNode(Locations.HD).setDisable(true);
            location_processor.toNode(Locations.HH).setDisable(true);
            location_processor.toNode(Locations.HG).setDisable(true);

            location_processor.toNode(Locations.AD).setDisable(false);
            location_processor.toNode(Locations.AH).setDisable(false);
            location_processor.toNode(Locations.AG).setDisable(false);
        }
    }

    public void updateCounts() {
        // case AD:
        ((Text)((VBox)this.location_processor.toNode(1, 5)).getChildren().get(0)).setText(App.getSession().getAway().getDeckSize());
        // case AG:
        ((Text)((VBox)this.location_processor.toNode(1, 0)).getChildren().get(0)).setText(App.getSession().getAway().getPileSize());
        // case HD:
        ((Text)((VBox)this.location_processor.toNode(2, 5)).getChildren().get(1)).setText(App.getSession().getHome().getDeckSize());
        // case HG:
        ((Text)((VBox)this.location_processor.toNode(2, 0)).getChildren().get(1)).setText(App.getSession().getHome().getPileSize());
    }
}
