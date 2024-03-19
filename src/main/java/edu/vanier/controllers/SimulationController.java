package edu.vanier.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimulationController {

    Stage primaryStage;

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

    public SimulationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void backToEditorOnAction(ActionEvent event) throws IOException {
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
