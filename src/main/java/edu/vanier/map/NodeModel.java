package edu.vanier.map;

import edu.vanier.physics.Rigid2D;
import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */
public class NodeModel extends javafx.scene.shape.Circle{

    private static double radius = 20;
    private static double mass = 5; //Might become non-static in the future
    private double centerX;
    private double centerY;
    private double speedX = 0;
    private double speedY = 0;
    private Rigid2D body;

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
        this.body = body;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void updateNode(double deltaTime) {
        // F_x = m*a_x
        // a_x = F_x/m
        // a_x = (v_f_x - v_i_x) / t (v_i_x: this.speed) 
        // v_f = v_i_x + a_x*t  t-> animation Timer time diff;
        //same thing for y component

        double accelerationOnX = body.finalForce().getX() / mass;
        double accelerationOnY = body.finalForce().getY() / mass;

        this.speedX = +accelerationOnX * deltaTime;
        this.speedY = +accelerationOnY * deltaTime;
        

    }

    public boolean equals(NodeModel model) {
        return (model.getCenterX() == centerX) && (model.getCenterY() == centerY);
    }

    public static double getMass() {
        return mass;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public Rigid2D getBody() {
        return body;
    }

    public static double getBASELINE_OFFSET_SAME_AS_HEIGHT() {
        return BASELINE_OFFSET_SAME_AS_HEIGHT;
    }
    
    
}
