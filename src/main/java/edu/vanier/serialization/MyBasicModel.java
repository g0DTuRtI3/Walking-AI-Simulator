package edu.vanier.serialization;

/**
 *
 * @author 2253883
 */
public class MyBasicModel {
    private static double strokeWidth = 10;

    private MyNodeModel prevNode;
    private MyNodeModel nextNode;
    private MyLine link;
    private String color;

    public MyBasicModel(MyNodeModel prevNode, MyNodeModel nextNode, MyLine line ,String color) {
        this.prevNode = prevNode;
        this.nextNode = nextNode;
        this.link = line;
        this.color = color;
    }
}
