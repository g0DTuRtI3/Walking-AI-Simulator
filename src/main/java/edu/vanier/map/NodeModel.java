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
    private double centerX;
    private double centerY;
    private double speedX = 0;
    private double speedY = 0;
    private double angle = 0;
    private boolean nodeMoved = false;
    private double vf = 0;
    private double vi = 0;

    public NodeModel(double centerX, double centerY, Color color) {
        super(centerX, centerY, radius, color);
    }

    public void updateNode(double deltaTime) {
        // F_x = m*a_x
        // a_x = F_x/m
        // a_x = (v_f_x - v_i_x) / t (v_i_x: this.speed) 
        // v_f = v_i_x + a_x*t  t-> animation Timer time diff;
        //same thing for y component

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
    }

    public void setForce(double force, double time, BasicModel basicModel) {

        Vector2D linkVec = new Vector2D();
        Vector2D nodeVec = new Vector2D(this.getRadius(), 0);
        double linkLength = basicModel.getLinkMagnitude();
        NodeModel node = basicModel.getNode(this);
        NodeModel otherNode = basicModel.getOtherNode(this);

        if (node == basicModel.getNextNode()) {

            linkVec = new Vector2D(this.getCenterX() - otherNode.getCenterX(), this.getCenterY() - otherNode.getCenterY());

            if (otherNode.nodeMoved) {
                //angle = otherNode.angle - 180;
                otherNode.nodeMoved = false;
            }

        } else if (node == basicModel.getPrevNode()) {

            linkVec = new Vector2D(otherNode.getCenterX() - this.getCenterX(), otherNode.getCenterY() - this.getCenterY());

            if (otherNode.nodeMoved) {
                //angle = otherNode.angle + 180;
                otherNode.nodeMoved = false;
            }

        }

        double angleBetween = Math.toDegrees(linkVec.getAngleBetween(nodeVec));
        System.out.println("Angle Between: " + angleBetween);
        //double force_x = force * Math.cos(angleBetween);
        //double force = force * Math.sin(angleBetween);
        double a = force / mass;
        //double a_y = force_y/mass;

        //double magnitude = force.calculateMagnitude();
        //Vector2D nodeVec = new Vector2D (this.getRadius(), 0);
        //double angle = force.getAngleBetween(nodeVec);
        //this.setCenterX(getCenterX() - magnitude * Math.sin(Math.toRadians(angle)));
        //this.setCenterY(getCenterY() - magnitude * Math.sin(Math.toRadians(angle)));
        //        vf_x = vi_x + a_x*time;
        //        vf_y = vi_y + a_y*time;
        vf = vi + a * time;
        //vf_y = vi_y + a_y * time;

        double theta = Math.acos(vf / linkLength);
        //double theta_y = Math.asin(vf_y/linkLength);

        angle += theta; //+ angle;
        //angle_y += theta_y + 1; //+ angle;

        this.setCenterX(otherNode.getCenterX() + linkLength * Math.cos(Math.toRadians(angle)));
        this.setCenterY(otherNode.getCenterY() + linkLength * Math.sin(Math.toRadians(angle)));

        vi = vf;
        //vi_y = vf_y;

        //angle = angle_x;
        nodeMoved = true;

    }

    public boolean equals(NodeModel model) {
        return (model.getCenterX() == centerX) && (model.getCenterY() == centerY);
    }

    public static double getMass() {
        return mass;
    }
}
