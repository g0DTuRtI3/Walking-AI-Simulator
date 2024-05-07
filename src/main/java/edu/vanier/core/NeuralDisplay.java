package edu.vanier.core;

import edu.vanier.model.Walker;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * This class consists of the visible neural display accessible in Simulation
 */
public class NeuralDisplay extends Pane {

    //to be set according to UI
    private static final int WIDTH = 600;
    //to be set according to UI
    private static final int HEIGHT = 400;
    //to be set according to UI
    private static final int PANE_PADDING = 20;
    //to be set according to UI
    private static double xLayout = 1000;
    //to be set according to UI
    private static double yLayout = 0;

    private Walker displayedWalker;
    private NeuralNetwork neuralNetwork;
    private double[][] activations;
    private HiddenLayer[] layers;

    private int[] nbLayers;

    private ArrayList<Line> lineWeights = new ArrayList();
    private ArrayList<ArrayList<Circle>> neuronList = new ArrayList();
    private static double neuronRadius = 20;

    /**
     *
     * @param walker
     */
    public NeuralDisplay(Walker walker) {

        this.setPrefSize(WIDTH, HEIGHT);
        this.displayedWalker = walker;
        this.activations = displayedWalker.getBrain().getActivations();
        this.neuralNetwork = displayedWalker.getBrain();
        this.layers = displayedWalker.getBrain().getHiddenLayers();

        nbLayers = displayedWalker.getBrain().getLayers();

        generateNeurons();
        generateWeight();
        this.setLayoutX(xLayout);
        this.setLayoutY(yLayout);

    }

    public static void setxLayout(double xLayout) {
        NeuralDisplay.xLayout = xLayout;
    }

    public static void setyLayout(double yLayout) {
        NeuralDisplay.yLayout = yLayout;
    }

    public static double getWIDTH() {
        return NeuralDisplay.WIDTH;
    }

    public static double getHEIGHT() {
        return NeuralDisplay.HEIGHT;
    }

    /**
     * Method Description: This method creates the neurons of the network, which
     * are the activations of NeuralNetwork.
     *
     */
    private void generateNeurons() {
        int numberOfLayers = activations.length;
        int disposableWidth = WIDTH - 2 * PANE_PADDING;
        int disposableHeight = HEIGHT - 2 * PANE_PADDING;
        int layerGap = disposableWidth / (numberOfLayers + 1);

        for (int i = 0; i < activations.length; i++) {
            neuronList.add(new ArrayList<>());
            int heighGap = disposableHeight / (activations[i].length + 1);
            for (int j = 0; j < activations[i].length; j++) {

                Label value = new Label();
                value.setId("neuronNet");
                value.setStyle("-fx-text-fill: white;");
                DoubleProperty prop = new SimpleDoubleProperty(activations[i][j]);

                value.textProperty().bind(prop.asString("%.2f"));

                Circle neuron = new Circle(neuronRadius);
                neuron.setUserData(prop);

                neuron.setCenterX(layerGap * (i + 0.5) - (this.getWidth()));
                neuron.setCenterY(heighGap * (j + 0.5) - (this.getHeight()));
                value.setTranslateX(layerGap * (i + 0.5) - (10));
                value.setTranslateY(heighGap * (j + 0.5) - (5));

                this.getChildren().addAll(neuron, value);
                neuronList.get(i).add(neuron);

            }
        }

    }

    /**
     * Method Description: this method generates the weights by creating a line
     * to every neuron(activations) that is in the NeuralNetwork.
     *
     * The line represents the weight between two neurons.
     */
    private void generateWeight() {

        for (int layer = 0; layer < layers.length; layer++) {

            for (int currNeuron = 0; currNeuron < layers[layer].getWeights().length; currNeuron++) {

                for (int prevNeuron = 0; prevNeuron < activations[layer].length; prevNeuron++) {

                    double k = layers[layer].getWeights()[currNeuron][prevNeuron];
                    k = Math.abs(k);
                    Line line = new Line();

                    DoubleProperty value = new SimpleDoubleProperty(k);
                    line.setUserData(value);
                    line.startXProperty().bind(neuronList.get(layer + 1).get(currNeuron).centerXProperty());
                    line.startYProperty().bind(neuronList.get(layer + 1).get(currNeuron).centerYProperty());
                    line.endXProperty().bind(neuronList.get(layer).get(prevNeuron).centerXProperty());
                    line.endYProperty().bind(neuronList.get(layer).get(prevNeuron).centerYProperty());

                    line.opacityProperty().bind((value.divide(2)).add(0.5));
                    line.strokeWidthProperty().bind(((value.divide(1.5)).add(0.5)).multiply(3));

                    this.getChildren().add(line);
                    line.toBack();

                }
            }

        }
    }

    /**
     *
     * @return
     */
    public Walker getDisplayedWalker() {
        return this.displayedWalker;
    }

}
