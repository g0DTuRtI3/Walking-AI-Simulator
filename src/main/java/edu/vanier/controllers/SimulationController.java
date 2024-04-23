package edu.vanier.controllers;

import edu.vanier.core.NeuralDisplay;
import edu.vanier.map.BasicModel;
import edu.vanier.map.NodeModel;
import edu.vanier.map.Walker;
import java.io.IOException;
import java.util.HashSet;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private TitledPane networkPane;

    @FXML
    private TitledPane physicGraphPane;

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
    @FXML
    private VBox physicalGraphVBox;
    private int[] layers = {4, 8, 2};
    private long previousTime = -1;

    AnimationTimer timer = new AnimationTimer() {
        float startedTime = -1;

        @Override
        public void start() {
            updateSpeed.setName("Best Walker");
            updatePos.setName("Best Walker");
            updateKE.setName("Best Walker");
            updateGeneration.setName("Generation Of Walkers");
            chart_Physics1.setCreateSymbols(false);
            chart_Physics1.setCreateSymbols(false);
            chart_Physics1.setCreateSymbols(false);
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
                    walk.setOpacity(1);
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
                bestWalker.setFitnessScore(bestWalker.getFitnessScore() + (int) (100 * lastXbestWalker * pxlToMeterConst));
                System.out.println(bestWalker.getFitnessScore());
                settingNextGeneration(bestWalker, updateGeneration);
                startedTime = now;
                updateSpeed.getData().clear();
                updatePos.getData().clear();
                updateKE.getData().clear();

            }
            previousTime = now;
        }

        public void settingNextGeneration(Walker best, Series<String, Number> updateGeneration) {
            lastXbestWalker = 0;
            System.err.println("Generation " + txt_Generation.getText() + " finished");

            updateGeneration.getData().add(new XYChart.Data<>(txt_Generation.getText(), best.getFitnessScore()));

            txt_Generation.setText(String.format("%d", Integer.parseInt(txt_Generation.getText()) + 1));
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
            Walker walkerI = new Walker(walker.getBasicModelsONLYATTRIBUTES(), layers);
            walkerI.learningRate(learningRate);
            walkers[i] = walkerI;

            for (BasicModel b : walkers[i].getBasicModels()) {

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

    private void moveWalker() {

        for (Walker w : walkers) {
            HashSet<NodeModel> nodes = w.getAllNodes();

            double[] forcesOnNodes = new double[nodes.size()];
            for (int i = 0; i < w.getAllNodes().size(); i++) {

                //put all force on every node in every []
                forcesOnNodes[i] = 10 * Math.random();

            }
            //all forces that walker will apply on every Node
            double[] predictions = w.getBrain().predict(forcesOnNodes);

            // make walker move here e.g. w.update(predictions); 
        }
    }

    private static void WorldToScreen() {

    }

    private static void ScreenToWorld() {
    }

    private void showNeuralDisplay(Walker walker) {
        if (simulationPane.getChildren().contains(neuralDisplay)) {
            simulationPane.getChildren().remove(neuralDisplay);
            neuralDisplay = new NeuralDisplay(walker);
            simulationPane.getChildren().add(neuralDisplay);

        } else {

            neuralDisplay = new NeuralDisplay(walker);
            simulationPane.getChildren().add(neuralDisplay);
        }
        neuralDisplay.setOnMouseDragged(e -> {
            System.out.println(simulationPane.getWidth() - NeuralDisplay.getWIDTH() / 2);
            System.out.println(e.getSceneX());
            if ((e.getSceneX() < simulationPane.getLayoutX() + simulationPane.getWidth() - NeuralDisplay.getWIDTH() / 2)
                    && (e.getSceneY() < simulationPane.getLayoutY() + simulationPane.getHeight() - NeuralDisplay.getHEIGHT() / 2)) {
                neuralDisplay.setLayoutX(e.getSceneX());
                neuralDisplay.setLayoutY(e.getSceneY());
            }

        });

    }

    @FXML
    void initialize() {
        double realXTransition = walkers[0].getBasicModels().get(0).getPrevNode().getCenterX() - xtranslate;
        double realYTransition = walkers[0].getBasicModels().get(0).getPrevNode().getCenterY() - ytranslate;

        for (Walker w : walkers) {
            w.setTranslateX(initialXPos);
            w.setTranslateY(initialYPos);

            tf_Time.setText(String.format("%.2f", w.getTrainedTime()));
            /*for (BasicModel b : w.getBasicModels()) {

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
            }*/
            simulationPane.getChildren().addAll(w.getAllLinks());
            simulationPane.getChildren().addAll(w.getAllNodes());
            w.setOpacity(0.5);
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
