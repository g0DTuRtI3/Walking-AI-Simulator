package edu.vanier.model;

import edu.vanier.core.NeuralNetwork;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.shape.Line;

/**
 *
 * @author 2248826
 */
public class Walker implements Serializable {

    private ArrayList<BasicModel> basicModels = new ArrayList<>();
    private NeuralNetwork brain;
    private static float learningRate = 0.3f;
    private int fitnessScore;
    private int id;
    private double trainedTime = 0;
    private Line ground = new Line();

    public Walker() {

    }

    public HashSet<NodeModel> getAllNodes() {
        HashSet<NodeModel> returnedNodes = new HashSet<>();
        for (BasicModel bm : this.basicModels) {
            returnedNodes.add(bm.getPrevNode());
            returnedNodes.add(bm.getNextNode());
        }

        return returnedNodes;
    }

    public HashSet<Line> getAllLinks() {
        HashSet<Line> returnedLinks = new HashSet<>();
        for (BasicModel bm : this.basicModels) {
            returnedLinks.add(bm.getLink());
        }
        return returnedLinks;
    }

    public Walker(BasicModel basicModel) {
        addBasicModel(basicModel);
    }

  

    public void setBrain(NeuralNetwork brain) {
        this.brain = brain;
    }

    public Walker(ArrayList<BasicModel> linksOfWalker, int[] layers, float learningRate) {
        this.basicModels.addAll(linksOfWalker);
        Deque<Integer> allLayersList = new ArrayDeque<>();

        for (int nbNeurons : layers) {
            allLayersList.add(nbNeurons);
        }

        allLayersList.addFirst(this.getAllNodes().size());
        allLayersList.addLast(this.getAllNodes().size());

        int[] allLayersArray = allLayersList.stream().mapToInt(Integer::intValue).toArray();

        this.brain = new NeuralNetwork(learningRate, allLayersList.stream().mapToInt(Integer::intValue).toArray());

    }

    public void setInitialXY(double[][] xyPositionsPerNode) {
        ArrayList<NodeModel> allNodes = new ArrayList<>(this.getAllNodes());

        for (int i = 0; i < allNodes.size(); i++) {
            allNodes.get(i).setCenterX(xyPositionsPerNode[i][0]);
            allNodes.get(i).setCenterY(xyPositionsPerNode[i][1]);
        }
        for (BasicModel bm : this.getBasicModels()) {
            bm.updateLink();
        }

    }

    public void addBasicModel(BasicModel basicModel) {
        basicModels.add(basicModel);
    }

    public void movePrevious(BasicModel basicModel, double force, double time) {
        //basicModel.getPrevNode().setForce(basicModel, force, time, basicModel.getNextNode());
        basicModel.updatePreviousNode(force);
    }

    public void moveNext(BasicModel basicModel, double force, double time) {
        //basicModel.getNextNode().setForce(basicModel, force, time, basicModel.getPrevNode());
        basicModel.updateNextNode(force);
    }

