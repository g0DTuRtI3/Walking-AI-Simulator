package edu.vanier.serialization;

import edu.vanier.core.NeuralNetwork;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Zeyu Huang
 */
public class MyWalker implements Serializable{
    private ArrayList<MyBasicModel> basicModels = new ArrayList<>();
    private NeuralNetwork brain;
    private static float learningRate = 0.3f;
    private int fitnessScore;
    private int id;

    public ArrayList<MyBasicModel> getBasicModels() {
        return basicModels;
    }

    public NeuralNetwork getBrain() {
        return brain;
    }

    public static float getLearningRate() {
        return learningRate;
    }

    public int getId() {
        return id;
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    public void setBasicModels(ArrayList<MyBasicModel> basicModels) {
        this.basicModels = basicModels;
    }

    public void setBrain(NeuralNetwork brain) {
        this.brain = brain;
    }

    public static void setLearningRate(float learningRate) {
        MyWalker.learningRate = learningRate;
    }

    public void setFitnessScore(int fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    public void setId(int id) {
        this.id = id;
    }
}