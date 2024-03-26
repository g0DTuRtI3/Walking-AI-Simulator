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

    public ArrayList<BasicModel> getModel() {
        return this.basicModels;
    }
;
}
