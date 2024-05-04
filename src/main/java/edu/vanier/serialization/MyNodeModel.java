package edu.vanier.serialization;

import java.io.Serializable;

/**
 *
 * @author Zeyu Huang
 */
public class MyNodeModel implements Serializable{

    private static final double RADIUS = 20;
    private static final double MASS = 5; //Might become non-static in the future
    private final double centerX;
    private final double centerY;
    private final double speedX = 0;
    private final double speedY = 0;
    private final String color;

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
