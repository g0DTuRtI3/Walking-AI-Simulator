package edu.vanier.controllers;

import edu.vanier.core.NeuralDisplay;
import edu.vanier.model.BasicModel;
import edu.vanier.model.NodeModel;
import edu.vanier.model.Walker;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.DOWN;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimulationController {

    private final double nanoTOSecond = 1000000000.0;
    private final double pxlToMeterConst = 1 / 100.0;

    private int i = 0;
    private int interval;
    private double lastXbestWalker = 0;
    private double currentInterval = 0;
    private double speedY = 0;
    private double xtranslate;
    private double ytranslate;
    private long previousTime = -1;
    private static double GRAVITY = 9.8;
    private int[] layers = {4, 8, 2};
    private double[][] initialXYPositions;

    private final String environment;
    private NeuralDisplay neuralDisplay;
    private Series<Number, Number> updateSpeed = new Series<>();
    private Series<Number, Number> updatePos = new Series<>();
    private Series<Number, Number> updateKE = new Series<>();
    private Series<String, Number> updateGeneration = new Series<>();
    private final Stage primaryStage;
    private Walker bestWalker;
    private Walker[] walkers;
    private Line ground = new Line(0, 0, 0, 0);
    private Rectangle belowGround = new Rectangle(0, 0, 0, 0);
    private Group panGroup = new Group();

    @FXML
    private Pane simulationPane;

    @FXML
    private TitledPane networkPane;

    @FXML
    private TitledPane physicGraphPane;

    @FXML
    private Button btn_BackToEditor;

    @FXML
    private LineChart<Number, Number> chart_Network;

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

    /**
     *
     * @param primaryStage
     * @param walker
     * @param nbModel
     * @param interval
     * @param learningRate
     * @param xtranslate
     * @param ytranslate
     * @param environment
     */
    public SimulationController(Stage primaryStage, Walker walker, int nbModel, int interval, float learningRate, double xtranslate, double ytranslate, String environment) {
        this.interval = interval;
        this.primaryStage = primaryStage;
        this.xtranslate = xtranslate;
        this.ytranslate = ytranslate;
        this.environment = environment;

        walkers = new Walker[nbModel];

        initialXYPositions = new double[walker.getAllNodes().size()][2];
        ArrayList<NodeModel> allNodes = new ArrayList<>(walker.getAllNodes());
        for (int i = 0; i < allNodes.size(); i++) {
            initialXYPositions[i][0] = allNodes.get(i).getCenterX();
            initialXYPositions[i][1] = allNodes.get(i).getCenterY();

        }
        boolean brainFlag = walker.getBrain() != null;
        for (int i = 0; i < nbModel; i++) {
            Walker walkerI = new Walker(walker.getBasicModelsONLYATTRIBUTES(), layers);
            if (brainFlag) {
                if (i == 0) {
                    walkerI.setBrain(walker.getBrain());
                } else {
                    walkerI.setBrain(walker.getBrain().clone());
                    walkerI.getBrain().mutate();
                }
            }
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

    /**
     * Description of Method: This method initializes the ground and the
     */
    @FXML
    void initialize() {
        determineEnvironment();

        ground.setStartX(-100000);
        ground.setEndX(100000);
        ground.setStartY(800);
        ground.setEndY(800);
        ground.setStrokeWidth(5);

        belowGround.setX(ground.getStartX());
        belowGround.setY(ground.getStartY());
        belowGround.setHeight(10000000);
        belowGround.setWidth(ground.getEndX() - ground.getStartX());

        for (Walker w : walkers) {

            tf_Time.setText(String.format("%.2f", w.getTrainedTime()));

            panGroup.getChildren().addAll(w.getAllLinks());
            panGroup.getChildren().addAll(w.getAllNodes());

            w.setOpacity(0.5);
        }

        panGroup.getChildren().addAll(belowGround, ground);
        simulationPane.getChildren().add(panGroup);
        simulationPane.setOnKeyPressed((event) -> {

            switch (event.getCode()) {

                case LEFT -> {
                    panGroup.setLayoutX(panGroup.getLayoutX() + 10);
                }
                case UP -> {
                    panGroup.setLayoutY(panGroup.getLayoutY() + 10);
                }
                case DOWN -> {
                    panGroup.setLayoutY(panGroup.getLayoutY() - 10);
                }
                case RIGHT ->
                    panGroup.setLayoutX(panGroup.getLayoutX() - 10);
            }

        });

        timer.start();
    }

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

        private double lastXbestWalker = 0;
        private double currentInterval = 0;
        private final double nanoTOSecond = 1000000000.0;
        private int i = 0;
        private final double pxlToMeterConst = 1 / 100.0;
        private Series<Number, Number> updateSpeed = new Series<>();
        private Series<Number, Number> updatePos = new Series<>();
        private Series<Number, Number> updateKE = new Series<>();
        private Series<Number, Number> updateGeneration = new Series<>();
        private double speedY = 0;

        /**
         * Description of method: This handle function updates multiple things
         * such as the Artificial Intelligence, some labels like the countdown
         * and the trained time.
         *
         */
        @Override
        public void handle(long now) {
            countdownUpdate();

            double elapsedTime = (now - previousTime) / nanoTOSecond;
            currentInterval = (now - startedTime) / nanoTOSecond;

            double bestDistance = 0;
            for (Walker walk : walkers) {
                for (NodeModel node : walk.getAllNodes()) {
                    //if (Shape.intersect(node, ground).getBoundsInParent().getWidth() != -1) {
                    if (node.intersects(ground.getBoundsInParent())) {

                    } else {

                    }

                }
                moveWalker(elapsedTime);
                walk.setTrainedTime(walk.getTrainedTime() + elapsedTime);

                tf_Time.setText(String.format("%.2f", walk.getTrainedTime()));
                if (walk.getPosition() > bestDistance) {
                    if (bestWalker != walk && bestWalker != null) {
                        bestWalker.setOpacity(0.9);
                    }
                    bestWalker = walk;
                    bestDistance = walk.getPosition();
                    bestWalker.setFitnessScore(bestWalker.getFitnessScore() + 1);
                    walk.setOpacity(1);
                }

                walk.updateWalker();
            }
            if (bestWalker != null) {

                double instantSpeed = ((bestWalker.getPosition() - lastXbestWalker)) * pxlToMeterConst / elapsedTime;

                updateSpeed.getData().add(new XYChart.Data<>(currentInterval, instantSpeed));

                updatePos.getData().add(new XYChart.Data<>(currentInterval, bestDistance * pxlToMeterConst));

                double KE = 1 / 2.0 * bestWalker.getMass() * Math.pow(instantSpeed, 2);
                updateKE.getData().add(new XYChart.Data<>(currentInterval, KE));

            }
            lastXbestWalker = bestDistance;
            if (currentInterval >= interval) {

                System.out.println("finished " + i++);
                bestWalker.setFitnessScore(bestWalker.getFitnessScore() + (int) (100 * lastXbestWalker * pxlToMeterConst));

                settingNextGeneration();
                startedTime = now;
                updateSpeed.getData().clear();
                updatePos.getData().clear();
                updateKE.getData().clear();

            }
            previousTime = now;
        }

        public void settingNextGeneration() {
            lastXbestWalker = 0;
            System.err.println("Generation " + txt_Generation.getText() + " finished");

            for (Walker w : walkers) {
                if (bestWalker.getFitnessScore() < w.getFitnessScore()) {
                    bestWalker = w;
                }
            }
            updateGeneration.getData().add(new XYChart.Data<>(Integer.valueOf(txt_Generation.getText()), bestWalker.getFitnessScore()));
            for (Walker w : walkers) {

                w.setFitnessScore(0);
                w.setInitialXY(initialXYPositions);
                if (w == bestWalker) {
                    continue;
                }
                w.setBrain(bestWalker.getBrain().clone());

                w.getBrain().mutate();
                txt_Generation.setText(String.format("%d", i + 1));

            }
        }

        @Override
        public void stop() {
            previousTime = -1;
            super.stop();
        }

        /**
         * Description of method: this method updates the countdown label.
         */
        private void countdownUpdate() {

            txt_Countdown.setText(String.format("%.0f", interval - currentInterval));
        }

    };

    /**
     * Description of method: this method updates the countdown label.
     */
    private void moveWalker(double dtime) {
        for (Walker w : walkers) {
            ArrayList<NodeModel> nodes = new ArrayList<>(w.getAllNodes());

            double[] motionOnNodes = new double[nodes.size()];
            for (int i = 0; i < nodes.size(); i++) {

                //put all force on every node in every []
//                motionOnNodes[i] = nodes.get(i).getSpeedX();
            }
            //all forces that walker will apply on every Node
            double[] predictions = w.getBrain().predict(motionOnNodes);

            // make walker move here e.g. w.update(predictions); 
            for (int i = 0; i < w.getBasicModels().size(); i++) {

                //put all force on every node in every []
                for (int j = 0; j < w.getBasicModels().size(); j++) {
//                    w.getBasicModels().get(j).updateNextNode(w.getBasicModels().get(i), predictions[j], dtime);
//                    w.getBasicModels().get(j).updatePreviousNode(w.getBasicModels().get(i), predictions[j], dtime);
                }

            }
        }
    }

    private static void WorldToScreen() {

    }

    private static void ScreenToWorld() {
    }

    /**
     * Description of method: this method displays the neuralnetwork of a
     * walker.
     */
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

            if ((e.getSceneX() < simulationPane.getLayoutX() + simulationPane.getWidth() - NeuralDisplay.getWIDTH() / 2)
                    && (e.getSceneY() < simulationPane.getLayoutY() + simulationPane.getHeight() - NeuralDisplay.getHEIGHT() / 2)) {
                neuralDisplay.setLayoutX(e.getSceneX());
                neuralDisplay.setLayoutY(e.getSceneY());
            }

        });

    }

    /**
     * Description of method: This method makes the user be able to come back to
     * the main menu.
     */
    @FXML
    void backToEditorOnAction(ActionEvent event) throws IOException {

        MainAppController.naturePlayer.stop();
        MainAppController.spacePlayer.stop();
        MainAppController.defaultPlayer.play();
        switchToEditor();
    }

    /**
     * Description of method: This method uses the editor enviroment input and
     * applies it in the simulation by setting the gravity and the background.
     */
    private void determineEnvironment() {
        switch (EditorController.environment) {
            case "Earth" -> {

                this.simulationPane.setId("Earth");
                MainAppController.naturePlayer.play();
                MainAppController.defaultPlayer.stop();

                this.belowGround.setFill(Color.GREEN);

                SimulationController.GRAVITY = 9.8;
            }
            case "Moon" -> {
                this.simulationPane.setId("Moon");
                MainAppController.spacePlayer.play();
                MainAppController.defaultPlayer.stop();
                this.belowGround.setFill(Color.LIGHTGRAY);
                SimulationController.GRAVITY = 1.6;
            }
            default -> {
                this.panGroup.setId("");
                this.belowGround.setFill(Color.LIGHTGREEN);
                SimulationController.GRAVITY = 9.8;
            }
        }
    }

    /**
     * Description of method: This method is used in the backtoeditor method to
     * stop the timer, load back the editor.
     */
    private void switchToEditor() throws IOException {
        timer.stop();
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        mainAppLoader.setController(new EditorController(primaryStage, bestWalker));
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Walking AI Simulator");
        primaryStage.show();
    }
}
