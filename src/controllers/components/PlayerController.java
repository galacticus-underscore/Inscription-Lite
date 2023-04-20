package controllers.components;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class PlayerController implements Initializable {
    private int player_number;

    public PlayerController(int n) {
        this.player_number = n;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // add the elements of the player here based on the player number
    }
}
