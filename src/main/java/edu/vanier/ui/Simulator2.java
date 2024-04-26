/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.ui;

import edu.vanier.map.BasicModel;
import edu.vanier.map.NodeModel;
import edu.vanier.map.Walker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author 2242462
 */
public class Simulator2 extends Application{
    
    NodeModel nextNode = new NodeModel(250.0+100, 375.0, Color.BLUE);
    NodeModel previousNode = new NodeModel(50.0+100, 375.0, Color.BLUE);
    BasicModel basicModel = new BasicModel(previousNode, nextNode, Color.BLACK);
    Walker walker = new Walker(basicModel);
    
    Rectangle ground = new Rectangle(0, 400, 500, 400);
    private boolean moveNextNode = true;
    
    double rightForce = 100;
    double leftForce = 100;
    double tempForce = 100;
    
    private AnimationTimer timer;
    private long previousTime = -1;
    double elapsedTime = 0;
    
    public Simulator2() {
        timer = new AnimationTimer() {
            
            public void handle(long currentTime) {
                elapsedTime = (currentTime - previousTime)/1e9;
                //System.out.println(elapsedTime);
                update(elapsedTime);
                previousTime = currentTime;
            }
            
            public void start() {
                previousTime = System.nanoTime();
                super.start();
            }
            
            public void stop() {
                previousTime = -1;
                super.stop();
            }
        };
        
        timer.start();
    }
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        walker.addBasicModel(basicModel);
        
        ground.setFill(Color.GREEN);
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);
        root.getChildren().addAll(basicModel.getPrevNode(), basicModel.getNextNode(), basicModel.getLink());
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case A:
                    if (moveNextNode) {
                        basicModel.setNextForce(-rightForce);
                        //walker.moveNext(basicModel, -rightForce, elapsedTime);
                    }else {
                        basicModel.setPreviousForce(-rightForce);
                        //walker.movePrevious(basicModel, -rightForce, elapsedTime);
                    }
                    break;
                case D:
                    if (moveNextNode) {
                        basicModel.setNextForce(leftForce);
                        //walker.moveNext(basicModel, leftForce, elapsedTime);
                    }else {
                        basicModel.setPreviousForce(leftForce);
                        //walker.movePrevious(basicModel, leftForce, elapsedTime);
                    }
                    break;
                case F:
                    tempForce += 100;
                    basicModel.setNextForce(tempForce);
                    break;
                case LEFT:
                    moveNextNode = false;
                    break;
                case RIGHT:
                    moveNextNode = true;
                    break;
            }
        });
    }
    
    public void update(double elapsedTime) {
        
        walker.setElapsedTime(elapsedTime);
        walker.updateWalker();
        //System.out.println(basicModel.getPrevNode().getAngle());
        //System.out.println(basicModel.getNextNode().getAngle());
        // Debug
//        System.out.println(basicModel.getPrevNode().getCenterX());
//        System.out.println(basicModel.getPrevNode().getCenterY());
//        System.out.println(basicModel.getNextNode().getCenterX());
//        System.out.println(basicModel.getNextNode().getCenterY());
        
        //System.out.println(elapsedTime);
        
//        if (elapsedTime >= 2) {
//            
//            elapsedTime = 0;
//            
//            //Linear gravity
//            
////            if (node1.getCenterY() < 400) {
////                node1.setCenterY(node1.getCenterY() + 5);
////                link.setStartY(link.getStartY() + 5);
////                
////            }
////            
////            if (node2.getCenterY() < 400) {
////                node2.setCenterY(node2.getCenterY() + 5);
////                link.setEndY(link.getEndY() + 5);
////            }
//            
//            //Angular gravity
//            
//
////            if (angleRight > 0) {
////                angleRight -= 10;
////                node1.setCenterY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
////                node1.setCenterX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
////                link.setStartY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)) );
////                link.setStartX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
////            }
////            if (angleLeft < 180) {
////                angleLeft += 10;
////                node2.setCenterY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
////                node2.setCenterX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
////                link.setEndY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)) );
////                link.setEndX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
////            }
//        }
//        elapsedTime += 0.016;
    }
    
    
}
