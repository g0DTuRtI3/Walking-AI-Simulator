package edu.vanier.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingsController {

    Stage primaryStage;

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> musicListView;

    @FXML
    private Slider volumeSlider;

    public SettingsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void initialize() {
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            MainAppController.defaultPlayer.setVolume((double) newValue / 100.0);
            MainAppController.naturePlayer.setVolume((double) newValue / 100.0);
            MainAppController.spacePlayer.setVolume((double) newValue / 100.0);
        });
        musicListView.getItems().add("Default Music - Pixabay free to use music");
        musicListView.getItems().add("Nature Music - Pixabay free to use music");
        musicListView.getItems().add("Space Music - Pixabay free to use music");
        musicListView.getItems().add("Nature Image - https://www.pixel4k.com/2d-environment-108808.html");
        musicListView.getItems().add("Space Image - https://www.pinterest.com.au/pin/505669864389030417/");
        musicListView.getItems().add("Background Menu Image -https://unsplash.com/photos/blue-and-white-diamond-illustration-VlZYu3nZIRI");

    }

    /**
     * This method brings the use back to the main menu.
     * 
     * @param event The ActionEvent of the GUI.
     * @throws IOException 
     */
    @FXML
    void backButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
        mainAppLoader.setController(new MainAppController(primaryStage));
        Pane root = mainAppLoader.load();

        //-- 2) Create and set the scene to the stage.
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setTitle("Walking AI Simulator");
        primaryStage.show();

        primaryStage.setAlwaysOnTop(false);
    }
}
