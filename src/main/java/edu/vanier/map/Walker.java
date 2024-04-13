package edu.vanier.map;

import edu.vanier.core.NeuralNetwork;
import java.util.ArrayList;

/**
 *
 * @author 2248826
 */
public class Walker {

    private ArrayList<BasicModel> basicModels = new ArrayList<>();
    private NeuralNetwork brain;

    public Walker() {

    }

    public Walker(ArrayList<BasicModel> basicModels) {
        this.basicModels.addAll(basicModels);
    }
    
    public Walker(BasicModel basicModel) {
        addBasicModel(basicModel);
    }
    
    public NeuralNetwork getBrain() {
        return this.brain;
    }

    public void addBasicModel(BasicModel basicModel) {
        basicModels.add(basicModel);
    }

    public void movePrevious(BasicModel basicModel, double force, double time) {
        //basicModel.getPrevNode().setForce(basicModel, force, time, basicModel.getNextNode());
        basicModel.updateNextNode(basicModel, force, time);
    }

    public void moveNext(BasicModel basicModel, double force, double time) {
        //basicModel.getNextNode().setForce(basicModel, force, time, basicModel.getPrevNode());
        basicModel.updatePreviousNode(basicModel, force, time);
    }

    public void updateWalker() {

        for (BasicModel basicModel : basicModels) {
            basicModel.updateLink();
        }
    }

    public ArrayList<BasicModel> getBasicModel() {
        return this.basicModels;
    }

}
