package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.application.Platform;

import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;

import models.App;
import models.engines.DisplayEngine;
import models.processors.StyleProcessor;

public class HomeController implements Initializable {
    private DisplayEngine display_engine = new DisplayEngine();

    @FXML private Text title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int font_size = StyleProcessor.getFontSize();
        Font font = Font.loadFont(getClass().getResourceAsStream("../static/fonts/Heavyweight.ttf"), font_size * 10);
        title.setFont(font);
    }
    
    @FXML 
    protected void startGame(MouseEvent event) throws IOException {
        App.getSession().open(0, 1);
        display_engine.switchScreen("Confirmation.fxml", event);
    }

    @FXML 
    protected void close(MouseEvent e) throws IOException {
        Platform.exit();
    }
}
