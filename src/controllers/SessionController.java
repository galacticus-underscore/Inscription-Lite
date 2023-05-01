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
        renderHands();

        if (this.playing_avatar == 'h') {
            home_icon.setDisable(false);
            home_health.setDisable(false);
            home_blood.setDisable(false);
            home_error.setDisable(false);
            home_deck.setDisable(false);
            home_deck_count.setDisable(false);
            home_hand.setDisable(false);
            home_slot1.setDisable(false);
            home_slot2.setDisable(false);
            home_slot3.setDisable(false);
            home_slot4.setDisable(false);
            home_graveyard.setDisable(false);
            home_graveyard_count.setDisable(false);

            away_icon.setDisable(true);
            away_health.setDisable(true);
            away_blood.setDisable(true);
            away_error.setDisable(false);
            away_deck.setDisable(true);
            away_deck_count.setDisable(true);
            away_hand.setDisable(true);
            away_slot1.setDisable(true);
            away_slot2.setDisable(true);
            away_slot3.setDisable(true);
            away_slot4.setDisable(true);
            away_graveyard.setDisable(true);
            away_graveyard_count.setDisable(true);
        }
        else if (this.playing_avatar == 'a') {
            home_icon.setDisable(true);
            home_health.setDisable(true);
            home_blood.setDisable(true);
            home_deck.setDisable(true);
            home_deck_count.setDisable(true);
            home_hand.setDisable(true);
            home_slot1.setDisable(true);
            home_slot2.setDisable(true);
            home_slot3.setDisable(true);
            home_slot4.setDisable(true);
            home_graveyard.setDisable(true);
            home_graveyard_count.setDisable(true);

            away_icon.setDisable(false);
            away_health.setDisable(false);
            away_blood.setDisable(false);
            away_deck.setDisable(false);
            away_deck_count.setDisable(false);
            away_hand.setDisable(false);
            away_slot1.setDisable(false);
            away_slot2.setDisable(false);
            away_slot3.setDisable(false);
            away_slot4.setDisable(false);
            away_graveyard.setDisable(false);
            away_graveyard_count.setDisable(false);
        }
    }
    
    @FXML 
    public void nextTurn(MouseEvent e) throws IOException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException {
        session.endTurn();
        
        root = FXMLLoader.load(getClass().getResource("../views/Confirmation.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleClick(MouseEvent e) {
        Node source = (Node)e.getSource();
        removeErrors();

        if (highlighted == null) {
            highlight(source);
        }
        else {
            if (source.getStyleClass().contains("deck") && source == highlighted) {
                draw(e);
            }
            else {
                unhighlight();
                highlight(source);
            }
        }
    }

    public void renderCard(Card c, char avatar) {
        File file = new File("src/static/images/backing.png");

        if (c.isFacingUp())
            file = new File(c.getImage());
        
        Image image = new Image(file.toURI().toString());
        ImageView card = new ImageView(image);
        card.setFitWidth(55.6);
        card.setFitHeight(80);
        card.getProperties().put("card", c);
        StackPane wrapper = new StackPane(card);

        EventHandler<MouseEvent> eventHandler = 
            new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    handleClick(e);       
                } 
            }; 

        wrapper.setOnMouseClicked(eventHandler);

        // Card test = (Card)card.getProperties().get("card");
        // System.out.println(test.getName());

        switch (avatar) {
            case 'h':
                home_hand.getChildren().add(wrapper);
                break;
            case 'a':
                away_hand.getChildren().add(wrapper);
                break;
        }
    }

    public void renderHands() {
        home.getHand().forEach((Card c) -> {
            try {
                this.renderCard(c, 'h');
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        away.getHand().forEach((Card c) -> {
            try {
                this.renderCard(c, 'a');
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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

        switch (this.playing_avatar) {
            case 'h':
                home_error.setText(e.getMessage());
                break;
            case 'a':
                away_error.setText(e.getMessage());
                break;
        }
    }

    public void removeErrors() {
        home_error.setText("");
        away_error.setText("");
    }

    public void draw(MouseEvent event) {
        try {
            unhighlight();
            Card drawn = session.getPlayingAvatar().draw();
            this.renderCard(drawn, this.playing_avatar);
        }
        catch (Exception ex) {
            displayError(ex);
        }
    }
}
