/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author 2242462
 */
public class Simulator extends Application{
    
    private final int speed = 15;
    private boolean moveRightNode = true;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
       Pane root = new Pane();
       Scene scene = new Scene(root, 500, 500);
       
       Circle node1 = new Circle(250.0, 400.0, 25.0);
       node1.setFill(Color.TRANSPARENT);
       node1.setStroke(Color.BLACK);
       Circle node2 = new Circle(50.0, 400.0, 25.0);
       node2.setFill(Color.TRANSPARENT);
       node2.setStroke(Color.BLACK);
       Line link = new Line(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY());
       
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case A:
                    if (moveRightNode) {
                       node1.setCenterX(node1.getCenterX() - speed);
                       break;
                    }else {
                       node2.setCenterX(node2.getCenterX() - speed);
                       break;
                    }
                case D:
                    if (moveRightNode) {
                        node1.setCenterX(node1.getCenterX() + speed);
                        break;
                    }else {
                        node2.setCenterX(node2.getCenterX() + speed);
                        break;
                    }
                case LEFT:
                   moveRightNode = false;
                   break;
                case RIGHT:
                   moveRightNode = true;
                   break;
            }
        });

       root.getChildren().addAll(link, node1, node2);
       primaryStage.setScene(scene);
       primaryStage.sizeToScene();
       primaryStage.show();
        
    }
    
    
    
}
