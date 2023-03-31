package controllers;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

public class ConfirmController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML 
    protected void startTurn(MouseEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Game.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
