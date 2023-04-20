package controllers;

import models.App;
import models.processors.StyleProcessor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.application.Platform;

import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;


public class HomeController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private Text title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int font_size = StyleProcessor.getFontSize();
        Font font = Font.loadFont(getClass().getResourceAsStream("../static/fonts/Heavyweight.ttf"), font_size * 10);
        title.setFont(font);
    }
    
    @FXML 
    protected void startGame(MouseEvent e) throws IOException {
        App.startSession();
        
        root = FXMLLoader.load(getClass().getResource("../views/Confirmation.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML 
    protected void close(MouseEvent e) throws IOException {
        Platform.exit();
    }
}
