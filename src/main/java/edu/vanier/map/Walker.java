/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.map;

import java.util.ArrayList;

/**
 *
 * @author 2248826
 */
public class Walker {

    private ArrayList<ModelLink> linksOfWalker = new ArrayList<>();

    public Walker() {

    }

    public Walker(ArrayList<ModelLink> linksOfWalker) {
        this.linksOfWalker.addAll(linksOfWalker);
    }

    public void addLink(ModelLink link) {
        linksOfWalker.add(link);
    }

    public ArrayList<ModelLink> getModel() {
        return this.linksOfWalker;
    }
;
}
