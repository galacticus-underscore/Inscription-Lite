import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import models.App;
import models.Card;

public class Main extends Application {

    public static void main(String[] args) {
        // App backend = new App();
        // System.out.println(backend.getClass().getSimpleName());

        // ArrayList<SigilEffect> effects = new ArrayList<SigilEffect>();

        // effects.add(new SigilEffect() {
        //     String output = "test successful";
        //     public void applyEffect() {
        //         System.out.println(output);
        //     }
        // });

        // effects.get(0).applyEffect();

        // returns null
        // Card[] arr = new Card[4];
        // System.out.println(arr[1]);

        ArrayList<Card> effects = new ArrayList<Card>();
        System.out.println(effects.get(0));

        // launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/Game.fxml"));
    
        Scene scene = new Scene(root);
    
        stage.setTitle("Inscryption Lite");
        stage.setScene(scene);
        stage.show();
    }
}
