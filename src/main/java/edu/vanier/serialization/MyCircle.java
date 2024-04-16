package edu.vanier.serialization;

/**
 *
 * @author Zeyu Huang
 */
public class MyCircle {
    private double centerX;
    private double centerY;
    private double radius;
    private String color;

    public MyCircle(double centerX, double centerY, double radius, String color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.color = color;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
