import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import models.processors.StyleProcessor;
import models.processors.LogProcessor;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        LogProcessor.start();

        // LogProcessor.log("Running preprocessing functions");
        // System.out.println();

        // StyleProcessor.compile();

        // System.out.println();
        // LogProcessor.success("Preprocessing performed successfully");
        // System.out.println();

        LogProcessor.start("Main", "start");
        
        LogProcessor.log("Loading home screen");
        Parent root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inscryption Lite");
        stage.setScene(scene);
        stage.show();
        LogProcessor.success("Home screen loaded");
    }
}
