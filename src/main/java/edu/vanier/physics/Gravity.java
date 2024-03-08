/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

/**
 *
 * @author 2248826
 */
public class Gravity {

    private double acceleration = 9.80;
    private Vector2D vector = new Vector2D(0, acceleration);

    public Gravity() {

    }

    public Gravity(double acceleration) {
        this.acceleration = acceleration;
        this.vector.setY(acceleration);
    }

    public Vector2D getVector() {
        return this.vector;
    }

    public double getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
        this.vector.setY(acceleration);
    }

}
