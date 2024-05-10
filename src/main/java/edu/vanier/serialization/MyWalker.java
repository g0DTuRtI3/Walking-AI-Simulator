package edu.vanier.serialization;

import edu.vanier.core.NeuralNetwork;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used to store the important information from the walker. This makes the walker class serializable.
 * 
 * @author Zeyu Huang
 */
public class MyWalker implements Serializable{
    private static float learningRate = 0.3f;
    
    private ArrayList<MyBasicModel> basicModels = new ArrayList<>();
    private NeuralNetwork brain;
    private int fitnessScore;
    private int id;

    public MyWalker(NeuralNetwork brain, int fitnessScore, int id) {
        this.brain = brain;
        this.fitnessScore = fitnessScore;
        this.id = id;
    }

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

    public static void setLearningRate(float learningRate) {
        MyWalker.learningRate = learningRate;
    }
}