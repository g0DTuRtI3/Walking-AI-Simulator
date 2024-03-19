package edu.vanier.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainAppController {
    
    Stage primaryStage;

    @FXML
    private Button btn_AboutUs;

    @FXML
    private Button btn_Setting;

    @FXML
    private Button btn_Simulation;
    
    public MainAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    void initialize() {
        
    }

    @FXML
    void aboutUsOnAction(ActionEvent event) {

    }

    @FXML
    void settingOnAction(ActionEvent event) {

    }

    // Loads the Editor scene
    @FXML
    void simulationOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        mainAppLoader.setController(new EditorController(primaryStage));
        Pane root = mainAppLoader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}
