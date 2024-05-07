package edu.vanier.model;

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
    private double prevForce = 0;
    private double nextForce = 0;

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

    public double getRadAngle() {

        
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
    
    public void updateNextNode(double force) {
        
        if (force != 0) {
            nextNode.setForce(force, this);
            nextForce = 0;
            nextNode.setCurrentTime(System.nanoTime());
            nextNode.setCurrentTime2(System.nanoTime());
//            System.out.println("nextNodeMoved");
//            System.out.println(nextNode);
        }else {
            if (nextNode.getAlpha() > 0) {
                nextNode.updateNode(this);
            }
        }
    }


    
    public void updatePreviousNode(double force) {
        
        if (force != 0) {
            prevNode.setForce(force, this);
            prevForce = 0;
            prevNode.setCurrentTime(System.nanoTime());
            prevNode.setCurrentTime2(System.nanoTime());
//            System.out.println("prevNodeMoved");
//            System.out.println(prevNode);
        }else {
            prevNode.updateNode(this);
            prevNode.setCurrentTime2(System.nanoTime());
        }
    }
    
    public void setPreviousForce(double previousForce) {
        this.prevForce = previousForce;
    }

    public void setNextForce(double nextForce) {
        this.nextForce = nextForce;
    }

    public double getLinkMagnitude() {
        return Math.sqrt(Math.pow((link.getStartX() - link.getEndX()), 2) + Math.pow((link.getStartY() - link.getEndY()), 2));
    }
    
    public double getPreviousForce() {
        return prevForce;
    }

    public double getNextForce() {
        return nextForce;
    }
    
    public NodeModel getOtherNode(NodeModel nodeModel) {
        if (nodeModel == nextNode) {
            return prevNode;
        }

        return nextNode;
    }
    
    public NodeModel getNode(NodeModel nodeModel) {

        if (nodeModel == nextNode) {
            return nextNode;
        }

        return prevNode;
    }
    
    public double getDeltaTime(double currentTime) {
        return System.nanoTime() - currentTime;
    }
    
}