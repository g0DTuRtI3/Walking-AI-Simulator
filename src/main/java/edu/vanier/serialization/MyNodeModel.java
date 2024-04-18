package edu.vanier.serialization;

/**
 *
 * @author Zeyu Huang
 */
public class MyNodeModel {

    private static double radius = 20;
    private static double mass = 5; //Might become non-static in the future
    private double centerX;
    private double centerY;
    private double speedX = 0;
    private double speedY = 0;
    private String color;

    public MyNodeModel(double centerX, double centerY, String color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public String getHexColor() {
        return color;
    }
}
