package edu.vanier.map;

import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */
public class ModelLink extends javafx.scene.shape.Line {

    private static double strokeWidth = 5;

    private NodeModel prevNode;
    private NodeModel nextNode;

    public ModelLink(NodeModel prevNode, NodeModel nextNode, Color colorOfLine) {
        super(prevNode.getCenterX(), prevNode.getCenterY(), nextNode.getCenterX(), nextNode.getCenterY());
        super.setStrokeWidth(strokeWidth);
        super.setStroke(colorOfLine);
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        
        System.out.println(prevNode.getCenterX());

    }

    public void updateLink() {
        super.setStartX(prevNode.getCenterX());
        super.setStartY(prevNode.getCenterY());
        super.setEndX(nextNode.getCenterX());
        super.setEndY(nextNode.getCenterY());
    }
    

    public double getRadAngle() {
        System.out.println("in rad angle");
        System.out.println(nextNode.getCenterX());
        double adjacent = nextNode.getCenterX()- prevNode.getCenterX();
        double opposite = nextNode.getCenterY() - nextNode.getCenterY();
        double angleRad = 0;
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
