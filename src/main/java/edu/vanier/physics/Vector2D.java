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
    private double x;
    /**
     * is the vertical coordinate of the vector
     */
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the magnitude of a vector
     * 
     * @return the magnitude of a vector
     */
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Transforms a vector in a unit vector
     * 
     * @return the normalized vector
     */
    public Vector2D normalize() {
        double magnitude = getMagnitude();
        if (magnitude == 0) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * Performs the dot product operation on two vectors
     * 
     * @param other the other vector for the operation
     * @return the result of the dot product
     */
    public double dot(Vector2D other) {
        return x * other.x + y * other.y;
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
}
