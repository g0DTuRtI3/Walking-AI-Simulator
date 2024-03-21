package edu.vanier.controllers;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorController {

    Stage primaryStage;
    boolean isCircleMode;
    boolean isLinkMode;
    Circle circle1;
    Circle circle2;
    ArrayList circle_list = new ArrayList<Circle>();
    double mouseX;
    double mouseY;
    Color color;

    @FXML
    private Button btn_Start;

    @FXML
    private Circle circle;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Pane editorPane;

    @FXML
    private Rectangle link;

    @FXML
    private MenuItem menuItem_About;

    @FXML
    private MenuItem menuItem_HowToUse;

    @FXML
    private MenuItem menuItem_Load;

    @FXML
    private MenuItem menuItem_Save;

    @FXML
    private TextField tf_interval;

    @FXML
    private TextField tf_learningRate;

    @FXML
    private TextField tf_nbModel;

    // Gets the Stage when called
    public EditorController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void initialize() {
        tf_interval.setFocusTraversable(false);
        tf_learningRate.setFocusTraversable(false);
        tf_nbModel.setFocusTraversable(false);
        color = colorPicker.getValue();
    }

    @FXML
    void circleOnMouseClicked(MouseEvent event) {
        if (!isCircleMode) {
            isCircleMode = true;
            isLinkMode = false;
            selected();
        } else {
            isCircleMode = false;
            selected();
        }
    }

    @FXML
    void circleOnMouseDragged(MouseEvent event) {
//        circle.setLayoutX(event.getSceneX() - mouseX);
//        circle.setLayoutY(event.getSceneY() - mouseY);
//        System.out.println(mouseX + " " + mouseY + " " + circle.getLayoutX() + " " + circle.getLayoutY());
    }

    @FXML
    void circleOnMousePressed(MouseEvent event) {
//        mouseX = event.getX();
//        mouseY = event.getY();
    }

    @FXML
    void colorPickerOnAction(ActionEvent event) {
        color = colorPicker.getValue();
    }

    @FXML
    void linkOnMouseClicked(MouseEvent event) {
        if (!isLinkMode) {
            isLinkMode = true;
            isCircleMode = false;
            selected();
        } else {
            isLinkMode = false;
            selected();
        }
    }

    @FXML
    void linkOnMouseDragged(MouseEvent event) {

    }

    @FXML
    void linkOnMousePressed(MouseEvent event) {

    }

    @FXML
    void paneOnMouseClicked(MouseEvent event) {
        // in circle mode: adds circle to the editor pane
        if (isCircleMode) {
            addCircle(event);

            // in link mode: add links between circles
        } else if (isLinkMode) {
            if (circle1 == null) {
                System.out.println("no");
                circle1 = addLink(event, circle1, circle2);
            } else {
                System.out.println("elo");
                System.out.println("circle: " + circle2 == null);
                addLink(event, circle1, circle2);
            }
            // if not specified move the circle
        } else {
            System.out.println(event.getTarget());
        }
    }

    // Switches to the simulation scene
    @FXML
    void startOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Simulation_layout.fxml"));
        mainAppLoader.setController(new SimulationController(primaryStage));
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Simulation");
        primaryStage.show();
    }

    private void selected() {
        if (isCircleMode) {
            circle.setFill(Color.PURPLE);
            link.setFill(Color.DODGERBLUE);
        } else if (isLinkMode) {
            link.setFill(Color.PURPLE);
            circle.setFill(Color.DODGERBLUE);
        } else {
            circle.setFill(Color.DODGERBLUE);
            link.setFill(Color.DODGERBLUE);
        }

    }

    // Adds circle to the editor pane
    private void addCircle(MouseEvent event) {
        Circle circle = new Circle(event.getX(), event.getY(), 25, color);
        circle_list.add(circle);
        editorPane.getChildren().add(circle);
    }

    private Circle addLink(MouseEvent event, Circle circle, Circle circle2) {
        Color color = null;

        if (circle == null) {
            try {
                circle = (Circle) event.getTarget();
                color = (Color) circle.getFill();
                circle.setFill(Color.DODGERBLUE);
            } catch (Exception e) {
                circle = null;
            }
            return circle;
        } else {
            System.out.println("Create rect");
//            Rectangle rect = new Rectangle();
//            rectFrame.setFrameFromDiagonal(circle.getCenterX(), circle.getCenterY(), circle2.getCenterX(), circle2.getCenterY());

            // Calculate the width and height of the rectangle
            double width = Math.abs(circle2.getCenterX() - circle.getCenterX());
            double height = Math.abs(circle2.getCenterY() - circle.getCenterY());

            // Determine the starting point of the rectangle
            double startX = Math.min(circle2.getCenterX(), circle.getCenterX());
            double startY = Math.min(circle2.getCenterY(), circle.getCenterY());

            // Create the rectangle
            Rectangle rectangle = new Rectangle(startX, startY, width, height);
            rectangle.setFill(Color.GREEN);
            rectangle.setStroke(Color.BLACK);
        }

        circle.setFill(color);
        circle2.setFill(color);
        return null;
    }

//    // A method that makes the shapes draggable
//    private void dragObject(Node node) {
//
//        node.setOnMousePressed(e -> {
//            mouseX = e.getSceneX();
//            mouseY = e.getSceneY();
//        });
//
//        node.setOnMouseDragged(e -> {
//            node.setLayoutX(e.getSceneX() - mouseX);
//            node.setLayoutY(e.getSceneY() - mouseY);
//        });
//    }
}
