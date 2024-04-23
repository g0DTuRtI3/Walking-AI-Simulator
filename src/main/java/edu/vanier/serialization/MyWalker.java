package edu.vanier.serialization;

import edu.vanier.core.NeuralNetwork;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    public void serialize(Object walker, String file) throws IOException{
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(walker);
        
        fos.close();
    }
    
    public Object deserialize(String file) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
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
}