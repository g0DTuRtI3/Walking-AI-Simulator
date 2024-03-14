package edu.vanier.core;



import java.util.Arrays;

/**
 *
 * This class consists of the design of the neuralNetwork used for our project.
 */
public class NeuralNetwork {

    private HiddenLayer[] hiddenLayers;
    private double[][] activations;
    private float learningRate; // Mutation Rate
    private int[] layers;

    /**
     *
     * @param learningRate
     * @param layers
     */
    public NeuralNetwork(float learningRate, int[] layers) {

        this.learningRate = learningRate;
        this.layers = layers;

        activations = new double[layers.length][];
        hiddenLayers = new HiddenLayer[layers.length - 1];
        activations[0] = new double[layers[0]];

        // Hidden layers starts at the second neurons layer, with a previous layer as input layer?
        for (int i = 1; i < layers.length; i++) {
            activations[i] = new double[layers[i]];
            hiddenLayers[i - 1] = new HiddenLayer(layers[i], layers[i - 1]);
        }

    }

    /**
     *
     * @return
     */
    @Override
    public NeuralNetwork clone() {

        NeuralNetwork neuralNetwork = new NeuralNetwork(this.learningRate, this.layers);
        for (int i = 0; i < this.hiddenLayers.length; i++) {
            neuralNetwork.hiddenLayers[i] = this.hiddenLayers[i].clone();
        }



        return neuralNetwork;
    }

    /**
     *
     * @param input
     * @return
     */
    public double[] predict(double[] input) {

        activations[0] = input;
        for (int i = 0; i < hiddenLayers.length; i++) {
            activations[i + 1] = hiddenLayers[i].activate(activations[i]);
        }
        return activations[activations.length - 1];
    }

    /**
     *
     */
    public void mutate() {
        for (HiddenLayer layer : this.hiddenLayers) {
            layer.mutate(this.learningRate);
        }
    }

    /**
     *
     * @return
     */
    public double[][] getActivations() {
        return activations;
    }

    /**
     *
     * @return
     */
    public HiddenLayer[] getHiddenLayers() {
        return hiddenLayers;
    }

    /**
     *
     * @return
     */
    public int[] getLayers() {
        return this.layers;
    }

    /**
     *
     * @return
     */
    public String toString() {
        String ret = "learning rate : " + this.learningRate + "\n";
        ret += "Hidden Layers : " + Arrays.toString(this.hiddenLayers) + "\n";

        for (int i = 0; i < this.layers.length; i++) {

            ret += "Hidden layer " + i + Arrays.toString(this.activations[i]) + "\n";

        }

        ret += "Output layer: " + Arrays.toString(this.activations[this.activations.length - 1]) + "\n";
        return ret;
    }

}