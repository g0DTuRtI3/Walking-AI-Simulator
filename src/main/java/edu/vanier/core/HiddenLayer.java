package edu.vanier.core;

import edu.vanier.model.Walker;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * Class which consists of the hidden layer used in the machine learning model.
 */
public class HiddenLayer implements Serializable{

    private double[][] weights;
    private int currentLayerSize;
    private int previousLayerSize;

    private Random random = new Random();

    public HiddenLayer(int currentLayerSize, int previousLayerSize) {
        this.currentLayerSize = currentLayerSize;
        this.previousLayerSize = previousLayerSize;
        weights = new double[currentLayerSize][previousLayerSize];
        initRandom();
    }

   
    
    
    
    

    /**
     *
     * @return all the parameters of the HiddenLayer object
     */
    @Override
    public String toString() {
        String ret = "[";

        for (double currentLayerIndex[] : this.weights) {
            for (double value : currentLayerIndex) {
                ret += value + ", ";
            }
            ret += "]\n";
        }

        return ret;
    }

    /**
     * Method Description: This method copy the value of every HiddenLayer by
     * using a double for loop.
     *
     * @return HiddenLayer This new hiddenLayer has the same propreties
     * (weights) as the hiddenLayer that is clone to.
     *
     */
    @Override
    public HiddenLayer clone() {
        HiddenLayer hiddenLayer = new HiddenLayer(this.currentLayerSize, this.previousLayerSize);
        for (int i = 0; i < this.currentLayerSize; i++) {
            for (int j = 0; j < this.previousLayerSize; j++) {
                hiddenLayer.weights[i][j] = this.weights[i][j];
            }
        }

        return hiddenLayer;
    }

    /**

     * Method Description: This method is used to mutate the weights of the
     * HiddenLayer according to the NeuralNetwork's learning rate using a
     * randomness.
     *
     * @param learningRate is the rate a which the neural networks learns, if it
     * is higher it will improve faster, but its most efficient generation won't
     * be as efficient as another neural network with lower learning rate.
     */
    public void mutate(float learningRate) {
        for (int i = 0; i < currentLayerSize; i++) {
            for (int j = 0; j < previousLayerSize; j++) {
                if (random.nextDouble() < learningRate) {
                    // Or Randomize the weight completely
                    weights[i][j] = random.nextDouble(-1, 1);
                }
            }
        }
    }

    /**
     * Method Description: This method is used when creating the hiddenLayers
     * the first time it initializes a value between -1 and 1 for the
     * activation.
     *
     */
    private void initRandom() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = 2 * Math.random() - 1; // [-1 .. 1)
            }
        }
    }

    /**
     * Method Description: This method is used to activate the weights using
     * hyperbolic tangent function, a commonly used function in Machine learning
     * algorithms.
     *
     * @param input is the forces input in each of the nodes of the model. a
     * negative force means that it is going the opposite direction.
     * @return a double[], which is an array that is passed on the next set of
     * activations in NeuralNetwork.
     */
    public double[] activate(double[] input) {
        double[] output = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            output[i] = ActivationFunctions.tanh(Walker.multiplyVectors(input, weights[i]));
        }

        return output;
    }

    

    /**
     *
     * @return the weights of the HiddenLayer
     */
    public double[][] getWeights() {
        return weights;
    }
}
