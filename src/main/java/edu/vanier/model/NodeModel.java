package edu.vanier.model;

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
    private double w = 0;
    private double prevW = 0;
    private double currentTime = 0;
    private double alpha = 0;
    private double newAlpha = 0;
    private double angle = 0;
    private double correctionAngle = 0;
    private boolean nodeMoved = false;
    private double currentTime2 = 0;
    private double currentAlpha = 0;
    private double lastForce = 0;

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
        
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }
    
    public void updateNode(BasicModel basicModel) {
        
        if (lastForce > 0) {
            
            if (alpha > 0) {
                NodeModel otherNode = basicModel.getOtherNode(this);
                double linkLength = basicModel.getLinkMagnitude();

                deltaTime = getDeltaTime(currentTime2);
                alpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                newAlpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                angle = w + newAlpha;
                prevW = Math.abs(currentAlpha * (deltaTime/1e9) - prevW); //prevW gets equal to current w so next alpha is 0

                this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
            }else {
                w += newAlpha;
                newAlpha = 0;
                alpha = 0;              
                angle = w;

            }
        }else if (lastForce < 0) {
            
            if (alpha < 0) {
                NodeModel otherNode = basicModel.getOtherNode(this);
                double linkLength = basicModel.getLinkMagnitude();
                deltaTime = getDeltaTime(currentTime2);

                alpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                newAlpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                angle = w + newAlpha;
                prevW = Math.abs(currentAlpha * (deltaTime/1e9) - prevW);

                this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
            }else {
                w -= newAlpha;
                newAlpha = 0;
                alpha = 0;      
                angle = w;
            }
            
        }
        
        // Gravity here
        
        
    }
    
    public void setForce(double force, BasicModel basicModel) {
        
        NodeModel otherNode = basicModel.getOtherNode(this);
        double linkLength = basicModel.getLinkMagnitude();
        
        lastForce = force;
        deltaTime = getDeltaTime(currentTime);
        
        if (otherNode.nodeMoved) {
            if (otherNode.correctionAngle > this.correctionAngle) {
                correctionAngle = (otherNode.angle + otherNode.correctionAngle + otherNode.alpha) - 180 - angle - alpha;
                otherNode.nodeMoved = false;
            }else {
                correctionAngle = (otherNode.angle + otherNode.correctionAngle + otherNode.alpha) + 180 - angle - alpha;
                otherNode.nodeMoved = false;
            }
        }
        
        double a = force / mass; // F = ma
        double v = Math.sqrt(Math.abs(a*linkLength)); // a = v^2/r

        if (force > 0) {
            w += v/linkLength;
            currentAlpha = Math.abs((v/linkLength - prevW) / (deltaTime/1e9));
            alpha += currentAlpha;
            
        }else {
            w -= v/linkLength;
            currentAlpha = Math.abs((v/linkLength - prevW) / (deltaTime/1e9));
            alpha -= currentAlpha;
        }
        
        newAlpha = alpha;
        prevW = v/linkLength;
        angle = w + alpha;
        
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

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public void setCurrentTime2(double currentTime2) {
        this.currentTime2 = currentTime2;
    }
    
    public String getHexColor() {
        return color.toString().substring(2,8);
    }

    @Override
    public String toString() {
        return "NodeModel{" + " w=" + w + ", prevW=" + prevW + ", alpha=" + alpha + ", newAlpha=" + newAlpha + ", angle=" + angle + ", correctionAngle=" + correctionAngle + ", nodeMoved=" + nodeMoved + ", currentAlpha=" + currentAlpha + ", lastForce=" + lastForce + '}';
    }
    
    
}
