package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;

import models.processors.StyleProcessor;

public class ConfirmController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private Text title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int font_size = StyleProcessor.getFontSize();
        Font font = Font.loadFont(getClass().getResourceAsStream("../static/fonts/Heavyweight.ttf"), font_size * 7);
        title.setFont(font);
    }
    
    @FXML 
    protected void startTurn(MouseEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Game.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
