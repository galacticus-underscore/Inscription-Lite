package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class WebViewTest implements Initializable {
    
    @FXML private WebView test;
    @FXML private WebEngine engine;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        engine = test.getEngine();
        engine.load("https://elaiah-pshs.github.io/AI-The-Inner-Workings");
    }
    
}
