package edu.vanier.map;

import edu.vanier.physics.Vector2D;
import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */
public class NodeModel extends javafx.scene.shape.Circle{

    private static double radius = 25;
    private static double mass = 5; //Might become non-static in the future
    private double speedX = 0;
    private double speedY = 0;
    private Vector2D force;

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
    }

//   speed = 0;
    
    //public void updateNode(double deltaTime) {
//        // F_x = m*a_x
//        // a_x = F_x/m
//        // a_x = (v_f_x - v_i_x) / t (v_i_x: this.speed) 
//        // v_f = v_i_x + a_x*t  t-> animation Timer time diff;
//        //same thing for y component
//
//        double accelerationOnX = body.getFinalForce().getX() / mass;
//        double accelerationOnY = body.getFinalForce().getY() / mass;
//
//        this.speedX =+ accelerationOnX * deltaTime;
//        this.speedY =+ accelerationOnY * deltaTime;
//        
//
//    }
    
//    public Vector2D getFinalForce() {
//        Vector2D finalForce = new Vector2D(0, 0);
//
//        for (int i = 0; i < forces.size(); i++) {
//            finalForce.add(forces.get(i));
//        }
//
//        return finalForce;
//
//    }

//    public void updateForce() {
//        forces.add(getFinalForce().opposite());
//    }
    
    public void setForce(Vector2D force) {
        this.force = force;
        double magnitude = force.calculateMagnitude();
        Vector2D nodeVec = new Vector2D (this.getRadius(), 0);
        double angle = force.getAngleBetween(nodeVec);
        this.setCenterX(getCenterX() - magnitude * Math.sin(Math.toRadians(angle)));
        this.setCenterY(getCenterY() - magnitude * Math.sin(Math.toRadians(angle)));
    }

}
