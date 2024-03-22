package edu.vanier.ui;

import edu.vanier.map.BasicModel;
import edu.vanier.map.NodeModel;
import edu.vanier.physics.Rigid2D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import edu.vanier.physics.Vector2D;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

/**
 *
 * @author 2242462
 */
public class Simulator extends Application {

    private final int speed = 15;
    private boolean moveRightNode = true;
    private final float gravity = 9.8f;
    private AnimationTimer timer;
    private double elapsedTime;
    private NodeModel node1;
    private NodeModel node2;
    private BasicModel link;
    private double angleBetween = 0;
    double angleRight = 0;
    double angleLeft = 180;
    boolean rightNodeMoved = false;
    boolean leftNodeMoved = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);

        //node1 = new NodeModel(250.0, 400.0, Color.BLUE, new Rigid2D(new ArrayList<>()));
        //node1.setFill(Color.TRANSPARENT);
        //node1.setStroke(Color.BLACK);
        //node2 = new NodeModel(50.0, 400.0, Color.BLUE, new Rigid2D(new ArrayList<>()));
        //node2.setFill(Color.TRANSPARENT);
        //node2.setStroke(Color.BLACK);
        //link = new Line(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY());
        Line line = new Line(0, 400, 500, 400);
        //line.setStroke(Color.RED);
        BasicModel link = new BasicModel(node1,node2,Color.BLACK);
        
        

        setTimer();
