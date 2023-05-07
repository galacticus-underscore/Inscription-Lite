package models.engines;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import models.App;
import models.processors.LocationProcessor;

public class DisplayEngine {
    private LocationProcessor location_processor;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private Node highlighted;
    private Text home_error;
    private Text away_error;

    public DisplayEngine(LocationProcessor l) {
        this.location_processor = l;
        this.home_error = (Text)this.location_processor.toNode(3, 0);
        this.away_error = (Text)this.location_processor.toNode(0, 0);
    }

    public DisplayEngine() {
        ;
    }

    public void switchScreen(String path, MouseEvent trigger) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../views/" + path));
        stage = (Stage)((Node)trigger.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScreen(String path, MouseEvent trigger, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../views/" + path));
        fxmlLoader.setController(controller);
        root = fxmlLoader.load();
        stage = (Stage)((Node)trigger.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Node getHighlighted() {
        return this.highlighted;
    }
    
    public void highlight(Node n) {
        this.highlighted = n;
        this.highlighted.getStyleClass().add("pane");
    }

    public void unhighlight() {
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

        switch (App.getSession().getPlayingSide()) {
            case 'h':
                home_error.setText(e.getMessage());
                break;
            case 'a':
                away_error.setText(e.getMessage());
                break;
        }
    }

    public void displayError(Exception e, char side) {
        if (highlighted != null)
            unhighlight();

        switch (side) {
            case 'h':
                home_error.setText(e.getMessage());
                break;
            case 'a':
                away_error.setText(e.getMessage());
                break;
        }
    }

    public void removeErrors() {
        try {
            if (!home_error.getText().isEmpty())
                home_error.setText("");
            if (!away_error.getText().isEmpty())
                away_error.setText("");
        }
        catch (NullPointerException e) {
            ;
        }
    }
}
