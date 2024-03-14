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
public class Link extends javafx.scene.shape.Line {
    
    private static double strokeWidth = 5;
    
    private Node prevNode;
    private Node nextNode;
    
    public Link(Node prevNode, Node nextNode, Color colorOfLine) {
        super(prevNode.getCenterX(), prevNode.getCenterY(), nextNode.getCenterX(), nextNode.getCenterY());
        super.setStrokeWidth(strokeWidth);
        super.setStroke(colorOfLine);
        
    }
    
    public void updateLink() {
        super.setStartX(prevNode.getCenterX());
        super.setStartY(prevNode.getCenterY());
        super.setEndX(nextNode.getCenterX());
        super.setEndY(nextNode.getCenterY());
    }
}
