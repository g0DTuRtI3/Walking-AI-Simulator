package edu.vanier.controllers;

import edu.vanier.core.NeuralDisplay;
import edu.vanier.map.BasicModel;
import edu.vanier.map.Walker;
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

    public Walker[] walkers;

    public NeuralDisplay neuralDisplay;

    @FXML
    private Pane simulationPane;

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

    private int[] layers = {2,4,6};
    
    public SimulationController(Stage primaryStage, Walker walker, int nbModel) {
        this.primaryStage = primaryStage;
        walkers = new Walker[nbModel];
        for (int i = 0; i < nbModel; i++) {
            Walker walkerI = new Walker(walker.getBasicModels(),layers);
            
            walkers[i] = walkerI;

            for (BasicModel b : walkers[i].getBasicModels()) {
                //simulationPane.getChildren().addAll(b.getLink(), b.getNextNode(), b.getPrevNode());
                b.getLink().setOnMouseClicked(mouseE -> {
                    showNeuralDisplay(walkerI);
                });
                b.getNextNode().setOnMouseClicked(mouseE -> {
                    showNeuralDisplay(walkerI);
                });
                b.getPrevNode().setOnMouseClicked(mouseE -> {
                    showNeuralDisplay(walkerI);
                });
            }

        }
    }

    private void showNeuralDisplay(Walker walker) {
        
        if (simulationPane.getChildren().contains(neuralDisplay)) {
            simulationPane.getChildren().remove(neuralDisplay);
            neuralDisplay = new NeuralDisplay(walker);
            simulationPane.getChildren().add(neuralDisplay);
            System.out.println("new show");
        } else {
            System.out.println("new");
            neuralDisplay = new NeuralDisplay(walker);
            simulationPane.getChildren().add(neuralDisplay);
        }

    }

    @FXML
    void initialize() {
        for (Walker w : walkers) {
            for (BasicModel b : w.getBasicModels()) {
                
                if (!simulationPane.getChildren().contains(b.getNextNode())) {
                    simulationPane.getChildren().addAll(b.getLink(), b.getNextNode(), b.getPrevNode());
                } else if(!simulationPane.getChildren().contains(b.getPrevNode())){
                    simulationPane.getChildren().addAll(b.getLink(), b.getPrevNode());
                }
                else if(!simulationPane.getChildren().contains(b.getLink()))
                    simulationPane.getChildren().addAll(b.getLink());
            }
        }

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
        primaryStage.setTitle("Model Editor");
        primaryStage.show();
    }

}
