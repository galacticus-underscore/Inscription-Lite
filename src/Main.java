import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
    
        Scene scene = new Scene(root);
        
        Font font = Font.loadFont("static/css/HEAVYWEI.TTF", 10);
    
        stage.setTitle("Inscryption Lite");
        stage.setScene(scene);
        stage.show();
    }
}


