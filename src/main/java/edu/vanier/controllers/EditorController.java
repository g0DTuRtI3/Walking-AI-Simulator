package edu.vanier.controllers;

import edu.vanier.map.Node;
import java.io.IOException;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorController {

    Stage primaryStage;
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
}
