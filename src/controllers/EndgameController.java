package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;

import models.App;
import models.engines.DisplayEngine;
import models.processors.StyleProcessor;

public class EndgameController implements Initializable {
    private char winner;
    private DisplayEngine display_engine = new DisplayEngine();

    @FXML private Text title;
    @FXML private ImageView player_icon;
    @FXML private Text winner_side;

    public EndgameController(char w) {
        this.winner = w;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int font_size = StyleProcessor.getFontSize();
        Font font = Font.loadFont(getClass().getResourceAsStream("../static/fonts/Heavyweight.ttf"), font_size * 7);
        title.setFont(font);

        App.getSession().close();

        if (this.winner == 'h') {
            winner_side.setText("Home");
            File file = new File("src/static/icons/player_1.jpg");                
            Image image = new Image(file.toURI().toString());
            player_icon.setImage(image);
        }
        else {
            winner_side.setText("Away");
            File file = new File("src/static/icons/player_2.jpg");                
            Image image = new Image(file.toURI().toString());
            player_icon.setImage(image);
        }
    }
    
    @FXML 
    protected void goHome(MouseEvent event) throws IOException {
        display_engine.switchScreen("Home.fxml", event);
    }
}
