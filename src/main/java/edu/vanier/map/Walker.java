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

    private ArrayList<Link> linksOfWalker = new ArrayList<>();

    public Walker() {

    }

    public Walker(ArrayList<Link> linksOfWalker) {
        this.linksOfWalker.addAll(linksOfWalker);
    }

    public void addLink(Link link) {
        linksOfWalker.add(link);
    }
}
