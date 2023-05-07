package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;

import models.App;
import models.Session;
import models.Avatar;
import models.enums.Locations;
import models.enums.Pointers;

import models.processors.LocationProcessor;
import models.processors.PointerProcessor;

import models.engines.ActionEngine;
import models.engines.BoardEngine;
import models.engines.CardEngine;
import models.engines.DisplayEngine;
import models.exceptions.BloodCountException;
import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;

public class SessionController implements Initializable {
    private Session session = App.getSession();
    private Avatar home = session.getHome();
    private Avatar away = session.getAway();

    private LocationProcessor location_processor;
    private BoardEngine board_engine;
    private CardEngine card_engine;
    private DisplayEngine display_engine;
    private ActionEngine action_engine;

    private Node highlighted;
    private EventHandler<MouseEvent> event_handler = 
    new EventHandler<MouseEvent>() {
        public void handle(MouseEvent e) {
            handleClick(e);
        } 
    };

    @FXML private GridPane board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            session.nextPlayer();
        } catch (BloodCountException e) {}

        System.out.println("=".repeat(80));
        System.out.println("Turn number: " + session.getTurnNumber());
        System.out.println("Playing side: " + (session.getPlayingSide() == 'h' ? "Home" : "Away"));

        location_processor = new LocationProcessor(board);
        board_engine = new BoardEngine(location_processor);
        card_engine = new CardEngine(location_processor);
        display_engine = new DisplayEngine(location_processor);
        action_engine = new ActionEngine(location_processor, board_engine, card_engine, display_engine, event_handler);

        board_engine.setProperties();
        board_engine.setDisables();
        board_engine.updateCounts();
        card_engine.renderHands();
        card_engine.renderSlots();

        HBox home_hand = ((HBox)location_processor.toNode(Locations.HH));
        HBox away_hand = ((HBox)location_processor.toNode(Locations.AH));

        for (Node n : home_hand.getChildren())
            n.setOnMouseClicked(event_handler);
        
        for (Node n : away_hand.getChildren())
            n.setOnMouseClicked(event_handler);
    }

    @FXML
    public void handleClick(MouseEvent event) {
        // all click methods for the board are unified in this function to
        // allow the highlight function to work
        Node source = (Node)event.getSource();
        display_engine.removeErrors();

        if (this.highlighted == null)
            display_engine.highlight(source);
        else {
            if (action_engine.isDraw(event))
                action_engine.draw(event);
            else if (action_engine.isSummon(event))
                action_engine.summon(event, source, this.highlighted);
            else if (action_engine.isSacrifice(event))
                action_engine.sacrifice(event, source, this.highlighted);
            else {
                display_engine.unhighlight();
                display_engine.highlight(source);
            }
        }

        this.highlighted = display_engine.getHighlighted();
    }

    @FXML 
    public void nextTurn(MouseEvent event) throws IOException, PointerConversionException {
        boolean avatar_dead = false;
        char death_side = 'h';

        if (session.getTurnNumber() > 0) {
            for (int i = 0; i < 4; i++) {
                try {
                    session.getPlayingAvatar().getCharInSlot(i).attack();

                    Pointers attack_target = session.getLastTarget();
                    Locations attack_location = PointerProcessor.toLocation(attack_target);
                    String attack_location_str = attack_location.toString().toLowerCase();

                    if (attack_location_str.charAt(1) == 'a') {
                        char attacked_avatar = attack_location_str.charAt(0);
                        board_engine.updateAvatarHealth(attacked_avatar);
                    }
                }
                catch (NullPointerException e) {
                    // skip slot if there is no character in it
                    continue;
                }
                catch (DeadCharacterException e) {
                    Pointers target_pointer = session.getLastTarget();
                    Avatar target_avatar = PointerProcessor.getAvatar(target_pointer);
                    target_avatar.killChar(target_pointer);
                }
                catch (DeadAvatarException e) {
                    avatar_dead = true;
                    Pointers target_pointer = session.getLastTarget();
                    Avatar target_avatar = (Avatar)PointerProcessor.toEntity(target_pointer);

                    if (target_avatar == home)
                        death_side = 'h';
                    else
                        death_side = 'a';

                    break;
                }
            }
        }

        if (!avatar_dead) {
            System.out.println("Home health: " + home.getHealth());
            System.out.println("Away health: " + away.getHealth());
            session.getPlayingAvatar().flip();
            display_engine.switchScreen("Confirmation.fxml", event);
        }
        else {
            char winner = death_side == 'h' ? 'a' : 'h';
            EndgameController controller = new EndgameController(winner);
            display_engine.switchScreen("Endgame.fxml", event, controller);
        }

        System.out.println();
    }
}
