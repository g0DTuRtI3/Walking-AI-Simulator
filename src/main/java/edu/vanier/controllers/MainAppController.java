package edu.vanier.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainAppController {

    @FXML
    private Button btn_AboutUs;

    @FXML
    private Button btn_Setting;

    @FXML
    private Button btn_Simulation;

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
        mainAppLoader.setController(new EditorController());
        Pane root = mainAppLoader.load();
    }
}
