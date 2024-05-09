package edu.vanier.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author YOUSSEF
 */
public class BasicModel {

    private static final double STROKEWIDTH = 10;

    private final NodeModel prevNode;
    private final NodeModel nextNode;
    private final Line link = new Line();
    private Color color;
    private double prevNodeForce = 0;
    private double nextNodeForce = 0;
    private ArrayList<NodeModel> nodes = new ArrayList<>();

    public BasicModel(NodeModel prevNode, NodeModel nextNode, Color colorOfLine) {
        this.link.setStartX(prevNode.getCenterX());
        this.link.setStartY(prevNode.getCenterY());
        this.link.setEndX(nextNode.getCenterX());
        this.link.setEndY(nextNode.getCenterY());
        this.link.setStrokeWidth(STROKEWIDTH);
        this.link.setStroke(colorOfLine);
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        if (prevNode.getCenterX() < nextNode.getCenterX()) {
            prevNode.setCorrectionAngle(180);
        }else {
            nextNode.setCorrectionAngle(180);
        }
        this.color = colorOfLine;
        
        nodes.add(prevNode);
        nodes.add(nextNode);
    }

    public NodeModel getPrevNode() {
        return prevNode;
    }

    public NodeModel getNextNode() {
        return nextNode;
    }

    public Line getLink() {
        return link;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(String hexColor) {
        color = Color.web(hexColor);
    }

    public void updateLink() {
        this.link.setStartX(prevNode.getCenterX());
        this.link.setStartY(prevNode.getCenterY());
        this.link.setEndX(nextNode.getCenterX());
        this.link.setEndY(nextNode.getCenterY());
    }
    
    public void updateNextNode(double force) {
          
        if (force != 0) {
            nextNode.setForce(force, this);
            nextNodeForce = 0;
            nextNode.setCurrentTime(System.nanoTime());
            nextNode.setCurrentTime2(System.nanoTime());
        }else {
            nextNode.updateNode(this);
            nextNode.setCurrentTime2(System.nanoTime());
        }
    }
    
    public void updatePreviousNode(double force) {
        
        if (force != 0) {
            prevNode.setForce(force, this);
            prevNodeForce = 0;
            prevNode.setCurrentTime(System.nanoTime());
            prevNode.setCurrentTime2(System.nanoTime());
        }else {
            prevNode.updateNode(this);
            prevNode.setCurrentTime2(System.nanoTime());
        }
    }
    
    public void setPreviousForce(double previousForce) {
        this.prevNodeForce = previousForce;
    }

    public void setNextForce(double nextForce) {
        this.nextNodeForce = nextForce;
    }

    public double getLinkMagnitude() {
        return Math.sqrt(Math.pow((link.getStartX() - link.getEndX()), 2) + Math.pow((link.getStartY() - link.getEndY()), 2));
    }
    
    public double getPrevNodeForce() {
        return prevNodeForce;
    }

    public double getNextNodeForce() {
        return nextNodeForce;
    }
    
    public NodeModel getOtherNode(NodeModel nodeModel) {
        if (nodeModel == nextNode) {
            return prevNode;
        }

        return nextNode;
    }
    
    public NodeModel getCurrentNode(NodeModel nodeModel) {

        if (nodeModel == nextNode) {
            return nextNode;
        }

        return prevNode;
    }
    
    public ArrayList<NodeModel> getNodes() {
        return nodes;
    }
    
    public double getDeltaTime(double currentTime) {
        return System.nanoTime() - currentTime;
    }
    
}
