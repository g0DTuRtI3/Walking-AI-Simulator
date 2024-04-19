package edu.vanier.serialization;

import java.io.Serializable;

/**
 *
 * @author Zeyu Huang
 */
public class MyLine implements Serializable{
    private double strokeWidth;
    private double startX;
    private double startY;
    private double EndX;
    private double EndY;
    
    public MyLine() {
        
    }

    public MyLine(double strokeWidth, double startX, double startY, double EndX, double EndY) {
        this.strokeWidth = strokeWidth;
        this.startX = startX;
        this.startY = startY;
        this.EndX = EndX;
        this.EndY = EndY;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return EndX;
    }

    public double getEndY() {
        return EndY;
    }
}