scene.setOnKeyPressed((KeyEvent event) -> {System.out.println(link.getRadAngle());});
        /*scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case A:
                    if (moveRightNode) {

                        rightNodeMoved = true;

                        //node1.setCenterY(node1.getCenterY() - Math.sin(Math.toRadians(angleRight)));
                        //node1.setCenterX(node1.getCenterX() - Math.cos(Math.toRadians(angleRight)));
                        if (leftNodeMoved) {
                            angleRight = angleLeft - 180;
                            leftNodeMoved = false;
                        }

                        angleRight += 10;

                        System.out.println("angleRight: " + angleRight);

                        if (angleRight == 360) {
                            angleRight = 0;
                        }

                        node1.setCenterY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
                        node1.setCenterX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
                        link.setStartY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
                        link.setStartX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));

                        /*
                        
                        if (angleBetween >= 0 && angleBetween < 90) {
                            double angle = Math.asin((-node1.getCenterY()+400 + 10)/200);
                            System.out.println("Angle: " + Math.toDegrees(angle));
                            node1.setCenterY(node1.getCenterY() - 10);
                            node1.setCenterX(50 + 200 * Math.cos(angle));
                            link.setStartY(link.getStartY() - 10);
                            link.setStartX(50 + 200 * Math.cos(angle));
                            System.out.println("(x1,y1): " + link.getStartX() + ", " + link.getEndX() + ", (x2,y2): " + link.getEndX() + ", " + link.getEndY());
                            Vector2D linkVec = new Vector2D(link.getStartX() - link.getEndX(), link.getStartY() - link.getEndY());
                            Vector2D nodeVec = new Vector2D(25, 0);
                            angleBetween = Math.toDegrees(linkVec.getAngleBetween(nodeVec));
                            System.out.println("Angle between: " + angleBetween);
                            
                        } else if (angleBetween >= 90 && angleBetween < 180) {
                            double angle = Math.asin((node1.getCenterY()+400 - 10)/200);
                            System.out.println("Angle: " + Math.toDegrees(angle));
                            node1.setCenterY(node1.getCenterY() + 10);
                            node1.setCenterX(50 + 200 * -Math.cos(angle));
                            link.setStartY(link.getStartY() + 10);
                            link.setStartX(50 + 200 * -Math.cos(angle));
                            System.out.println("(x1,y1): " + link.getStartX() + ", " + link.getEndX() + ", (x2,y2): " + link.getEndX() + ", " + link.getEndY());
                            Vector2D linkVec = new Vector2D(link.getStartX() - link.getEndX(), link.getStartY() - link.getEndY());
                            Vector2D nodeVec = new Vector2D(25, 0);
                            angleBetween = Math.toDegrees(linkVec.getAngleBetween(nodeVec));
                            System.out.println("Angle between: " + angleBetween);
                            
                        }else if (angleBetween >= 180 && angleBetween < 270) {
                            double angle = Math.asin((node1.getCenterY()-400 - 10)/200);
                            System.out.println("Angle: " + Math.toDegrees(angle));
                            node1.setCenterY(node1.getCenterY() + 10);
                            node1.setCenterX(50 + 200 * -Math.cos(angle));
                            link.setStartY(link.getStartY() + 10);
                            link.setStartX(50 + 200 * -Math.cos(angle));
                            System.out.println("(x1,y1): " + link.getStartX() + ", " + link.getEndX() + ", (x2,y2): " + link.getEndX() + ", " + link.getEndY());
                            Vector2D linkVec = new Vector2D(link.getStartX() - link.getEndX(), link.getStartY() - link.getEndY());
                            Vector2D nodeVec = new Vector2D(25, 0);
                            angleBetween = Math.toDegrees(linkVec.getAngleBetween(nodeVec));
                            System.out.println("Angle between: " + angleBetween);
                        }

                         /
                    } else {
                        leftNodeMoved = true;

                        if (rightNodeMoved) {
                            angleLeft = angleRight + 180;
                            rightNodeMoved = false;
                        }

                        angleLeft += 10;
                        System.out.println("angleLeft: " + angleLeft);

                        if (angleLeft == 360) {
                            angleLeft = 0;
                        }

                        node2.setCenterY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
                        node2.setCenterX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
                        link.setEndY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
                        link.setEndX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
                    }
                    break;
                case D:
                    if (moveRightNode) {
                        rightNodeMoved = true;

                        if (leftNodeMoved) {
                            angleRight = angleLeft - 180;
                            leftNodeMoved = false;
                        }

                        angleRight -= 10;

                        if (angleRight == 360) {
                            angleRight = 0;
                        }

                        node1.setCenterY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
                        node1.setCenterX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
                        link.setStartY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
                        link.setStartX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
                    } else {
                        leftNodeMoved = true;

                        if (rightNodeMoved) {
                            angleLeft = angleRight + 180;
                            rightNodeMoved = false;
                        }

                        angleLeft -= 10;

                        if (angleLeft == 360) {
                            angleLeft = 0;
                        }

                        node2.setCenterY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
                        node2.setCenterX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
                        link.setEndY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
                        link.setEndX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
                    }
                    break;
                case LEFT:
                    moveRightNode = false;
                    break;
                case RIGHT:
                    moveRightNode = true;
                    break;
            }
        });*/
        root.getChildren().addAll(link, node1, node2, line);
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public void setTimer() {
        timer = new AnimationTimer() {
            static double prevTime = System.nanoTime();

            @Override
            public void handle(long now) {
                elapsedTime = (now - prevTime) / 1000000000;
               
                //update 
//update();
                prevTime = now;
            }

            @Override
            public void start() {
                prevTime = System.nanoTime();
                super.start();
            }

            @Override
            public void stop() {
                prevTime = -1;
                super.stop();
            }
        };
        timer.start();
    }

    /*public void update() {
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
            

            if (angleRight > 0) {
                angleRight -= 10;
                node1.setCenterY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)));
                node1.setCenterX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
                link.setStartY(node2.getCenterY() - 200 * Math.sin(Math.toRadians(angleRight)) );
                link.setStartX(node2.getCenterX() + 200 * Math.cos(Math.toRadians(angleRight)));
            }
            if (angleLeft < 180) {
                angleLeft += 10;
                node2.setCenterY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)));
                node2.setCenterX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
                link.setEndY(node1.getCenterY() - 200 * Math.sin(Math.toRadians(angleLeft)) );
                link.setEndX(node1.getCenterX() + 200 * Math.cos(Math.toRadians(angleLeft)));
            }
        }
        elapsedTime += 0.016;
    }*/
}
