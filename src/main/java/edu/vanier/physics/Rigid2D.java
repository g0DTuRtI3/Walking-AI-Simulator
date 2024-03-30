/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.physics;

import java.util.ArrayList;

/**
 *
 * @author 2248826
 */
public class Rigid2D {

    private ArrayList<Vector2D> forces = new ArrayList<>();
    private Gravity gravity;

    public Rigid2D(ArrayList<Vector2D> forces) {
        this.forces = forces;

    }

    public Vector2D finalForce() {
        Vector2D finalForce = new Vector2D(0, 0);

        for (int i = 0; i < forces.size(); i++) {
            finalForce.add(forces.get(i));
        }

        return finalForce;

    }

    public void updateForce() {
        {
            forces.add(finalForce().opposite());
        }
        
    }

}
