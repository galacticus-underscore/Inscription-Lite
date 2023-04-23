package controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import models.Card;

public class CardController implements Initializable {
    private Card card;

    @FXML private ImageView card_image;

    public CardController(Card c) {
        this.card = c;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/static/images/backing.png");

        if (this.card.isFacingUp())
            file = new File(this.card.getImage());
        
        Image image = new Image(file.toURI().toString());
        card_image.setImage(image);
    }
}
