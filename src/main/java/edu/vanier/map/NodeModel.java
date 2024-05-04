package edu.vanier.map;

import java.util.ArrayList;
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
    private double currentForce = 0;
    ArrayList<Double> a1 = new ArrayList<>();
    ArrayList<Double> a2 = new ArrayList<>();

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
        
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }
    
    public void updateNode(BasicModel basicModel) {
        
        if (currentForce > 0) {
            
            if (alpha > 0) {
                NodeModel otherNode = basicModel.getOtherNode(this);
                double linkLength = basicModel.getLinkMagnitude();

                deltaTime = getDeltaTime(currentTime2);

                alpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                newAlpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));

                a2.add(Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9)));

    //              alpha -= 0.1;
    //              newAlpha += 0.1;


                angle = w + newAlpha;
                prevW = Math.abs(currentAlpha * (deltaTime/1e9) - prevW); //prevW gets equal to current w so next alpha is 0
                //currentAlpha = Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (2 * deltaTime/1e9));

                System.out.println("currentAlpha " + currentAlpha);
                System.out.println("prevW " + prevW);
                System.out.println("deltaTime2 " + deltaTime/1e9);
                System.out.println("alpha -: " + alpha);
                System.out.println("alpha n: " + newAlpha);
    //            System.out.println(a1);
    //            System.out.println(a2);
                System.out.println();

                this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
            }else {
                w += newAlpha; //include time?
                newAlpha = 0;

                alpha = 0;
                
                angle = w;

                //prevW = 0; // makes prevW always 0
                //System.out.println("PrevW set to 0");
                //System.out.println("alpha test " + alpha + " " + (this == basicModel.getNextNode()));
                //System.out.println();
            }
        }else if (currentForce < 0) {
            
            if (alpha < 0) {
                NodeModel otherNode = basicModel.getOtherNode(this);
                double linkLength = basicModel.getLinkMagnitude();

                deltaTime = getDeltaTime(currentTime2);

                alpha += Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));
                newAlpha -= Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9));

                a2.add(Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (3.5 * deltaTime/1e9)));

    //              alpha -= 0.1;
    //              newAlpha += 0.1;


                angle = w + newAlpha;
                prevW = Math.abs(currentAlpha * (deltaTime/1e9) - prevW); //prevW gets equal to current w so next alpha is 0
                //currentAlpha = Math.abs((Math.abs(currentAlpha * (deltaTime/1e9) - prevW) - prevW) / (2 * deltaTime/1e9));

                System.out.println("currentAlpha " + currentAlpha);
                System.out.println("prevW " + prevW);
                System.out.println("deltaTime2 " + deltaTime/1e9);
                System.out.println("alpha -: " + alpha);
                System.out.println("alpha n: " + newAlpha);
    //            System.out.println(a1);
    //            System.out.println(a2);
                System.out.println();

                this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle + correctionAngle))); // x = rcos(wt)
                this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle + correctionAngle))); // y = rsin(wt)
            }else {
                w -= newAlpha; //include time?
                newAlpha = 0;

                alpha = 0;
                
                angle = w;

                //prevW = 0; // makes prevW always 0
                //System.out.println("PrevW set to 0");
//                System.out.println("alpha test " + alpha + " " + (this == basicModel.getNextNode()));
//                System.out.println();
            }
            
        }
    }
    
    public void setForce(double force, double time, BasicModel basicModel) {
        
        NodeModel otherNode = basicModel.getOtherNode(this);
        double linkLength = basicModel.getLinkMagnitude();
        
        currentForce = force;
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
        //System.out.println("a: " + a);
        double v = Math.sqrt(Math.abs(a*linkLength)); // a = v^2/r
        //System.out.println("v: " + v);
        //correctionAngle += newAlpha;
        
        //alpha = newAlpha;
        
//        if (w > 0) {
//            if (v/linkLength > prevW) {
//        alpha += (v/linkLength - prevW) / (deltaTime/1e9);
//        newAlpha = alpha;
//                //System.out.println("alpha: " + alpha);
//                //System.out.println("deltaTime: " + deltaTime/1e9);
//            }else {
//                  alpha -= (prevW) / (deltaTime/1e9);
//                //System.out.println("alpha: " + alpha);
//                //System.out.println("deltaTime: " + deltaTime/1e9);
//            }
//        }

        if (force > 0) {
            w += v/linkLength;
            currentAlpha = Math.abs((v/linkLength - prevW) / (deltaTime/1e9));
            alpha += currentAlpha;
            a1.add(alpha);
            
        }else {
            w -= v/linkLength;
            currentAlpha = Math.abs((v/linkLength - prevW) / (deltaTime/1e9));
            alpha -= currentAlpha;
        }
        
        System.out.println("w: " + w);
        System.out.println("alpha: " + alpha);
        System.out.println("force: " + force);
        System.out.println("c1: " + v/linkLength);
        System.out.println("c2: " + prevW);
        System.out.println("calculations: " + String.valueOf(v/linkLength - prevW));
        System.out.println("Deltatime: " + deltaTime/1e9);
        System.out.println();
        
        newAlpha = alpha;
        
        prevW = v/linkLength;
        
        //System.out.println("w: " + w);
        
        //currentTime = System.nanoTime();
        
        angle = w + alpha;
         
        // TODO : set angle and correctionAngle to 0 after a full 360 turn
        
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
}
