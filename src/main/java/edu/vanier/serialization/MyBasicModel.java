package edu.vanier.serialization;

import java.io.Serializable;

/**
 * 
 * @author Zeyu Huang
 */
public class MyBasicModel implements Serializable{

    private static final double STROKE_WIDTH = 10;

    private final MyNodeModel prevNode;
    private final MyNodeModel nextNode;
    private final MyLine link;
    private final String color;

    public MyBasicModel(MyNodeModel prevNode, MyNodeModel nextNode, MyLine line, String color) {
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        this.link = line;
        this.color = color;
    }

    public static double getStrokeWidth() {
        return STROKE_WIDTH;
    }

    public MyNodeModel getPrevNode() {
        return prevNode;
    }

    public MyNodeModel getNextNode() {
        return nextNode;
    }

    public MyLine getLink() {
        return link;
    }

    public String getColor() {
        return color;
    }
}
