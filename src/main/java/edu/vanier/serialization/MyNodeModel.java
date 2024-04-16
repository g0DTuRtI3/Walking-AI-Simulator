package edu.vanier.serialization;

/**
 *
 * @author 2253883
 */
public class MyNodeModel extends MyCircle{
    private static double radius = 20;
    private static double mass = 5; //Might become non-static in the future
    private double centerX;
    private double centerY;
    private double speedX = 0;
    private double speedY = 0;
    
    public MyNodeModel(double centerX, double centerY, String color) {
        super(centerX, centerY, radius, color);
        this.centerX = centerX;
        this.centerY = centerY;
    }
}
