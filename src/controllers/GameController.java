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

// import javafx.scene.layout.HBox;
// import javafx.scene.layout.StackPane;
// import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import models.App;
import models.Session;
import models.exceptions.DeadAvatarException;
import models.exceptions.DeadCharacterException;
import models.exceptions.PointerConversionException;
import models.exceptions.ZeroHealthException;

public class GameController implements Initializable {
    private Session session = App.getSession();
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    // @FXML protected HBox avatar;
    // @FXML protected StackPane slot1;
    // @FXML protected StackPane slot2;
    // @FXML protected StackPane slot3;
    // @FXML protected StackPane slot4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        session.nextPlayer();
    }
    
    @FXML 
    protected void nextTurn(MouseEvent e) throws IOException, DeadAvatarException, DeadCharacterException, PointerConversionException, ZeroHealthException {
        session.endTurn();
        
        root = FXMLLoader.load(getClass().getResource("../views/Confirmation.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // @FXML
    // protected void draw(MouseEvent e) throws IOException {
    //     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/components/Card.fxml"));
    //     avatar.getChildren().add(fxmlLoader.load());
    // }

    // @FXML 
    // protected void highlight(MouseEvent e){
    //     ((ImageView)e.getSource()).getStyleClass().add("pane");
    // }

    // @FXML 
    // protected void unhighlight(MouseEvent e){
    //     ((ImageView)e.getSource()).getStyleClass().add("pane");
    // }
}
