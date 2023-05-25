package models.engines;

import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import models.App;
import models.Avatar;
import models.Session;
import models.enums.Locations;
import models.processors.LocationProcessor;

public class BoardEngine {
    Session session = App.getSession();
    private Avatar home = session.getHome();
    private Avatar away = session.getAway();
    LocationProcessor location_processor;

    public BoardEngine(LocationProcessor l) {
        this.location_processor = l;
    }
    
    public void setProperties() {
        for (Locations l : Locations.values())
            location_processor.toNode(l).getProperties().put("location", l);
    }

    public void setDisables() {
        if (session.getPlayingSide() == 'h') {
            location_processor.toNode(Locations.HD).setDisable(false);
            location_processor.toNode(Locations.HH).setDisable(false);
            location_processor.toNode(Locations.HG).setDisable(false);

            location_processor.toNode(Locations.AD).setDisable(true);
            location_processor.toNode(Locations.AH).setDisable(true);
            location_processor.toNode(Locations.AG).setDisable(true);
        }
        else if (session.getPlayingSide() == 'a') {
            location_processor.toNode(Locations.HD).setDisable(true);
            location_processor.toNode(Locations.HH).setDisable(true);
            location_processor.toNode(Locations.HG).setDisable(true);

            location_processor.toNode(Locations.AD).setDisable(false);
            location_processor.toNode(Locations.AH).setDisable(false);
            location_processor.toNode(Locations.AG).setDisable(false);
        }
    }

    
    /** 
     * @param updated_avatar
     */
    public void updateDeckCount(char updated_avatar) {
        if (updated_avatar == 'h') {
            VBox avatar = (VBox)this.location_processor.toNode(2, 5);
            Text avatar_deck_count = (Text)avatar.getChildren().get(1);
            avatar_deck_count.setText(home.getDeckSize());
        }
        else {
            VBox avatar = (VBox)this.location_processor.toNode(1, 5);
            Text avatar_deck_count = (Text)avatar.getChildren().get(0);
            avatar_deck_count.setText(away.getDeckSize());
        }
    }

    public void updateGraveyardCount(char updated_avatar) {
        if (updated_avatar == 'h') {
            VBox avatar = (VBox)this.location_processor.toNode(2, 0);
            Text avatar_deck_count = (Text)avatar.getChildren().get(1);
            avatar_deck_count.setText(home.getPileSize());
        }
        else {
            VBox avatar = (VBox)this.location_processor.toNode(1, 0);
            Text avatar_deck_count = (Text)avatar.getChildren().get(0);
            avatar_deck_count.setText(away.getPileSize());
        }
    }

    public void updateAvatarHealth(char updated_avatar) {
        if (updated_avatar == 'h') {
            HBox avatar = (HBox)this.location_processor.toNode(3, 1);
            VBox avatar_stats = (VBox)avatar.getChildren().get(1);
            StackPane avatar_health_icon = (StackPane)avatar_stats.getChildren().get(0);
            Text avatar_health = (Text)avatar_health_icon.getChildren().get(0);
            avatar_health.setText(Integer.toString(home.getHealth()));
        }
        else {
            HBox avatar = (HBox)this.location_processor.toNode(0, 1);
            VBox avatar_stats = (VBox)avatar.getChildren().get(1);
            StackPane avatar_health_icon = (StackPane)avatar_stats.getChildren().get(0);
            Text avatar_health = (Text)avatar_health_icon.getChildren().get(0);
            avatar_health.setText(Integer.toString(away.getHealth()));
        }
    }

    public void updateAvatarBlood(char updated_avatar) {
        if (updated_avatar == 'h') {
            HBox avatar = (HBox)this.location_processor.toNode(3, 1);
            VBox avatar_stats = (VBox)avatar.getChildren().get(1);
            StackPane avatar_blood_icon = (StackPane)avatar_stats.getChildren().get(1);
            Text avatar_blood= (Text)avatar_blood_icon.getChildren().get(0);
            avatar_blood.setText(Integer.toString(home.getBlood()));
        }
        else {
            HBox avatar = (HBox)this.location_processor.toNode(0, 1);
            VBox avatar_stats = (VBox)avatar.getChildren().get(1);
            StackPane avatar_blood_icon = (StackPane)avatar_stats.getChildren().get(1);
            Text avatar_blood = (Text)avatar_blood_icon.getChildren().get(0);
            avatar_blood.setText(Integer.toString(away.getBlood()));
        }
    }

    public void updateCounts() {
        StackPane turn_number_parent = (StackPane)this.location_processor.toNode(0, 5);
        Text turn_number = (Text)turn_number_parent.getChildren().get(1);
        turn_number.setText("Turn No. " + session.getTurnNumber());

        this.updateDeckCount('h');
        this.updateDeckCount('a');
        this.updateGraveyardCount('h');
        this.updateGraveyardCount('a');

        this.updateAvatarHealth('h');
        this.updateAvatarHealth('a');
        this.updateAvatarBlood('h');
        this.updateAvatarBlood('a');
    }
}
