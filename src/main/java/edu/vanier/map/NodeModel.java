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
    private double force;
    private double vi_x;
    private double vf_x;
    private double vi_y;
    private double vf_y;
    

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
    
    public void setForce(double force, double angle, double time) {
        this.force = force;
        double force_x = force * Math.cos(angle);
        double force_y = force * Math.sin(angle);
        double a_x = force_x/mass;
        double a_y = force_y/mass;
        //double magnitude = force.calculateMagnitude();
        //Vector2D nodeVec = new Vector2D (this.getRadius(), 0);
        //double angle = force.getAngleBetween(nodeVec);
        //this.setCenterX(getCenterX() - magnitude * Math.sin(Math.toRadians(angle)));
        //this.setCenterY(getCenterY() - magnitude * Math.sin(Math.toRadians(angle)));
        vf_x = vi_x + a_x*time;
        vf_y = vi_y + a_y*time;
        
        this.setCenterX(getCenterX() - vf_x); // * Math.sin(Math.toRadians(angle)));
        this.setCenterY(getCenterY() - vf_y);// * Math.sin(Math.toRadians(angle)));
        
        vi_x = vf_x;
        vi_y = vf_y;
    }

}
