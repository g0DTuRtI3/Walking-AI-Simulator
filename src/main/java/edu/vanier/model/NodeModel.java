package edu.vanier.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author YOUSSEF
 */
public class NodeModel extends javafx.scene.shape.Circle {

    private static double radius = 20;
    private final static double MASS = 5;
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
    private boolean grounded = false;
    private double gravity = 0.1;
    private Line ground = new Line();

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
        
        // Deceleration
        
        if (lastForce > 0) {
            
            if (alpha > 0) {

                deltaTime = getDeltaTime(currentTime2);
                alpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                newAlpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                angle = w + newAlpha;
                
                double newPosY = otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle));
                
                if ((newPosY + radius) < 400) {
                    prevW = Math.abs(currentAlpha * (deltaTime/1e9) - prevW);
                    this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                    this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
                }else {
                    alpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                    newAlpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                    angle = w + newAlpha;
                }
            }else {
                w += newAlpha;
                newAlpha = 0;
                alpha = 0;              
                angle = w;
                
                this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)

            }
        }else if (lastForce < 0) {
            
            if (alpha < 0) {
                deltaTime = getDeltaTime(currentTime2);

                alpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                newAlpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                angle = w + newAlpha;
                
                double newPosY = otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle));
                
                if ((newPosY + radius) < 400) {
                    prevW = Math.abs(currentAlpha * (deltaTime/1e9) - prevW);
                    this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                    this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
                    
                }else {
                    alpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                    newAlpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                    angle = w + newAlpha;
                }
            }else {
                w += newAlpha;
                newAlpha = 0;
                alpha = 0;
                angle = w;
                
                this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
            }
        }
        
        // Angular Gravity
        
        if (gravity == 0.1) {
            
            double formattedAngle = (Math.round((angle % 360) * 10))/10.0;

            // Quadrants 1 and 4
            if (formattedAngle > -90 && formattedAngle < 0 || formattedAngle > -360 && formattedAngle < -270  || formattedAngle > 0 && formattedAngle < 90 || formattedAngle > 270 && formattedAngle < 360) {
                w = (Math.round(w * 10))/10.0;
                if (this == basicModel.getNextNode()) {
                    w += gravity;
                }else {
                    w -= gravity;
                }

            }
            // Quadrants 2 and 3
            else if (formattedAngle > -180 && formattedAngle < -90 || formattedAngle < -180 && formattedAngle > -270 || formattedAngle > 90 && formattedAngle < 180  || formattedAngle > 180 && formattedAngle < 270) {
                w = (Math.round(w * 10))/10.0;
                if (this == basicModel.getNextNode()) {
                    w -= gravity;
                }else {
                    w += gravity;
                }
            }
            
        }else {

            double formattedAngle = (Math.round((angle % 360) * 100))/100.0;

            // Quadrants 1 and 4
            if (formattedAngle > -90 && formattedAngle < 0 || formattedAngle > -360 && formattedAngle < -270  || formattedAngle > 0 && formattedAngle < 90 || formattedAngle > 270 && formattedAngle < 360) {
                w = (Math.round(w * 100))/100.0;
                if (this == basicModel.getNextNode()) {
                    w += gravity;
                }else {
                    w -= gravity;
                }
            }
            // Quadrants 2 and 3
            else if (formattedAngle > -180 && formattedAngle < -90 || formattedAngle < -180 && formattedAngle > -270 || formattedAngle > 90 && formattedAngle < 180  || formattedAngle > 180 && formattedAngle < 270) {
                w = (Math.round(w * 100))/100.0;
                if (this == basicModel.getNextNode()) {
                    w -= gravity;
                }else {
                    w += gravity;
                }
            }
        }  
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
        
        double a = force / MASS; // F = ma
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
        
        angle = w + alpha;

        double newPosY = otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle));
        
        if ((newPosY + radius) < 400) {
            prevW = v/linkLength;
            newAlpha = alpha;
            this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
            this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
        }else {
            if (force > 0) {
                w -= v/linkLength;
                alpha -= currentAlpha;
            }else {
                w += v/linkLength;
                alpha += currentAlpha;
            }
            currentAlpha = 0;
            angle = w  + alpha;
        }
        nodeMoved = true;
    }
    
    public double getDeltaTime(double currentTime) {
        return System.nanoTime() - currentTime;
    }

    public boolean equals(NodeModel model) {
        return (model.getCenterX() == super.getCenterX()) && (model.getCenterY() == super.getCenterY());
    }
    
    public static double getMass() {
        return MASS;
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

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGround(Line ground) {
        this.ground = ground;
    }

    @Override
    public String toString() {
        return "NodeModel{" + " w=" + w + ", prevW=" + prevW + ", alpha=" + alpha + ", newAlpha=" + newAlpha + ", angle=" + angle + ", correctionAngle=" + correctionAngle + ", nodeMoved=" + nodeMoved + ", currentAlpha=" + currentAlpha + ", lastForce=" + lastForce + '}';
    }
}
