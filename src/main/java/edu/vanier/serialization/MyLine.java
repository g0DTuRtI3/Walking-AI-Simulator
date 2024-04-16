/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.serialization;

/**
 *
 * @author 2253883
 */
public class MyLine {
    private double strokeWidth;
    private double startX;
    private double startY;
    private double EndX;
    private double EndY;
    private String Color;
    
    public MyLine() {
        
    }

    public MyLine(double strokeWidth, double startX, double startY, double EndX, double EndY, String Color) {
        this.strokeWidth = strokeWidth;
        this.startX = startX;
        this.startY = startY;
        this.EndX = EndX;
        this.EndY = EndY;
        this.Color = Color;
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

    public String getColor() {
        return Color;
    }
    
}