    public void updateWalker() {

        int count = 0;

        // Count grounded models
        for (BasicModel basicModel : basicModels) {
            for (NodeModel nodeModel : basicModel.getNodes()) {
                if (Math.round(Math.round(nodeModel.getCenterY() + nodeModel.getRadius())) == ground.getStartX()) {
                    count++;
                    nodeModel.setGrounded(true);
                }
            }
        }

        // Remove pairs
        for (int i = 0; i < basicModels.size() - 1; i++) {
            for (int j = i + 1; j < basicModels.size(); j++) {
                for (NodeModel nodeModel1 : basicModels.get(i).getNodes()) {
                    for (NodeModel nodeModel2 : basicModels.get(j).getNodes()) {
                        // Check for matches between nodes of different BasicModels
                        if (nodeModel1 == nodeModel2) {
                            count--;
                        }
                    }
                }
            }
            for (int j = i - 1; j >= 0; j--) {
                for (NodeModel nodeModel1 : basicModels.get(i).getNodes()) {
                    for (NodeModel nodeModel2 : basicModels.get(j).getNodes()) {
                        // Check for matches between nodes of different BasicModels
                        if (nodeModel1 == nodeModel2) {
                            count++;
                        }
                    }
                }
            }
        }

        for (BasicModel basicModel : basicModels) {

            if (basicModel.getNextNodeForce() == 0) {
                basicModel.updateNextNode(basicModel.getNextNodeForce());
            } else if (count > 1 && basicModel.getNextNodeForce() != 0) {
                basicModel.updateNextNode(basicModel.getNextNodeForce());
                count -= 1;
                basicModel.getNextNode().setGrounded(false);
            } else if (count == 1 && !basicModel.getNextNode().isGrounded() && basicModel.getNextNodeForce() != 0) {
                basicModel.updateNextNode(basicModel.getNextNodeForce());
                count -= 1;
                basicModel.getNextNode().setGrounded(false);
            }

            if (basicModel.getPrevNodeForce() == 0) {
                basicModel.updatePreviousNode(basicModel.getPrevNodeForce());
            } else if (count > 1 && basicModel.getPrevNodeForce() != 0) {
                basicModel.updatePreviousNode(basicModel.getPrevNodeForce());
                count -= 1;
                basicModel.getPrevNode().setGrounded(false);
            } else if (count == 1 && !basicModel.getPrevNode().isGrounded() && basicModel.getPrevNodeForce() != 0) {
                basicModel.updatePreviousNode(basicModel.getPrevNodeForce());
                count -= 1;
                basicModel.getPrevNode().setGrounded(false);
            }
            basicModel.updateLink();
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

    /**
     *
     * @param score
     */
    public void setFitnessScore(int score) {
        this.fitnessScore = score;
    }

    public static void learningRate(float learningRate) {
        Walker.learningRate = learningRate;
    }

    public double getPosition() {
        double position = 0;
        Map<NodeModel, Double> nodeMap = new HashMap<>();
        for (BasicModel bm : this.basicModels) {
            nodeMap.put(bm.getNextNode(), bm.getNextNode().getCenterX());
            nodeMap.put(bm.getPrevNode(), bm.getPrevNode().getCenterX());
        }
        return nodeMap.values().stream().mapToDouble(Double::doubleValue).average().getAsDouble();

    }

    public double getMass() {
        return this.getAllNodes().size() * NodeModel.getMass();
    }

    public ArrayList<BasicModel> getBasicModels() {
        return basicModels;
    }

    public static float getLearningRate() {
        return learningRate;
    }

    public int getFitnessScore() {

        return this.fitnessScore;
    }

    public NeuralNetwork getBrain() {
        return this.brain;
    }

    public double getTrainedTime() {
        return trainedTime;
    }

    public void setTrainedTime(double trainedTime) {
        this.trainedTime = trainedTime;
    }

    public void setBasicModels(ArrayList<BasicModel> basicModels) {
        this.basicModels = basicModels;
    }

    public ArrayList<BasicModel> getBasicModelsONLYATTRIBUTES() {
        ArrayList<BasicModel> returnedBasicModels = new ArrayList<>();
        for (BasicModel bm : this.basicModels) {
            BasicModel newBasicModel = new BasicModel(new NodeModel(bm.getPrevNode().getCenterX(), bm.getPrevNode().getCenterY(), bm.getPrevNode().getColor()),
                    new NodeModel(bm.getNextNode().getCenterX(), bm.getNextNode().getCenterY(), bm.getNextNode().getColor()), bm.getColor());
            returnedBasicModels.add(newBasicModel);
        }
        return returnedBasicModels;
    }

    public void setOpacity(double percent) {
        this.getAllLinks().forEach(link -> link.setOpacity(percent));
        this.getAllNodes().forEach(node -> node.setOpacity(percent));

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGround(Line ground) {
        this.ground = ground;
    }

}
