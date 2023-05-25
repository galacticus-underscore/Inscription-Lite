package models.engines;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import models.App;
import models.Session;
import models.Avatar;
import models.Card;
import models.Character;

import models.patterns.Event;

import models.enums.Pointers;
import models.enums.Locations;
import models.enums.SigilCodes;

import models.processors.PointerProcessor;
import models.processors.LocationProcessor;

import models.exceptions.WrongSlotException;

public class ActionEngine {
    private Session session = App.getSession();
    private Avatar home = session.getHome();
    private Avatar away = session.getAway();
    private LocationProcessor location_processor;
    private BoardEngine board_engine;
    private CardEngine card_engine;
    private DisplayEngine display_engine;
    private EventHandler<MouseEvent> event_handler;

    public ActionEngine(LocationProcessor l, BoardEngine b, CardEngine c, DisplayEngine d, EventHandler<MouseEvent> e) {
        location_processor = l;
        board_engine = b;
        card_engine = c;
        display_engine = d;
        event_handler = e;
    }

    
    /** 
     * @param event
     * @return boolean
     */
    public boolean isDraw(MouseEvent event) {
        Node source = (Node)event.getSource();
        boolean source_is_deck = source.getStyleClass().contains("deck");
        boolean double_clicked = source == display_engine.getHighlighted();
        return source_is_deck && double_clicked;
    }

    public void draw(MouseEvent event) {
        try {
            display_engine.unhighlight();

            Card drawn = session.getPlayingAvatar().draw();
            String location = (session.getPlayingSide() + "h").toUpperCase();
            StackPane card = card_engine.renderCard(drawn, Locations.valueOf(location));
            card.setOnMouseClicked(event_handler);

            board_engine.updateDeckCount(session.getPlayingSide());

            System.out.println("DRAW: " + drawn.getName());
        }
        catch (Exception e) {
            display_engine.displayError(e);
        }
    }

    public boolean nodeHasClass(Node element, String property) {
        return element.getStyleClass().contains(property);
    }

    public boolean isSummon(MouseEvent event) {
        Node source = (Node)event.getSource();
        Node highlighted = display_engine.getHighlighted();

        boolean source_is_slot = nodeHasClass(source, "slot");
        boolean source_is_card = nodeHasClass(source, "card");
        boolean highlighted_is_slot = nodeHasClass(highlighted, "slot");
        boolean highlighted_is_card = nodeHasClass(highlighted, "card");

        boolean valid_1 = source_is_slot && highlighted_is_card;
        boolean valid_2 = source_is_card && highlighted_is_slot;

        return valid_1 || valid_2;
    }

    public void summon(MouseEvent event, Node source, Node highlighted) {
        Node card, slot;

        if (nodeHasClass(source, "slot")) {
            slot = source;
            card = highlighted;
        }
        else {
            slot = highlighted;
            card = source;
        }

        try {
            display_engine.unhighlight();

            Locations slot_location = (Locations)slot.getProperties().get("location");
            char slot_side = slot_location.toString().toLowerCase().charAt(0);
            if (!(slot_side == session.getPlayingSide()))
                throw new WrongSlotException("You cannot summon a character in your enemy's slots!");


            int source_index = home.getHand().indexOf(card.getProperties().get("card"));
            if (session.getPlayingSide() == 'a')
                source_index = away.getHand().indexOf(card.getProperties().get("card"));

            Locations target_location = (Locations)slot.getProperties().get("location");
            Card summoned = session.getPlayingAvatar().summon(source_index, location_processor.toPointer(target_location));
            
            if (summoned instanceof Character) {
                if (session.getPlayingSide() == 'h')
                    ((HBox)location_processor.toNode(Locations.HH)).getChildren().remove(card);
                else
                    ((HBox)location_processor.toNode(Locations.AH)).getChildren().remove(card);

                ((StackPane)slot).getChildren().add(card);
                card.setOnMouseClicked(null);
            }

            Event summon_event = session.peekLastEvent();
            Pointers summon_target = summon_event.getTarget();
            Locations summon_location = PointerProcessor.toLocation(summon_target);
            char summon_avatar = summon_location.toString().toLowerCase().charAt(0);
            board_engine.updateAvatarBlood(summon_avatar);

            System.out.println("SUMMON: " + summoned.getName());
        }
        catch (Exception e) {
            display_engine.displayError(e);
        }
    }

    public boolean isSacrifice(MouseEvent event) {
        Node source = (Node)event.getSource();
        Node highlighted = display_engine.getHighlighted();

        boolean source_is_slot = nodeHasClass(source, "slot");
        boolean source_is_pile = nodeHasClass(source, "graveyard");
        boolean highlighted_is_slot = nodeHasClass(highlighted, "slot");
        boolean highlighted_is_pile = nodeHasClass(highlighted, "graveyard");

        boolean valid_1 = source_is_slot && highlighted_is_pile;
        boolean valid_2 = source_is_pile && highlighted_is_slot;

        return valid_1 || valid_2;
    }

    public void sacrifice(MouseEvent event, Node source, Node highlighted) {
        StackPane slot;

        if (nodeHasClass(source, "slot"))
            slot = (StackPane)source;
        else
            slot = (StackPane)highlighted;

        try {
            display_engine.unhighlight();

            Locations slot_location = (Locations)slot.getProperties().get("location");
            char slot_side = slot_location.toString().toLowerCase().charAt(0);
            if (!(slot_side == session.getPlayingSide()))
                throw new WrongSlotException("You cannot sacrifice your enemy's character!");
            
            Character sacrificed = session.getPlayingAvatar().sacrifice(location_processor.toPointer(slot_location));
            if (!sacrificed.hasSigil(SigilCodes.NINE_LIVES))
                slot.getChildren().clear();

            char sacrifice_avatar = slot_location.toString().toLowerCase().charAt(0);
            board_engine.updateGraveyardCount(sacrifice_avatar);
            board_engine.updateAvatarBlood(sacrifice_avatar);

            System.out.println("SACRIFICE: " + sacrificed.getName());
            System.out.println(slot.getChildren());
        }
        catch (Exception e) {
            display_engine.displayError(e);
        }
    }
}
