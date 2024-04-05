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
    private double force = 0;
   
    private double vf = 0;
    private double vi = 0;
    private double angle = 0;
 
    

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
    
    public void setForce(double force, double time) {
        this.force = force;
//        double force_x = force * Math.cos(angle);
//        double force_y = force * Math.sin(angle);
        double a = force/mass;
        //double magnitude = force.calculateMagnitude();
        //Vector2D nodeVec = new Vector2D (this.getRadius(), 0);
        //double angle = force.getAngleBetween(nodeVec);
        //this.setCenterX(getCenterX() - magnitude * Math.sin(Math.toRadians(angle)));
        //this.setCenterY(getCenterY() - magnitude * Math.sin(Math.toRadians(angle)));
//        vf_x = vi_x + a_x*time;
//        vf_y = vi_y + a_y*time;

        vf = vi + a * time;
        angle += 1;
        
        this.setCenterX(getCenterX() - 5 * Math.cos(Math.toRadians(angle)));
        this.setCenterY(getCenterY() - 5 * Math.sin(Math.toRadians(angle)));
        vi = vf;
    }

}
