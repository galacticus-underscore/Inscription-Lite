package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

import javafx.stage.Stage;

public class GameController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML protected HBox avatar;
    @FXML protected StackPane slot1;
    @FXML protected StackPane slot2;
    @FXML protected StackPane slot3;
    @FXML protected StackPane slot4;
    
    @FXML 
    protected void nextTurn(MouseEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Confirmation.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void draw(MouseEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/components/Card.fxml"));
        avatar.getChildren().add(fxmlLoader.load());
    }
}
