package edu.vanier.controllers;

import edu.vanier.database.SqliteDB;
import edu.vanier.map.*;
import edu.vanier.serialization.MyBasicModel;
import edu.vanier.serialization.MyLine;
import edu.vanier.serialization.MyNodeModel;
import edu.vanier.serialization.MyWalker;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private final static int CIRCLE_RADIUS = 20;
    private final static int CIRCLE_SOCIAL_DISTANCING = 10;
    private int nbModel = 10;
    private int interval = 10;
    private float learningRate = 0.3f;
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
    private MyWalker seriWalker;
    private SqliteDB database = new SqliteDB();

    // delete str when done
    byte[] b_Array;

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
    void loadOnAction(ActionEvent event) throws IOException, ClassNotFoundException {
        seriWalker = (MyWalker) deSerializeObjectFromString(b_Array);
        walker = loadModel(seriWalker);
        System.out.println(walker.getBasicModels().get(0).getNextNode().getCenterX());
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
    void saveOnAction(ActionEvent event) throws IOException {
        seriWalker = saveModel();
        b_Array = serialize(seriWalker);
        database.addModel(b_Array, "JoeMAMA");
        
        System.out.println(walker.getBasicModels().get(0).getNextNode().getCenterX());
    }

    // Switches to the simulation scene
    @FXML
    void startOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Simulation_layout.fxml"));
//        walker.serialize(walker, "yes");
        double xtranslate = walker.getBasicModels().get(0).getPrevNode().getCenterX();
        double ytranslate = walker.getBasicModels().get(0).getPrevNode().getCenterY();
        mainAppLoader.setController(new SimulationController(primaryStage, walker, nbModel, interval, learningRate, xtranslate, ytranslate));
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

        if (event.getX() > CIRCLE_RADIUS && event.getY() > CIRCLE_RADIUS && event.getX() < (editorPane.getWidth() - CIRCLE_RADIUS) && event.getY() < (editorPane.getHeight() - CIRCLE_RADIUS)) {
            Circle circle = new Circle(event.getX(), event.getY(), CIRCLE_RADIUS, circleColor);

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
        int distance = CIRCLE_RADIUS * 2 + CIRCLE_SOCIAL_DISTANCING;
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
                walker.addBasicModel(basicModel);
                editorPane.getChildren().addAll(basicModel.getLink(), basicModel.getPrevNode(), basicModel.getNextNode());
                System.out.println("link has been created");
            } else {
                editorPane.getChildren().addAll(basicModel.getLink(), basicModel.getPrevNode(), basicModel.getNextNode());
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

    private MyWalker saveModel() {
        MyWalker serializeWalker = new MyWalker();
        serializeWalker.setBrain(walker.getBrain());
        serializeWalker.setFitnessScore(walker.getFitnessScore());

        ArrayList<MyBasicModel> serializeBasicModels = new ArrayList<>();
        for (BasicModel basicModel : walker.getBasicModels()) {
            MyNodeModel serializePrevNode = new MyNodeModel(basicModel.getPrevNode().getCenterX(), basicModel.getPrevNode().getCenterY(), basicModel.getPrevNode().getFill().toString().substring(2, 8));
            MyNodeModel serializeNextNode = new MyNodeModel(basicModel.getNextNode().getCenterX(), basicModel.getNextNode().getCenterY(), basicModel.getNextNode().getFill().toString().substring(2, 8));
            MyLine serializeLine = new MyLine(basicModel.getLink().getStrokeWidth(), basicModel.getLink().getStartX(), basicModel.getLink().getStartY(), basicModel.getLink().getEndX(), basicModel.getLink().getEndY());
            MyBasicModel serializeModel = new MyBasicModel(serializePrevNode, serializeNextNode, serializeLine, basicModel.getColor().toString().substring(2, 8));

            serializeBasicModels.add(serializeModel);
        }

        serializeWalker.setBasicModels(serializeBasicModels);

        return serializeWalker;
    }

    private Walker loadModel(MyWalker serializedWalker) {
        clearPane();
        Walker load = new Walker();

        load.setBrain(serializedWalker.getBrain());
        load.setFitnessScore(serializedWalker.getFitnessScore());
        ArrayList<BasicModel> basicModels = new ArrayList<>();

        for (MyBasicModel myBasicModel : serializedWalker.getBasicModels()) {
            NodeModel prevNode = new NodeModel(myBasicModel.getPrevNode().getCenterX(), myBasicModel.getPrevNode().getCenterY(), Color.web(myBasicModel.getPrevNode().getHexColor()));
            NodeModel nextNode = new NodeModel(myBasicModel.getNextNode().getCenterX(), myBasicModel.getNextNode().getCenterY(), Color.web(myBasicModel.getNextNode().getHexColor()));
            Color color = Color.web(myBasicModel.getColor());
            BasicModel loadModel = new BasicModel(prevNode, nextNode, color);

            Circle prevCircle = new Circle(myBasicModel.getPrevNode().getCenterX(), myBasicModel.getPrevNode().getCenterY(), CIRCLE_RADIUS, Color.web(myBasicModel.getPrevNode().getHexColor()));
            Circle nextCircle = new Circle(myBasicModel.getNextNode().getCenterX(), myBasicModel.getNextNode().getCenterY(), CIRCLE_RADIUS, Color.web(myBasicModel.getNextNode().getHexColor()));
            editorPane.getChildren().addAll(loadModel.getLink(), prevCircle, nextCircle);

            load.getBasicModels().add(loadModel);
        }

        return load;
    }
    
    public byte[] serialize(MyWalker serializableWalker) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(serializableWalker);
        oos.close();
        return baos.toByteArray();
    }
    
    public static Object deSerializeObjectFromString(byte[] b_Array) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(b_Array);
        ObjectInputStream ois = new ObjectInputStream(b);
        Object o = ois.readObject();
        ois.close();
        return o;
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
