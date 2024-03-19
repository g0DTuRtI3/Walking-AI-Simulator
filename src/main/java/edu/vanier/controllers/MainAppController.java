package edu.vanier.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    @FXML
    void simulationOnAction(ActionEvent event) {
        btn_Simulation.setVisible(false);
    }
}
