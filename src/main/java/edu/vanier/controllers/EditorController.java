package edu.vanier.controllers;

import edu.vanier.map.Node;
import java.io.IOException;
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
    ArrayList circle_list = new ArrayList<Circle>();
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
    
    // Gets the Stage when called
    public EditorController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    void initialize() {
        
    }
    
    @FXML
    void circleOnMouseClicked(MouseEvent event) {
        if (!isCircleMode){
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
    void linkOnMouseClicked(MouseEvent event) {
        if (!isLinkMode){
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
        if (isCircleMode) {
            Circle circle = new Circle(event.getX(), event.getY(), 25);
            circle_list.add(circle);
            editorPane.getChildren().add(circle);
            System.out.println("ues");
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
        primaryStage.show();
    }
    
    private void selected() {
        if (isCircleMode) {
            circle.setFill(Color.PURPLE);
            link.setFill(Color.DODGERBLUE);
        } else if (isLinkMode){
            link.setFill(Color.PURPLE);
            circle.setFill(Color.DODGERBLUE);
        } else {
            circle.setFill(Color.DODGERBLUE);
            link.setFill(Color.DODGERBLUE);
        }
        
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
