package edu.vanier.controllers;

import edu.vanier.map.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorController {

    double mouseX;
    double mouseY;
    int nbModel;
    int interval;
    int learningRate;
    boolean isCircleMode;
    boolean isLinkMode;
    
    ArrayList circle_list = new ArrayList<Circle>();
    Circle circle1 = null;
    Circle circle2 = null;
    Color circleColor;
    Color linkColor;
    Color ogColor;
    Stage primaryStage;
    
    private NodeModel prevNode;
    private NodeModel nextNode;
    private boolean isSelected;
    private Walker walker = new Walker();
    private boolean isDelMode = false;

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
        slider_NbModel.setValue(10);
        slider_LearningRate.setValue(0.3);
        label_NbModel.setText("10");
        label_Interval.setText(Integer.toString((int)slider_Interval.getValue()));
        label_LearningRate.setText("0.3");
        
        slider_NbModel.valueProperty().addListener((observable, oldValue, newValue) -> {
        label_NbModel.setText(Integer.toString(newValue.intValue()));
        });
        slider_Interval.valueProperty().addListener((observable, oldValue, newValue) -> {
        label_Interval.setText(Integer.toString(newValue.intValue()));
        });
        slider_LearningRate.valueProperty().addListener((observable, oldValue, newValue) -> {
        label_LearningRate.setText(String.format("%.2f", newValue.doubleValue()));
        });
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
    void linkOnMouseDragged(MouseEvent event) {

    }

    @FXML
    void linkOnMousePressed(MouseEvent event) {

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

    // Switches to the simulation scene
    @FXML
    void startOnAction(ActionEvent event) throws IOException {
        //////////////////// Error handeling for this
        int nb = 0;
        
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Simulation_layout.fxml"));
        mainAppLoader.setController(new SimulationController(primaryStage, walker, nb));
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
        Circle circle = new Circle(event.getX(), event.getY(), 25, circleColor);
        circle_list.add(circle);
        editorPane.getChildren().add(circle);
    }

    private void addLink(MouseEvent event) {

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
            walker.addLink(basicModel);
            editorPane.getChildren().addAll(basicModel.getLink(), basicModel.getPrevNode(), basicModel.getNextNode());
            circle1 = null;
            circle2 = null;
        }
    }

    private void clearPane() {
        editorPane.getChildren().clear();
        walker.getModel().clear();
    }

    private void removeCircle(MouseEvent event) {
        Circle circle = (Circle)event.getTarget();
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
