package edu.vanier.controllers;

import edu.vanier.core.NeuralDisplay;
import edu.vanier.map.BasicModel;
import edu.vanier.map.Walker;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimulationController {

    Stage primaryStage;

    public Walker[] walkers;

    public NeuralDisplay neuralDisplay;

    public int interval;

    public static final double initialXPos = 45.0;
    public static final double initialYPos = 499.0;
    public AnimationTimer time = new AnimationTimer() {
        @Override
        public void handle(long now) {

        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
        }
    };
    @FXML
    private Pane simulationPane;

    @FXML
    private Button btn_BackToEditor;

    @FXML
    private LineChart<String, Number> chart_Network;

    @FXML
    private LineChart<Number, Number> chart_Physics1;

    @FXML
    private LineChart<Number, Number> chart_Physics2;

    @FXML
    private LineChart<Number, Number> chart_Physics3;

    @FXML
    private TextField tf_Time;

    @FXML
    private Text txt_Countdown;

    @FXML
    private Text txt_Generation;

    @FXML
    private Text txt_IsTraining;

    private int[] layers = {4, 8, 3};
    private long previousTime = -1;

    AnimationTimer timer = new AnimationTimer() {
        float startedTime = -1;

        @Override
        public void start() {
            chart_Physics1.getData().add(updateSpeed);
            chart_Physics3.getData().add(updatePos);
            chart_Physics2.getData().add(updateKE);
            chart_Network.getData().add(updateGeneration);
            
            previousTime = System.nanoTime();
            startedTime = previousTime;
            super.start();
        }
        private Walker bestWalker = null;
        private double lastXbestWalker = 0;
        private double currentInterval = 0;
        private final double nanoTOSecond = 1000000000.0;
        private int i = 0;
        private final double pxlToMeterConst = 1 / 100;
        private Series<Number, Number> updateSpeed = new Series<>();
        private Series<Number, Number> updatePos = new Series<>();
        private Series<Number, Number> updateKE = new Series<>();
        private Series<String, Number> updateGeneration = new Series<>();

        @Override
        public void handle(long now) {
            countdownUpdate(now);

            double elapsedTime = (now - previousTime) / nanoTOSecond;
            currentInterval = (now - startedTime) / nanoTOSecond;
            
            double bestDistance = 0;
            for (Walker walk : walkers) {
                walk.setTrainedTime(walk.getTrainedTime() + elapsedTime);
                moveWalker();
                tf_Time.setText(String.format("%.2f", walk.getTrainedTime()));
                if (walk.getPosition() > bestDistance) {
                    bestWalker = walk;
                    bestDistance = walk.getPosition();
                    bestWalker.setFitnessScore(bestWalker.getFitnessScore() + 1);
                }

//                for (BasicModel model : walk.getBasicModels()) {
//                    if (model.getPrevNode().getCenterX() >= model.getPrevNode().getCenterX()) {
//                        if (model.getPrevNode().getCenterX() >= bestDistance) {
//                            bestWalker = walk;
//                            bestDistance = model.getPrevNode().getCenterX();
//                            
//                        }
//                    } else {
//                        if (model.getNextNode().getCenterX() >= bestDistance) {
//                            bestWalker = walk;
//                            bestDistance = model.getPrevNode().getCenterX();
//                        }
//                    }
//                }
                walk.updateWalker();
            }
            if (bestWalker != null) {

                double instantSpeed = ((bestWalker.getPosition() - lastXbestWalker)) * pxlToMeterConst / elapsedTime;
                updateSpeed.getData().add(new XYChart.Data<>(currentInterval, instantSpeed));

                updatePos.getData().add(new XYChart.Data<>(currentInterval, bestDistance * pxlToMeterConst));

                double KE = 1 / 2 * bestWalker.getMass() * Math.pow(instantSpeed, 2);
                updateKE.getData().add(new XYChart.Data<>(currentInterval, KE));

            }
            lastXbestWalker = bestDistance;
            if (currentInterval >= interval) {
                
                System.out.println("finished " + i++);
                bestWalker.setFitnessScore((int) (100 * lastXbestWalker * pxlToMeterConst));
                settingNextGeneration(bestWalker, updateGeneration);
                startedTime = now;
                updateSpeed.getData().clear();
                updatePos.getData().clear();
                updateKE.getData().clear();

            }
            previousTime = now;
        }

        @Override
        public void stop() {
            previousTime = -1;
            super.stop();
        }

        private void countdownUpdate(long now) {

            txt_Countdown.setText(String.format("%.0f", interval - currentInterval));
        }

        

    };
    
    private double xtranslate;
    private double ytranslate;

    public SimulationController(Stage primaryStage, Walker walker, int nbModel, int interval, float learningRate, double xtranslate, double ytranslate) {
        this.interval = interval;
        this.primaryStage = primaryStage;
        this.xtranslate = xtranslate;
        this.ytranslate = ytranslate;
        walkers = new Walker[nbModel];

        for (int i = 0; i < nbModel; i++) {
            Walker walkerI = new Walker(walker.getBasicModels(), layers);
            walkerI.learningRate(learningRate);
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

    public void settingNextGeneration(Walker best, Series<String, Number> updateGeneration) {
        System.err.println("Generation " + this.txt_Generation.getText() + " finished");
        
        updateGeneration.getData().add(new XYChart.Data<>(this.txt_Generation.getText(), best.getFitnessScore()));
        

        this.txt_Generation.setText(String.format("%d", Integer.parseInt(this.txt_Generation.getText()) + 1));
        for (Walker w : walkers) {

            w.setFitnessScore(0);
            w.setTranslateX(800);
            w.setTranslateY(700);

            if (w == best) {
                continue;
            }
            w.setBrain(best.getBrain().clone());

            w.getBrain().mutate();

        }
    }
    private void moveWalker() {
            for (Walker w : walkers) {

            double[] forcesOnNodes = new double[w.getBasicModels().size()];

            for (int i = 0; i < w.getBasicModels().size(); i++) {
               //put all force on every node in every []
            }
            //all forces that walker will apply on every Node
            double[] predictions = w.getBrain().predict(forcesOnNodes);

          // make walker move here e.g. w.update(predictions); 

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

        double realXTransition = walkers[0].getBasicModels().get(0).getPrevNode().getCenterX() - xtranslate;
        double realYTransition = walkers[0].getBasicModels().get(0).getPrevNode().getCenterY() - ytranslate;

        for (Walker w : walkers) {
            w.setTranslateX(initialXPos);
            w.setTranslateY(initialYPos);
            tf_Time.setText(String.format("%.2f", w.getTrainedTime()));
            for (BasicModel b : w.getBasicModels()) {

                if (!simulationPane.getChildren().contains(b.getNextNode())) {
                    simulationPane.getChildren().addAll(b.getLink(), b.getNextNode(), b.getPrevNode());
                    b.getNextNode().setTranslateX(-realXTransition);
                    b.getNextNode().setTranslateY(-realYTransition);
                    b.getPrevNode().setTranslateX(-realXTransition);
                    b.getPrevNode().setTranslateY(-realYTransition);

                } else if (!simulationPane.getChildren().contains(b.getPrevNode())) {
                    simulationPane.getChildren().addAll(b.getLink(), b.getPrevNode());

                    b.getPrevNode().setTranslateX(-realXTransition);
                    b.getPrevNode().setTranslateY(-realYTransition);
                } else if (!simulationPane.getChildren().contains(b.getLink())) {
                    simulationPane.getChildren().addAll(b.getLink());
                }
            }
        }
        timer.start();
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
