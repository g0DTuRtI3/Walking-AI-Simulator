/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.serialization;

import edu.vanier.map.NodeModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author 2253883
 */
public class MyBasicModel {
    private static double strokeWidth = 10;

    private NodeModel prevNode;
    private NodeModel nextNode;
    private Line link = new Line();
    private Color color;
}
