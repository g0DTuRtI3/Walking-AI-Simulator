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
        
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case A:
                    if (moveRightNode) {
                        walker.moveNext(basicModel, rightForce.inverse());
                    }else {
                        walker.movePrevious(basicModel, rightForce.inverse());
                    }
                    break;
                case D:
                    if (moveRightNode) {
                        walker.moveNext(basicModel, rightForce);
                    }else {
                        walker.movePrevious(basicModel, rightForce);
                    }
                    break;
                case LEFT:
                    moveRightNode = false;
                    break;
                case RIGHT:
                    moveRightNode = true;
                    break;
            }
        });
    }
}
