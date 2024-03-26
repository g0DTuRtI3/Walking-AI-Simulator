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
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorController {

    Color ogColor;
    Stage primaryStage;
    boolean isCircleMode;
    boolean isLinkMode;
    Circle circle1 = null;
    Circle circle2 = null;
    ArrayList circle_list = new ArrayList<Circle>();
    double mouseX;
    double mouseY;
    Color circleColor;
    Color linkColor;
    private NodeModel prevNode;
    private NodeModel nextNode;
    private boolean isSelected;
    private Walker walker = new Walker();
    private boolean delMode = false;

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_Start;

    @FXML
    private Circle editorCircle;

    @FXML
    private ColorPicker circleColorPicker;

    @FXML
    private Pane editorPane;

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
        circleColor = circleColorPicker.getValue();
        linkColor = linkColorPicker.getValue();

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
    void circleColorPickerOnAction(ActionEvent event) {
        circleColor = circleColorPicker.getValue();
    }
    
    @FXML
    void clearOnAction(ActionEvent event) {
        clearPane();
    }
    
    @FXML
    void delModeOnAction(ActionEvent event) {
        delMode = rb_DelMode.isSelected();
    }

    @FXML
    void linkOnMouseClicked(MouseEvent event) {
        if (delMode) {
            selected();
        } else if (!isLinkMode) {
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
    void linkColorPickerOnAction(ActionEvent event) {
        linkColor = linkColorPicker.getValue();
    }

    @FXML
    void paneOnMouseClicked(MouseEvent event) {
        if (delMode) {
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

    // changes the color of the editor display
    private void selected() {
        if (delMode) {
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
                return;
            } catch (Exception e) {
                circle1 = null;
                return;
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
        System.out.println(walker.getModel());
    }

    private void removeCircle(MouseEvent event) {
        System.out.println("felete circle");
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
    
    public Walker getWalker() {
        return walker;
    }

    public TextField getTf_interval() {
        return tf_interval;
    }

    public TextField getTf_learningRate() {
        return tf_learningRate;
    }

    public TextField getTf_nbModel() {
        return tf_nbModel;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
    }

    public void setTf_interval(TextField tf_interval) {
        this.tf_interval = tf_interval;
    }

    public void setTf_learningRate(TextField tf_learningRate) {
        this.tf_learningRate = tf_learningRate;
    }

    public void setTf_nbModel(TextField tf_nbModel) {
        this.tf_nbModel = tf_nbModel;
    }
}
