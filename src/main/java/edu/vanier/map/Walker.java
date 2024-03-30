package edu.vanier.map;

import edu.vanier.core.NeuralNetwork;
import edu.vanier.map.NodeModel;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author 2248826
 */
public class Walker {

    private ArrayList<BasicModel> basicModels = new ArrayList<>();
    private NeuralNetwork brain;
    private static float learningRate = 0.3f;
    private int fitnessScore;

    public Walker() {

    }

    public NeuralNetwork getBrain() {
        return this.brain;
    }

    public Walker(ArrayList<BasicModel> linksOfWalker) {
        this.basicModels.addAll(linksOfWalker);
    }

    public void addLink(BasicModel link) {
        basicModels.add(link);
    }

    public void movePreviousLeft(Circle previousNode, Circle nextNode, Circle link) {
//        previousNode.setCenterY(nextNode.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
//        previousNode.setCenterX(nextNode.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
//        link.setStartY(nextNode.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
//        link.setStartX(nextNode.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
    }

    public void movePreviousRight(Circle previousNode, Circle nextNode, Circle link) {

    }

    public void moveNextLeft(Circle previousNode, Circle nextNode, Circle link) {

    }

    public void moveNextRight(Circle previousNode, Circle nextNode, Circle link) {

    }

    public void updateWalker() {

        for (BasicModel basicModel : basicModels) {
            basicModel.updateModel();
        }
    }

    public static double multiplyVectors(double[] a, double[] b) {

        if (a.length != b.length) {
            throw new IllegalArgumentException("Input vectors must have the same length");
        }

        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }

        return result;
    }

    /**
     *
     * @param velocity
     */
    /**
     *
     * @param learningRate
     */
    public static void setLearningRate(float learningRate) {
        Walker.learningRate = learningRate;
    }

    public int getFitnessScore() {

        return this.fitnessScore;
    }

    /**
     *
     * @param score
     */
    public void setFitnessScore(int score) {
        this.fitnessScore = score;
    }

    public ArrayList<BasicModel> getBasicModels() {
        return basicModels;
    }

    public static float getLearningRate() {
        return learningRate;
    }
}
