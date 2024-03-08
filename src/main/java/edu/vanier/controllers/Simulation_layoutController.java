package edu.vanier.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Simulation_layoutController {

    @FXML
    private Pane SimulationPane;

    @FXML
    private Button btn_BackToEditor;

    @FXML
    private LineChart<?, ?> chart_Network;

    @FXML
    private LineChart<?, ?> chart_Physics1;

    @FXML
    private LineChart<?, ?> chart_Physics2;

    @FXML
    private LineChart<?, ?> chart_Physics3;

    @FXML
    private TextField tf_Time;

    @FXML
    private Text txt_Countdown;

    @FXML
    private Text txt_Generation;

    @FXML
    private Text txt_IsTraining;

    @FXML
    void backToEditorOnAction(ActionEvent event) {

    }

}
