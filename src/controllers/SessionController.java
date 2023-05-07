package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;

import models.App;
import models.Session;
import models.Avatar;
import models.Card;
import models.Character;

import models.patterns.Event;

import models.enums.Locations;
import models.enums.Pointers;
import models.processors.LocationProcessor;
import models.processors.PointerProcessor;

import models.engines.BoardEngine;
import models.engines.CardEngine;
import models.engines.DisplayEngine;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;
import models.exceptions.WrongSlotException;

public class SessionController implements Initializable {
    private Session session = App.getSession();
    private Avatar home = session.getHome();
    private Avatar away = session.getAway();
    private char playing_avatar;

    private LocationProcessor location_processor;
    private BoardEngine board_engine;
    private CardEngine card_engine;
    private DisplayEngine display_engine;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Node highlighted;
    private EventHandler<MouseEvent> eventHandler = 
    new EventHandler<MouseEvent>() {
        public void handle(MouseEvent e) {
            handleClick(e);
        } 
    };

    @FXML private GridPane board;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playing_avatar = session.nextPlayer();

        System.out.println("Turn number: " + session.getTurnNumber());
        System.out.println("Playing side: " + (session.getPlayingSide() == 'h' ? "Home" : "Away"));

        location_processor = new LocationProcessor(board);
        board_engine = new BoardEngine(location_processor);
        card_engine = new CardEngine(location_processor);
        display_engine = new DisplayEngine(location_processor);

        board_engine.setProperties();
        board_engine.setDisables();
        board_engine.updateCounts();
        card_engine.renderHands();
        card_engine.renderSlots();

        addClickReaction();
    }

    @FXML
    public void handleClick(MouseEvent event) {
        Node source = (Node)event.getSource();
        display_engine.removeErrors();

        if (this.highlighted == null) {
            display_engine.highlight(source);
        }
        else {
            if (source.getStyleClass().contains("deck") && source == this.highlighted) {
                draw(event);
                addClickReaction();
                board_engine.updateCounts();
            }
            else if (source.getStyleClass().contains("slot") && this.highlighted.getStyleClass().contains("card")) {
                try {
                    getSlotCondition(source);
                    summon(event, this.highlighted, source);
                } catch (WrongSlotException e) {
                    display_engine.displayError(e);
                }
            }
            else if (source.getStyleClass().contains("card") && this.highlighted.getStyleClass().contains("slot")) {
                try {
                    getSlotCondition(source);
                    summon(event, source, this.highlighted);
                } catch (WrongSlotException e) {
                    display_engine.displayError(e);
                }
            }
            else {
                display_engine.unhighlight();
                display_engine.highlight(source);
            }
        }

        this.highlighted = display_engine.getHighlighted();
    }

    @FXML 
    public void nextTurn(MouseEvent event) throws IOException, PointerConversionException, InterruptedException {
        boolean avatar_dead = false;
        char death_side = 'h';

        if (session.getTurnNumber() > 0) {
            for (int i = 0; i < 4; i++) {
                try {
                    session.getPlayingAvatar().getCharInSlot(i).attack();
                }
                catch (NullPointerException e) {
                    // skip slot if there is no character in it
                    continue;
                }
                catch (DeadCharacterException e) {
                    Event death_event = session.peekLastEvent();
                    Pointers target_pointer = death_event.getTarget();
                    Pointers target_avatar_pointer  = PointerProcessor.getAvatarOfPointer(target_pointer);
                    Avatar target_avatar = (Avatar)PointerProcessor.toEntity(target_avatar_pointer);
                    target_avatar.killChar(target_pointer);
                }
                catch (DeadAvatarException e) {
                    avatar_dead = true;

                    Event death_event = session.peekLastEvent();
                    Pointers target_pointer = death_event.getTarget();
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
            
            root = FXMLLoader.load(getClass().getResource("../views/Confirmation.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            char winner = death_side == 'h' ? 'a' : 'h';
            EndgameController controller = new EndgameController(winner);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/Endgame.fxml"));
            fxmlLoader.setController(controller);
            root = fxmlLoader.load();;
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void addClickReaction() {
        HBox home_hand = ((HBox)location_processor.toNode(Locations.HH));
        HBox away_hand = ((HBox)location_processor.toNode(Locations.AH));

        for (Node n : home_hand.getChildren())
            n.setOnMouseClicked(eventHandler);
        
        for (Node n : away_hand.getChildren())
            n.setOnMouseClicked(eventHandler);
    }

    public void draw(MouseEvent event) {
        try {
            display_engine.unhighlight();
            Card drawn = session.getPlayingAvatar().draw();
            String location = (playing_avatar + "h").toUpperCase();
            card_engine.renderCard(drawn, Locations.valueOf(location));
            System.out.println("DRAW: " + drawn.getName());
        }
        catch (Exception e) {
            display_engine.displayError(e);
        }
    }

    public void getSlotCondition(Node source) throws WrongSlotException {
        boolean source_is_slot = source.getStyleClass().contains("slot");
        boolean slot_cond;
        if (source_is_slot)
            slot_cond = source.getProperties().get("location").toString().toLowerCase().charAt(0) == playing_avatar;
        else
            slot_cond = this.highlighted.getProperties().get("location").toString().toLowerCase().charAt(0) == playing_avatar;
        
        if (!slot_cond)
            throw new WrongSlotException();
    }

    public void summon(MouseEvent event, Node card, Node slot) {
        try {
            display_engine.unhighlight();

            int source = home.getHand().indexOf(card.getProperties().get("card"));
            if (playing_avatar == 'a')
                source = away.getHand().indexOf(card.getProperties().get("card"));

            Locations target_location = (Locations)slot.getProperties().get("location");

            Card summoned = session.getPlayingAvatar().summon(source, location_processor.toPointer(target_location));
            
            if (summoned instanceof Character) {
                if (playing_avatar == 'h')
                    ((HBox)location_processor.toNode(Locations.HH)).getChildren().remove(card);
                else
                    ((HBox)location_processor.toNode(Locations.AH)).getChildren().remove(card);

                ((StackPane)slot).getChildren().add(card);
                card.setOnMouseClicked(null);
            }

            System.out.println("SUMMON: " + summoned.getName());
        }
        catch (Exception e) {
            display_engine.displayError(e);
            e.printStackTrace();
        }
    }
}
