package edu.vanier.controllers;

import edu.vanier.map.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class EditorController {

    double mouseX;
    double mouseY;

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

    @FXML
    void circleOnMouseDragged(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    @FXML
    void circleOnMousePressed(MouseEvent event) {
        circle.setLayoutX(event.getSceneX() - mouseX);
        circle.setLayoutY(event.getSceneY() - mouseY);
    }

    // A method that makes the shapes draggable
    private void dragObject(Node node) {

        node.setOnMousePressed(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        node.setOnMouseDragged(e -> {
            node.setLayoutX(e.getSceneX() - mouseX);
            node.setLayoutY(e.getSceneY() - mouseY);
        });
    }
    
    @FXML
    void linkOnMouseDragged(MouseEvent event) {

    }

    @FXML
    void linkOnMousePressed(MouseEvent event) {

    }
}
