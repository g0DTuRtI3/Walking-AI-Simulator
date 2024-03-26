package edu.vanier.physics;

/**
 * Helper class that represents a 2d vector with an x and y coordinate along
 * with vector methods
 *
 * @author Gabriel Saberian
 */
public class Vector2D {

    /**
     * is the horizontal coordinate of the vector
     */
    private double x1;
    /**
     * is the vertical coordinate of the vector
     */
    private double y1;
    
    private double x2;
    
    private double y2;
    
    private double angle;
    
    private double magnitude;

    public Vector2D() {
    }

    public Vector2D(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y2 = y2;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double[] getX() {
        double[] array = new double[2];
        array[0] = x1;
        array[1] = x2;
        return array;
    }

    public double[] getY() {
        double[] array = new double[2];
        array[0] = y1;
        array[1] = y2;
        return array;
    }

    public void setX(double x1, double x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    public void setY(double y1, double y2) {
        this.y1 = y1;
        this.y2 = y2;
    }

    /**
     * Gets the magnitude of a vector
     *
     * @return the magnitude of a vector
     */
    public double getMagnitude() {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Transforms a vector in a unit vector
     *
     * @return the normalized vector
     */
    public Vector2D normalize() {
        double magnitude = getMagnitude();
        if (magnitude == 0) {
            return new Vector2D(0, 0,0,0);
        }
        return new Vector2D(x1 / magnitude, y1 / magnitude, x2 / magnitude, y2 / magnitude);
    }

    /**
     * Performs the dot product operation on two vectors
     *
     * @param other the other vector for the operation
     * @return the result of the dot product
     */
    public double dot(Vector2D other) {
        return x1 * other.x1 + y1 * other.y1;
    }

    /**
     * Adds 2 vectors together
     *
     * @param other the other vector for the operation
     * @return a new vector that is the result of the addition
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    /**
     * Subtracts 2 vectors together
     *
     * @param other the other vector for the operation
     * @return a new vector that is the result of the subtraction
     */
    public Vector2D sub(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    /**
     * Divides a vector by a scalar
     *
     * @param value the value of the scalar
     * @return a new vector that is the result of the division
     */
    public Vector2D div(double value) {
        return new Vector2D(x / value, y / value);
    }

    /**
     * Rotates a vector to an angle based on a straight horizontal line that
     * represents the angle 0
     *
     * @param theta angle of the desired rotation
     * @return a new vector that is the result of the rotation
     */
    public Vector2D setRotation(double theta) {
        return new Vector2D(getMagnitude() * Math.cos(theta), getMagnitude() * Math.sin(theta));
    }

    /**
     * Gets the rotation angle based on a straight line that represents the
     * angle 0 (using trigonometry)
     *
     * @return the angle of rotation
     */
    public double getRotation() {
        double theta;

        if (this.x < 0 && this.y >= 0) {
            theta = Math.PI + Math.atan(this.y / this.x);
        } else if (this.x < 0 && this.y <= 0) {
            theta = -Math.PI + Math.atan(this.y / this.x);
        } else {
            theta = Math.atan(this.y / this.x);
        }
        return theta;
    }

    /**
     * Rotates a vector to angle based on the current angle of that vector
     *
     * @param theta angle of the desired rotation
     * @return a new vector that is the result of the rotation
     */
    public Vector2D rotate(double theta) {
        return setRotation(theta + getRotation());
    }

    /**
     * Copies the attributes (x and y) of a vector
     *
     * @return a news vector that was copied off another vector
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * Gets the angle between two vectors (using vector properties)
     *
     * @param other the other vector
     * @return the angle between the two vectors
     */
    public double getAngleBetween(Vector2D other) {
        return Math.acos(this.dot(other) / (getMagnitude() * other.getMagnitude()));
    }

    @Override
    public String toString() {
        return "Vector2D{" + "x = " + x + ", y = " + y + '}';
    }

    Vector2D opposite() {
        return new Vector2D(- this.x, - this.y);
    }
}
