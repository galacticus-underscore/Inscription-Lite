package controllers;

import java.io.File;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import models.App;
import models.Session;
import models.Avatar;
import models.Card;
import models.Character;

import models.enums.Pointers;
import models.enums.Locations;

import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public class SessionController implements Initializable {
    private Session session = App.getSession();
    private Avatar home = session.getHome();
    private Avatar away = session.getAway();
    private char playing_avatar;
    
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

    @FXML private Pane home_icon;
    @FXML private Text home_health;
    @FXML private Text home_blood;
    @FXML private Text home_error;
    @FXML private StackPane home_deck;
    @FXML private Text home_deck_count;
    @FXML private HBox home_hand;
    @FXML private StackPane home_slot1;
    @FXML private StackPane home_slot2;
    @FXML private StackPane home_slot3;
    @FXML private StackPane home_slot4;
    @FXML private StackPane home_graveyard;
    @FXML private Text home_graveyard_count;

    @FXML private Pane away_icon;
    @FXML private Text away_health;
    @FXML private Text away_blood;
    @FXML private Text away_error;
    @FXML private StackPane away_deck;
    @FXML private Text away_deck_count;
    @FXML private HBox away_hand;
    @FXML private StackPane away_slot1;
    @FXML private StackPane away_slot2;
    @FXML private StackPane away_slot3;
    @FXML private StackPane away_slot4;
    @FXML private StackPane away_graveyard;
    @FXML private Text away_graveyard_count;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playing_avatar = session.nextPlayer();
        setProperties();
        setDisables();
        renderHands();
        renderSlots();
    }

    @FXML
    public void handleClick(MouseEvent event) {
        Node source = (Node)event.getSource();
        removeErrors();

        if (highlighted == null) {
            highlight(source);
        }
        else {
            if (source.getStyleClass().contains("deck") && source == highlighted) {
                draw(event);
            }
            else if (source.getStyleClass().contains("slot") && highlighted.getStyleClass().contains("card")) {
                play(event, highlighted, source);
            }
            else if (source.getStyleClass().contains("card") && highlighted.getStyleClass().contains("slot")) {
                play(event, source, highlighted);
            }
            else {
                unhighlight();
                highlight(source);
            }
        }
    }

    @FXML 
    public void nextTurn(MouseEvent event) throws IOException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException {
        session.endTurn();
        
        root = FXMLLoader.load(getClass().getResource("../views/Confirmation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setProperties() {
        home_icon.getProperties().put("location", Locations.HA);
        home_deck.getProperties().put("location", Locations.HD);
        home_hand.getProperties().put("location", Locations.HH);
        home_slot1.getProperties().put("location", Locations.H1);
        home_slot2.getProperties().put("location", Locations.H2);
        home_slot3.getProperties().put("location", Locations.H3);
        home_slot4.getProperties().put("location", Locations.H4);
        home_graveyard.getProperties().put("location", Locations.HG);

        away_icon.getProperties().put("location", Locations.HA);
        away_deck.getProperties().put("location", Locations.HD);
        away_hand.getProperties().put("location", Locations.HH);
        away_slot1.getProperties().put("location", Locations.H1);
        away_slot2.getProperties().put("location", Locations.H2);
        away_slot3.getProperties().put("location", Locations.H3);
        away_slot4.getProperties().put("location", Locations.H4);
        away_graveyard.getProperties().put("location", Locations.HG);
    }

    public void setDisables() {
        if (this.playing_avatar == 'h') {
            home_deck.setDisable(false);
            home_hand.setDisable(false);
            home_graveyard.setDisable(false);

            away_deck.setDisable(true);
            away_hand.setDisable(true);
            away_graveyard.setDisable(true);
        }
        else if (this.playing_avatar == 'a') {
            home_deck.setDisable(true);
            home_hand.setDisable(true);
            home_graveyard.setDisable(true);

            away_deck.setDisable(false);
            away_hand.setDisable(false);
            away_graveyard.setDisable(false);
        }
    }

    public StackPane createCard(Card c, Locations l) {
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
        wrapper.getProperties().put("position", l);
        wrapper.setOnMouseClicked(eventHandler);

        return wrapper;
    }

    public void renderCard(Card c, Locations l) {
        StackPane card = createCard(c, l);

        switch ((Locations)card.getProperties().get("position")) {
            case HH:
                home_hand.getChildren().add(card);
                break;
            case AH:
                away_hand.getChildren().add(card);
                break;
            case H1:
                home_slot1.getChildren().add(card);
                break;
            case H2:
                home_slot2.getChildren().add(card);
                break;
            case H3:
                home_slot3.getChildren().add(card);
                break;
            case H4:
                home_slot4.getChildren().add(card);
                break;
            case A1:
                away_slot1.getChildren().add(card);
                break;
            case A2:
                away_slot2.getChildren().add(card);
                break;
            case A3:
                away_slot3.getChildren().add(card);
                break;
            case A4:
                away_slot4.getChildren().add(card);
                break;
            default:
                ;
        }
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

        home.getTakenSlots().forEach((Integer index) -> {
            Card c = home.getCharInSlot(index);
            Locations l = Locations.valueOf("H" + Integer.toString(index + 1));
            renderCard(c, l);
        });

        away.getTakenSlots().forEach((Integer index) -> {
            Card c = home.getCharInSlot(index);
            Locations l = Locations.valueOf("A" + Integer.toString(index + 1));
            renderCard(c, l);
        });
    }

    public void highlight(Node n) {
        this.highlighted = n;
        this.highlighted.getStyleClass().add("pane");
    }

    public void unhighlight(){
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

        switch (playing_avatar) {
            case 'h':
                home_error.setText(e.getMessage());
                break;
            case 'a':
                away_error.setText(e.getMessage());
                break;
        }
    }

    public void removeErrors() {
        if (!home_error.getText().isEmpty())
            home_error.setText("");
        if (!away_error.getText().isEmpty())
            away_error.setText("");
    }

    public void draw(MouseEvent event) {
        try {
            unhighlight();
            Card drawn = session.getPlayingAvatar().draw();
            String location = (playing_avatar + "h").toUpperCase();
            this.renderCard(drawn, Locations.valueOf(location));
            System.out.println(drawn.getName());
        }
        catch (Exception e) {
            displayError(e);
        }
    }

    public Pointers locationToPointer(Locations l) {
        String location = l.name();

        if (playing_avatar == location.charAt(0))
            return Pointers.valueOf("P" + location.charAt(1));
        else
            return Pointers.valueOf("O" + location.charAt(1));
    }

    public void play(MouseEvent event, Node card, Node slot) {
        try {
            unhighlight();

            int source = home.getHand().indexOf(card.getProperties().get("card"));
            if (playing_avatar == 'a')
                source = away.getHand().indexOf(card.getProperties().get("card"));

            Locations target_location = (Locations)slot.getProperties().get("location");
            Pointers target_pointer = locationToPointer(target_location);

            Card summoned = session.getPlayingAvatar().summon(source, target_pointer);
            
            if (summoned instanceof Character) {
                if (playing_avatar == 'h')
                    home_hand.getChildren().remove(card);
                else
                    away_hand.getChildren().remove(card);

                ((StackPane)slot).getChildren().add(card);
                card.setOnMouseClicked(null);
            }

            System.out.println(summoned.getName());
        }
        catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }
}
