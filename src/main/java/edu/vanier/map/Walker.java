package edu.vanier.map;

import edu.vanier.core.NeuralNetwork;
import edu.vanier.map.NodeModel;
import edu.vanier.physics.Vector2D;
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

    public void movePrevious(BasicModel basicModel, Vector2D force) {
        basicModel.getPrevNode().setForce(force);
    }

    public void moveNext(BasicModel basicModel, Vector2D force) {
        basicModel.getPrevNode().setForce(force);
    }

    public void updateWalker() {

        for (BasicModel basicModel : basicModels) {
            basicModel.updateModel();
        }
    }

    public ArrayList<BasicModel> getBasicModel() {
        return this.basicModels;
    }
;
}
