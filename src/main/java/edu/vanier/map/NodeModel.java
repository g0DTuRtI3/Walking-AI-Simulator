package edu.vanier.map;

import edu.vanier.physics.Rigid2D;
import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */
public class NodeModel extends javafx.scene.shape.Circle{

    private static double radius = 25;
    private static double mass = 5; //Might become non-static in the future
    private Rigid2D body = new Rigid2D();
    private double speedX = 0;
    private double speedY = 0;

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
    }

    public void updateNode(double deltaTime) {
        // F_x = m*a_x
        // a_x = F_x/m
        // a_x = (v_f_x - v_i_x) / t (v_i_x: this.speed) 
        // v_f = v_i_x + a_x*t  t-> animation Timer time diff;
        //same thing for y component

        double accelerationOnX = body.getFinalForce().getX() / mass;
        double accelerationOnY = body.getFinalForce().getY() / mass;

        this.speedX = +accelerationOnX * deltaTime;
        this.speedY = +accelerationOnY * deltaTime;
        

    }

}
