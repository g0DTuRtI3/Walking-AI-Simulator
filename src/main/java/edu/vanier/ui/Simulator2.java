package edu.vanier.ui;

import edu.vanier.model.BasicModel;
import edu.vanier.model.NodeModel;
import edu.vanier.model.Walker;
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
    
    NodeModel nextNode = new NodeModel(250.0+100, 400 - 20, Color.RED);
    NodeModel previousNode = new NodeModel(50.0+100, 400 - 20, Color.RED);
    BasicModel basicModel = new BasicModel(previousNode, nextNode, Color.BLACK);
    Walker walker = new Walker(basicModel);
    
    Rectangle ground = new Rectangle(0, 400, 500, 400);
    private boolean moveNextNode = true;
    
    double cClockwiseForce = 600;
    double clockwiseForce = 600;
    double tempForce = 100;
    double tempForce2 = 100;
    
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
        
        nextNode.setCurrentTime(System.nanoTime());
        previousNode.setCurrentTime(System.nanoTime());
        
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
                    if (moveNextNode) {
                        basicModel.setNextForce(-cClockwiseForce);
                    }else {
                        basicModel.setPreviousForce(-cClockwiseForce);
                    }
                    break;
                case D:
                    if (moveNextNode) {
                        basicModel.setNextForce(clockwiseForce);
                    }else {
                        basicModel.setPreviousForce(clockwiseForce);
                    }
                    break;
                case F:
                    tempForce += 100;
                    basicModel.setNextForce(tempForce);
                    break;
                case Q:
                    tempForce2 -= 100;
                    basicModel.setPreviousForce(tempForce2);
                    
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
        walker.updateWalker();
    }
    
    
}
