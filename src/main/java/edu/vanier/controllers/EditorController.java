package edu.vanier.controllers;

import edu.vanier.map.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorController {

    private final int circleRadius = 20;
    private final int circleSocialDistancing = 10;
    private int nbModel;
    private int interval;
    private float learningRate;
    private double mouseX;
    private double mouseY;
    private boolean isCircleMode;
    private boolean isLinkMode;
    private boolean isSelected;
    private boolean isDelMode = false;
    private ArrayList<Circle> circle_list = new ArrayList<Circle>();
    private Circle circle1 = null;
    private Circle circle2 = null;
    private Color circleColor;
    private Color linkColor;
    private Color ogColor;
    private NodeModel prevNode;
    private NodeModel nextNode;
    private Stage primaryStage;
    private Walker walker = new Walker();

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_Start;

    @FXML
    private ColorPicker circleColorPicker;

    @FXML
    private Circle editorCircle;

    @FXML
    private Pane editorPane;

    @FXML
    private Label label_Interval;

    @FXML
    private Label label_LearningRate;

    @FXML
    private Label label_NbModel;

    @FXML
    private Rectangle link;

    @FXML
    private ColorPicker linkColorPicker;

    @FXML
    private MenuItem menuItem_About;

    @FXML
    private MenuItem menuItem_HowToUse;

    @FXML
    private MenuItem menuItem_Load;

    @FXML
    private MenuItem menuItem_Save;

    @FXML
    private RadioButton rb_DelMode;

    @FXML
    private Slider slider_Interval;

    @FXML
    private Slider slider_LearningRate;

    @FXML
    private Slider slider_NbModel;

    // Gets the Stage when called
    public EditorController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void initialize() {
        circleColor = circleColorPicker.getValue();
        linkColor = linkColorPicker.getValue();

        // Setting default value
        slider_NbModel.setValue(10);
        slider_LearningRate.setValue(0.3);
        label_NbModel.setText("10");
        label_Interval.setText(Integer.toString((int) slider_Interval.getValue()));
        label_LearningRate.setText("0.3");

        //Add listener for when uses make slider slide
        slider_NbModel.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_NbModel.setText(Integer.toString(newValue.intValue()));
            nbModel = newValue.intValue();
        });
        slider_Interval.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_Interval.setText(Integer.toString(newValue.intValue()));
            interval = newValue.intValue();
        });
        slider_LearningRate.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_LearningRate.setText(String.format("%.2f", newValue.doubleValue()));
            learningRate = newValue.floatValue();
        });
        
        System.out.println(circleColor.toString().substring(4));
    }

    @FXML
    void circleOnMouseClicked(MouseEvent event) {
        if (!isCircleMode) {
            isCircleMode = true;
            isLinkMode = false;
            isDelMode = false;
            selected();
        } else {
            isCircleMode = false;
            selected();
        }
    }

    @FXML
    void circleColorPickerOnAction(ActionEvent event) {
        circleColor = circleColorPicker.getValue();
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        clearPane();
    }

    @FXML
    void delModeOnAction(ActionEvent event) {
        isDelMode = rb_DelMode.isSelected();
        selected();
    }

    @FXML
    void linkOnMouseClicked(MouseEvent event) {
        if (!isLinkMode) {
            isLinkMode = true;
            isCircleMode = false;
            isDelMode = false;
            selected();
        } else {
            isLinkMode = false;
            selected();
        }
    }
    
    @FXML
    void loadOnAction(ActionEvent event) {

    }

    @FXML
    void linkColorPickerOnAction(ActionEvent event) {
        linkColor = linkColorPicker.getValue();
    }

    @FXML
    void paneOnMouseClicked(MouseEvent event) {
        if (isDelMode) {
            removeCircle(event);

            // in circle mode: adds circle to the editor pane
        } else if (isCircleMode) {
            addCircle(event);
            // in link mode: add links between circles
        } else if (isLinkMode) {
            addLink(event);
        } else {
            System.out.println("pane on mouse clicked error");
        }
    }
    
    @FXML
    void saveOnAction(ActionEvent event) {
        
    }

    // Switches to the simulation scene
    @FXML
    void startOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Simulation_layout.fxml"));
        mainAppLoader.setController(new SimulationController(primaryStage, walker, nbModel, interval, learningRate));
        walker.serialize(walker, "yes");
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

    // changes the color of the editor display
    private void selected() {
        if (isDelMode) {
            editorCircle.setFill(Color.GREY);
            link.setFill(Color.GREY);
            return;
        }

        if (isCircleMode) {
            editorCircle.setFill(Color.PURPLE);
            link.setFill(Color.DODGERBLUE);
        } else if (isLinkMode) {
            link.setFill(Color.PURPLE);
            editorCircle.setFill(Color.DODGERBLUE);
        } else {
            editorCircle.setFill(Color.DODGERBLUE);
            link.setFill(Color.DODGERBLUE);
        }
    }

    // Adds circle to the editor pane
    private void addCircle(MouseEvent event) {
        boolean isTouching = false;

        if (event.getX() > circleRadius && event.getY() > circleRadius && event.getX() < (editorPane.getWidth() - circleRadius) && event.getY() < (editorPane.getHeight() - circleRadius)) {
            Circle circle = new Circle(event.getX(), event.getY(), circleRadius, circleColor);

            if (circle_list.isEmpty()) {
                circle_list.add(circle);
                editorPane.getChildren().add(circle);
            } else {
                for (Circle editor_Circle : circle_list) {
                    if (isCircleTouching(editor_Circle, circle)) {
                        isTouching = true;
                        break;
                    }
                }

                if (!isTouching) {
                    circle_list.add(circle);
                    editorPane.getChildren().add(circle);
                }
            }
        }
    }

    private boolean isCircleTouching(Circle oldCircle, Circle newCircle) {
        boolean xTouching = false;
        int distance = circleRadius * 2 + circleSocialDistancing;
        if ((oldCircle.getCenterX() + distance) > newCircle.getCenterX() && (oldCircle.getCenterX() - distance) < newCircle.getCenterX()) {
            xTouching = true;
        }
        if ((oldCircle.getCenterY() + distance) > newCircle.getCenterY() && (oldCircle.getCenterY() - distance) < newCircle.getCenterY()) {
            if (xTouching) {
                return true;
            }
        }
        return false;
    }

    private void addLink(MouseEvent event) {
        boolean isCreated = false;

        if (circle1 == null) {
            try {
                circle1 = (Circle) event.getTarget();
                ogColor = (Color) circle1.getFill();
                circle1.setFill(Color.DODGERBLUE);
            } catch (Exception e) {
                circle1 = null;
            }

        } else {
            try {
                circle2 = (Circle) event.getTarget();
            } catch (Exception e) {
                System.out.println("clicked on other things");
                return;
            }

            nextNode = new NodeModel(circle1.getCenterX(), circle1.getCenterY(), ogColor);
            prevNode = new NodeModel(circle2.getCenterX(), circle2.getCenterY(), ogColor);

            BasicModel basicModel = new BasicModel(prevNode, nextNode, linkColor);

            for (BasicModel model : walker.getBasicModels()) {
                if (model.getPrevNode().equals(prevNode) && model.getNextNode().equals(nextNode) || model.getPrevNode().equals(nextNode) && model.getNextNode().equals(prevNode)) {
                    isCreated = true;
                    
                    if (!model.getColor().equals(linkColor)) {
                        editorPane.getChildren().remove(model.getLink());
                        editorPane.getChildren().addAll(basicModel.getLink());
                        model.setColor(linkColor);
                    }
                    break;
                }
            }

            if (!isCreated) {
                walker.addLink(basicModel);
                editorPane.getChildren().addAll(basicModel.getLink(), basicModel.getPrevNode(), basicModel.getNextNode());
                System.out.println("link has been created");
            }
            
            circle1.setFill(ogColor);
            circle1 = null;
            circle2 = null;
        }
    }

    private void clearPane() {
        editorPane.getChildren().clear();
        circle_list.clear();
        walker.getBasicModels().clear();
    }

    private void removeCircle(MouseEvent event) {
        Circle circle = (Circle) event.getTarget();
        editorPane.getChildren().remove(circle);
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
