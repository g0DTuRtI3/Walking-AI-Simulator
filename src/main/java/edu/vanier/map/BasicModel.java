package edu.vanier.map;

import java.io.Serializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author YOUSSEF
 */
public class BasicModel implements Serializable{

    private static double strokeWidth = 10;

    private NodeModel prevNode;
    private NodeModel nextNode;
    private Line link = new Line();
    private Color color;

    public BasicModel(NodeModel prevNode, NodeModel nextNode, Color colorOfLine) {
        this.link.setStartX(prevNode.getCenterX());
        this.link.setStartY(prevNode.getCenterY());
        this.link.setEndX(nextNode.getCenterX());
        this.link.setEndY(nextNode.getCenterY());
        this.link.setStrokeWidth(strokeWidth);
        this.link.setStroke(colorOfLine);
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        this.color = colorOfLine;
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

    public void updateLink() {
        this.link.setStartX(prevNode.getCenterX());
        this.link.setStartY(prevNode.getCenterY());
        this.link.setEndX(nextNode.getCenterX());
        this.link.setEndY(nextNode.getCenterY());
    }

    public double getRadAngle() {
        System.out.println("in rad angle");
        System.out.println(nextNode.getCenterX());
        double adjacent = nextNode.getCenterX()- prevNode.getCenterX();
        double opposite = nextNode.getCenterY() - nextNode.getCenterY();
        double angleRad;
        try {
            angleRad = Math.atan(Math.abs(opposite / adjacent));
        } catch (Exception e) {
            angleRad = Math.PI / 2;
        }

        if (adjacent < 0 || opposite < 0) {
            return angleRad + Math.PI;
        } else if (adjacent < 0) {
            return Math.PI - angleRad;
        } else if (opposite < 0) {
            return (2 * Math.PI) - angleRad;
        }
        
        return angleRad;
    }
    
    public void updateNextNode(BasicModel basicModel, double force, double time) {
        
        nextNode.setForce(force, time, basicModel);
        updateLink();
        
    }
    
    public void updatePreviousNode(BasicModel basicModel, double force, double time) {
        
        prevNode.setForce(force, time, basicModel);
        updateLink();
        
    }
    
    public NodeModel getNode(NodeModel nodeModel) {
        
        if (nodeModel == nextNode) {
            return nextNode;
        }
        
        return prevNode;
    }
    
    public NodeModel getOtherNode(NodeModel nodeModel) {
        
        if (nodeModel == nextNode) {
            return prevNode;
        }
        
        return nextNode;
    }
    
    public double getLinkMagnitude() {
        return Math.sqrt(Math.pow((link.getStartX()-link.getEndX()),2) + Math.pow((link.getStartY()-link.getEndY()),2));
    }
    
}
