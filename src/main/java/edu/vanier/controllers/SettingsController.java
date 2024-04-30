package edu.vanier.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;

public class SettingsController {

    Stage primaryStage;
    @FXML
    private Button backButton;

    @FXML
    private ListView<?> musicListView;

    @FXML
    private Slider volumeSlider;

    public SettingsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {

    }

    @FXML
    void volumeSliderDragDroppedAction(DragEvent event) {

    }

}
