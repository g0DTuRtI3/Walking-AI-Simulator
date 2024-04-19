package edu.vanier.serialization;

import java.io.Serializable;

/**
 *
 * @author Zeyu Huang
 */
public class MyBasicModel implements Serializable{

    private static double strokeWidth = 10;

    private MyNodeModel prevNode;
    private MyNodeModel nextNode;
    private MyLine link;
    private String color;

    public MyBasicModel(MyNodeModel prevNode, MyNodeModel nextNode, MyLine line, String color) {
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        this.link = line;
        this.color = color;
    }

    public static double getStrokeWidth() {
        return strokeWidth;
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
