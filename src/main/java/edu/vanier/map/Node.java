/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.map;

import javafx.scene.paint.Color;

/**
 *
 * @author YOUSSEF
 */
public class Node extends javafx.scene.shape.Circle{

    private static double radius = 10;
    private Color color;

    public Node(double centerX, double centerY) {
        super(centerX, centerY, radius, color);

    }

}
