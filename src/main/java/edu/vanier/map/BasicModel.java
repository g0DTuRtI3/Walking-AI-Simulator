package edu.vanier.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author YOUSSEF
 */
public class BasicModel {

    private static double strokeWidth = 5;

    private NodeModel prevNode;
    private NodeModel nextNode;
    private Line link;

    public BasicModel(NodeModel prevNode, NodeModel nextNode, Color colorOfLine) {
        this.link.setStartX(prevNode.getCenterX());
        this.link.setStartY(prevNode.getCenterY());
        this.link.setEndX(nextNode.getCenterX());
        this.link.setEndY(nextNode.getCenterY());
        this.link.setStrokeWidth(strokeWidth);
        this.link.setStroke(colorOfLine);
        this.prevNode = prevNode;
        this.nextNode = nextNode;
    }

    public void updateModel() {
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
    
}
