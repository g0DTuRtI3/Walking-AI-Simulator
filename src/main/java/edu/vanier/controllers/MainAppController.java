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
    void aboutUsOnAction(ActionEvent event) throws IOException {
        FXMLLoader aboutUsLoader = new FXMLLoader(getClass().getResource("/fxml/AboutUs_Layout.fxml"));
        aboutUsLoader.setController(new AboutUsController(primaryStage));
        Pane root = aboutUsLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("AboutUs");
        primaryStage.show();
    }

    @FXML
    void settingOnAction(ActionEvent event) throws IOException {
        FXMLLoader SettingsLoader = new FXMLLoader(getClass().getResource("/fxml/Settings_layout.fxml"));
        SettingsLoader.setController(new SettingsController(primaryStage));
        Pane root = SettingsLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }

    // Loads the Editor scene
    @FXML
    void startButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        mainAppLoader.setController(new EditorController(primaryStage));
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Model Editor");
        primaryStage.show();
    }
}
