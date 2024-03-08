package edu.vanier.physics;

/**
 * Helper class that represents a segment with a start and an end made of two 2d
 * vectors
 *
 * @author Gabriel Saberian
 */
public class Segment2D {

    /**
     * is the far left point of the segment
     */
    private Vector2D a = new Vector2D(0, 0);
    /**
     * is the far right point of the segment
     */
    private Vector2D b = new Vector2D(0, 0);

    public Segment2D(Vector2D a, Vector2D b) {
        this.a = a;
        this.b = b;
    }

    public Vector2D getA() {
        return a;
    }

    public Vector2D getB() {
        return b;
    }

    public void setA(Vector2D a) {
        this.a = a;
    }

    public void setB(Vector2D b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Segment2D{" + "a = " + a + ", b = " + b + '}';
    }
}
