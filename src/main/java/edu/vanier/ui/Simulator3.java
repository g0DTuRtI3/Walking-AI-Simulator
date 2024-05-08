package edu.vanier.ui;

import edu.vanier.model.BasicModel;
import edu.vanier.model.NodeModel;
import edu.vanier.model.Walker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author 2242462
 */
public class Simulator3 extends Application{
    
    NodeModel nextNode = new NodeModel(250.0+100, 400 - 20, Color.RED);
    NodeModel previousNode = new NodeModel(50.0+100, 400 - 20, Color.RED);
    NodeModel middleNode = new NodeModel(150.0+100, 400 - 20, Color.RED);
    BasicModel basicModel1 = new BasicModel(previousNode, middleNode, Color.BLACK);
    BasicModel basicModel2 = new BasicModel(middleNode, nextNode, Color.BLACK);
    Walker walker = new Walker();
    
    Rectangle ground = new Rectangle(0, 400, 500, 400);
    private int currentNode = 1;
    
    double cClockwiseForce = 600;
    double clockwiseForce = 600;
    double tempForce = 100;
    double tempForce2 = 100;
    
    private AnimationTimer timer;
    private long previousTime = -1;
    double elapsedTime = 0;
    
    public Simulator3() {
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
        
        walker.addBasicModel(basicModel1);
        walker.addBasicModel(basicModel2);
        
        ground.setFill(Color.GREEN);
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);
        root.getChildren().addAll(basicModel1.getPrevNode(), basicModel1.getNextNode(), basicModel2.getNextNode(), basicModel1.getLink(),  basicModel2.getLink(), ground);
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case A:
                    currentNode = 1;
                    break;
                case S:
                    currentNode = 2;
                    break;
                case D:
                    currentNode = 3;
                    break;
                case LEFT:
                    if (currentNode == 1) {
                        basicModel1.setPreviousForce(-cClockwiseForce);
                    }else if (currentNode == 2) {
                        basicModel1.setNextForce(-cClockwiseForce);
                    }else {
                        basicModel2.setNextForce(-cClockwiseForce);
                    }
                    break;
                case RIGHT:
                    if (currentNode == 1) {
                        basicModel1.setPreviousForce(clockwiseForce);
                    }else if (currentNode == 2) {
                        basicModel1.setNextForce(clockwiseForce);
                    }else {
                        basicModel2.setNextForce(clockwiseForce);
                    }
                    break;
                case UP:
                    if (currentNode == 1) {
                        basicModel1.setPreviousForce(-tempForce);
                    }else if (currentNode == 2) {
                        basicModel1.setNextForce(-tempForce);
                    }else {
                        basicModel2.setNextForce(-tempForce);
                    }
                    tempForce += 100;
                    break;
                case DOWN:
                    if (currentNode == 1) {
                        basicModel1.setPreviousForce(tempForce2);
                    }else if (currentNode == 2) {
                        basicModel1.setNextForce(tempForce2);
                    }else {
                        basicModel2.setNextForce(tempForce2);
                    }
                    tempForce2 += 100;
                    break;
            }
        });
    }
    
    public void update(double elapsedTime) {
        walker.updateWalker();
    }
    
    
}
