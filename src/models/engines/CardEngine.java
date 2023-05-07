package models.engines;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import models.App;
import models.Avatar;
import models.Card;
import models.Session;
import models.enums.Locations;
import models.processors.LocationProcessor;

public class CardEngine {
    private Session session = App.getSession();
    private Avatar home = session.getHome();
    private Avatar away = session.getAway();
    
    LocationProcessor location_processor;
    
    public CardEngine(LocationProcessor l) {
        this.location_processor = l;
    }
    
    public StackPane createCard(Card c) {
        File file = new File("src/static/images/backing.png");

        if (c.isFacingUp())
            file = new File(c.getImage());
        
        Image image = new Image(file.toURI().toString());
        ImageView card = new ImageView(image);
        card.setFitWidth(55.6);
        card.setFitHeight(80);
        StackPane wrapper = new StackPane(card);
        wrapper.getStyleClass().add("card");
        wrapper.getProperties().put("card", c);

        return wrapper;
    }

    public StackPane renderCard(Card c, Locations l) {
        StackPane card = createCard(c);

        switch (l) {
            case HH:
                ((HBox)location_processor.toNode(Locations.HH)).getChildren().add(card);
                break;
            case AH:
                ((HBox)location_processor.toNode(Locations.AH)).getChildren().add(card);
                break;
            case H1:
                ((StackPane)location_processor.toNode(Locations.H1)).getChildren().add(card);
                break;
            case H2:
                ((StackPane)location_processor.toNode(Locations.H2)).getChildren().add(card);
                break;
            case H3:
                ((StackPane)location_processor.toNode(Locations.H3)).getChildren().add(card);
                break;
            case H4:
                ((StackPane)location_processor.toNode(Locations.H4)).getChildren().add(card);
                break;
            case A1:
                ((StackPane)location_processor.toNode(Locations.A1)).getChildren().add(card);
                break;
            case A2:
                ((StackPane)location_processor.toNode(Locations.A2)).getChildren().add(card);
                break;
            case A3:
                ((StackPane)location_processor.toNode(Locations.A3)).getChildren().add(card);
                break;
            case A4:
                ((StackPane)location_processor.toNode(Locations.A4)).getChildren().add(card);
            default:
                ;
        }

        return card;
    }

    public void renderHands() {
        home.getHand().forEach((Card c) -> {
            try {
                this.renderCard(c, Locations.HH);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        away.getHand().forEach((Card c) -> {
            try {
                this.renderCard(c, Locations.AH);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void renderSlots() {
        for (int i = 0; i < 4; i++) {
            try {
                Card c = home.getCharInSlot(i);
                Locations l = Locations.valueOf("H" + Integer.toString(i + 1));
                renderCard(c, l); 
            }
            catch (NullPointerException e) {
                ;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Card c = away.getCharInSlot(i);
                Locations l = Locations.valueOf("A" + Integer.toString(i + 1));
                renderCard(c, l);
            }
            catch (NullPointerException e) {
                ;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
