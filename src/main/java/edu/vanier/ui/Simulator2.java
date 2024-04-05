/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ui;

import edu.vanier.map.BasicModel;
import edu.vanier.map.NodeModel;
import edu.vanier.map.Walker;
import edu.vanier.physics.Vector2D;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author 2242462
 */
public class Simulator2 extends Application{
    
    NodeModel previousNode = new NodeModel(250.0, 375.0, Color.BLUE);
    NodeModel nextNode = new NodeModel(50.0, 375.0, Color.BLUE);
    BasicModel basicModel = new BasicModel(previousNode, nextNode, Color.BLACK);
    Walker walker = new Walker(basicModel);
    
    Rectangle ground = new Rectangle(0, 400, 500, 400);
    private boolean moveRightNode = true;
    
    Vector2D rightForce = new Vector2D(0,10);
    Vector2D leftForce = new Vector2D(0,5);
    
    private AnimationTimer timer;
    private double elapsedTime;
    private double totalTime;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        walker.addBasicModel(basicModel);
        
        ground.setFill(Color.GREEN);
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);
        
        root.getChildren().addAll(basicModel.getPrevNode(), basicModel.getNextNode(), basicModel.getLink(), ground);
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        setTimer();
        
//        scene.setOnKeyPressed((KeyEvent event) -> {
//            switch (event.getCode()) {
//                case A:
//                    if (moveRightNode) {
//                        walker.moveNext(basicModel, rightForce.inverse());
//                    }else {
//                        walker.movePrevious(basicModel, rightForce.inverse());
//                    }
//                    break;
//                case D:
//                    if (moveRightNode) {
//                        walker.moveNext(basicModel, rightForce);
//                    }else {
//                        walker.movePrevious(basicModel, rightForce);
//                    }
//                    break;
//                case LEFT:
//                    moveRightNode = false;
//                    break;
//                case RIGHT:
//                    moveRightNode = true;
//                    break;
//            }
//        });
    }
    
    public void setTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
    
    public void update() {
        totalTime += 0.001;
        System.out.println(totalTime);
        if (elapsedTime >= 2) {
            
            elapsedTime = 0;
            
            //Linear gravity
            
//            if (node1.getCenterY() < 400) {
//                node1.setCenterY(node1.getCenterY() + 5);
//                link.setStartY(link.getStartY() + 5);
//                
//            }
//            
//            if (node2.getCenterY() < 400) {
//                node2.setCenterY(node2.getCenterY() + 5);
//                link.setEndY(link.getEndY() + 5);
//            }
            
            //Angular gravity
            

//            if (angleRight > 0) {
//                angleRight -= 10;
//                node1.setCenterY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
//                node1.setCenterX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
//                link.setStartY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)) );
//                link.setStartX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
//            }
//            if (angleLeft < 180) {
//                angleLeft += 10;
//                node2.setCenterY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
//                node2.setCenterX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
//                link.setEndY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)) );
//                link.setEndX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
//            }
        }
        elapsedTime += 0.016;
    }
}
