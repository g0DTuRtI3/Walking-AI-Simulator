package edu.vanier.map;

import java.util.ArrayList;
import javafx.scene.shape.Circle;

/**
 *
 * @author 2248826
 */
public class Walker {

    private ArrayList<ModelLink> linksOfWalker = new ArrayList<>();

    public Walker() {

    }

    public Walker(ArrayList<ModelLink> linksOfWalker) {
        this.linksOfWalker.addAll(linksOfWalker);
    }

    public void addLink(ModelLink link) {
        linksOfWalker.add(link);
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
}
