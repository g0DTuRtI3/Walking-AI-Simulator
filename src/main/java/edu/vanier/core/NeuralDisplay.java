package edu.vanier.core;

import edu.vanier.map.Walker;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * This class consists of the visible neural display accesible in Simulation
 */
public class NeuralDisplay extends Pane {

    //to be set according to UI
    private static final int WIDTH = 600;
    //to be set according to UI
    private static final int HEIGHT = 400;
    //to be set according to UI
    private static final int PANE_PADDING = 20;
     //to be set according to UI
    private static double xLayout = 0;
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
     * @param car
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
                    

                    line.setOnMouseClicked((e) -> {
                        System.out.println(value.get());
                    });
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
    public Walker getDisplayedCar() {
        return this.displayedWalker;
    }

    
}
