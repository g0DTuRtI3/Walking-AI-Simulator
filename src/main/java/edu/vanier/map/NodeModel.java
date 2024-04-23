package edu.vanier.map;

import edu.vanier.physics.Vector2D;
import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */
public class NodeModel extends javafx.scene.shape.Circle {

    
    private static double radius = 25;
    private static double mass = 5; //Might become non-static in the future
    //this was added since getColor is somehow not present in the Circle Class of Java...
    private Color color;
    private double deltaTime = 0;
    private double centerX;
    private double centerY;
    private double w = 0;
    private double prevW = 0;
    private double currentTime = 0;
    private double alpha = 100;
    private double angle = 0;
    private double correctionAngle = 0;
    private boolean nodeMoved = false;

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
        
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }



    
    public void updateNode(BasicModel basicModel) {
        NodeModel otherNode = basicModel.getOtherNode(this);
        double linkLength = basicModel.getLinkMagnitude();
        //alpha -= (prevW) / (getDeltaTime(currentTime)/1e9);
        alpha -= 10;
        angle = w + alpha;
        //currentTime = System.nanoTime();
        this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
        this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
    }
    
    public void setForce(double force, double time, BasicModel basicModel) {
        
        NodeModel thisNode = basicModel.getNode(this);
        NodeModel otherNode = basicModel.getOtherNode(this);
        double linkLength = basicModel.getLinkMagnitude();
        
        deltaTime = getDeltaTime(currentTime);
        
        if (otherNode.nodeMoved) {
            if (otherNode.correctionAngle > this.correctionAngle ) {
                correctionAngle = (otherNode.angle + otherNode.correctionAngle) - 180 - angle;
                otherNode.nodeMoved = false;
            }else {
                correctionAngle = (otherNode.angle + otherNode.correctionAngle) + 180 - angle;
                otherNode.nodeMoved = false;
            }
        }
        
        double a = force / mass; // F = ma
        System.out.println("a: " + a);
        double v = Math.sqrt(Math.abs(a*linkLength)); // a = v^2/r
        System.out.println("v: " + v);
        
        if (w > 0) {
            if (v/linkLength > prevW) {
                alpha += (v/linkLength - prevW) / (deltaTime/1e9);
                System.out.println("alpha: " + alpha);
                System.out.println("deltaTime: " + deltaTime/1e9);
            }else {
                alpha -= (prevW) / (deltaTime/1e9);
                System.out.println("alpha: " + alpha);
                System.out.println("deltaTime: " + deltaTime/1e9);
            }
        }
        
        if (force > 0) {
            w += v/linkLength;
        }else {
            w -= v/linkLength;
        }
        
        prevW = v/linkLength;
        
        System.out.println("w: " + w);
        
        currentTime = System.nanoTime();
        
        angle = w + alpha;
        
        // TODO : set angle and correction angle to 0 after a full 360 turn
        
        this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
        this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)

        nodeMoved = true;
        
    }
    
    public double getDeltaTime(double currentTime) {
        return System.nanoTime() - currentTime;
    }

    public boolean equals(NodeModel model) {
        return (model.getCenterX() == super.getCenterX()) && (model.getCenterY() == super.getCenterY());
    }
    
    public static double getMass() {
        return mass;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle + correctionAngle;
    }

    public void setCorrectionAngle(double correctionAngle) {
        this.correctionAngle = correctionAngle;
    }

    public double getAlpha() {
        return alpha;
    }
}
