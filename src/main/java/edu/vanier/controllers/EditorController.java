package edu.vanier.controllers;

import edu.vanier.database.SqliteDB;
import edu.vanier.map.*;
import edu.vanier.serialization.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditorController {

    private final static int CIRCLE_RADIUS = 20;
    private final static int CIRCLE_SOCIAL_DISTANCING = 10;
    private final Stage primaryStage;
    private final SqliteDB database = new SqliteDB();
    private int nbModel = 10;
    private int interval = 10;
    private float learningRate = 0.3f;
    private boolean isCircleMode, isLinkMode, isSelected;
    private boolean isDelMode = false;
    private ArrayList<Circle> circle_list = new ArrayList<>();
    private Circle circle1 = null, circle2 = null;
    private Color circleColor, linkColor, ogColor;
    private NodeModel prevNode, nextNode;
    private String modelName;
    private Walker walker = new Walker();
    private MyWalker seriWalker;

    // delete str when done
    byte[] b_Array;

    @FXML
    private ComboBox<String> environmentComboBox;
    
    @FXML
    private CheckBox cb_DelMode;

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
    private Slider slider_Interval;

    @FXML
    private Slider slider_LearningRate;

    @FXML
    private Slider slider_NbModel;

    @FXML
    private TextField tf_ModelName;

    // Gets the Stage when called
    public EditorController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //Gets the name of the model selected and loads the model on the 
    public EditorController(Stage primaryStage, String modelName) throws IOException {
        this.primaryStage = primaryStage;
        this.modelName = modelName;
    }

    @FXML
    void initialize() {
        this.environmentComboBox.setItems(FXCollections.observableArrayList("Earth", "Moon"));
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

        //Print all saved models
//        database.printAllModel();

        // Loads the models if it called
        if (modelName != null) {
            load(modelName);
        }
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
    void backButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
        mainAppLoader.setController(new MainAppController(primaryStage));
        Pane root = mainAppLoader.load();

        //-- 2) Create and set the scene to the stage.
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Walking AI Simulator");
        primaryStage.show();
        primaryStage.setAlwaysOnTop(false);
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
//        isDelMode = rb_DelMode.isSelected();
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

    public static String environment;

    @FXML
    void environmentSelected(ActionEvent event) {
        environment = this.environmentComboBox.getSelectionModel().getSelectedItem();
        System.out.println(environment);
    }

    @FXML
    void loadOnAction(ActionEvent event) throws IOException, ClassNotFoundException {
        loadModelSelector();
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
        } else if (isLinkMode && isLinkConnected(event)) {
            addLink(event);
        } else {
            System.out.println("pane on mouse clicked error");
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) throws IOException {
        seriWalker = saveModel();
        if (seriWalker.getBasicModels().isEmpty()) {
            System.out.println("Null walker");
            return;
        }
        b_Array = serialize(seriWalker);

        if (tf_ModelName.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter a Model Name");
            alert.show();
        } else {
            database.addModel(b_Array, tf_ModelName.getText());
        }

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

    private void loadModelSelector() throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/loadModelSelector_layout.fxml"));
        mainAppLoader.setController(new loadController(primaryStage));
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Editor");
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

            prevNode = new NodeModel(circle1.getCenterX(), circle1.getCenterY(), ogColor);
            nextNode = new NodeModel(circle2.getCenterX(), circle2.getCenterY(), ogColor);

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
        serializeWalker.setId(walker.getId());

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
        load.setId(serializedWalker.getId());

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

    public void load() {
        SqliteDB db = new SqliteDB();
        System.out.println(modelName);
        b_Array = db.readModel(modelName);

        try {
            seriWalker = (MyWalker) deSerializeObjectFromString(b_Array);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            walker = loadModel(seriWalker);
        } catch (Exception e) {
            System.out.println("Walker Null: " + e);
            System.out.println(seriWalker);
        }
    }

    public void load(String name) {
        SqliteDB db = new SqliteDB();

        if (modelName != null) {
            System.out.println(modelName);
        }

        b_Array = db.readModel(name);

        try {
            seriWalker = (MyWalker) deSerializeObjectFromString(b_Array);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
        walker = loadModel(seriWalker);
        } catch (Exception e) {
            System.out.println("Walker Null: " + e);
        System.out.println("printing serial walker: " + seriWalker);
        }
    }

    private boolean isLinkConnected(MouseEvent event) {
        try {
            if (walker.getBasicModels().isEmpty() || circle1 != null) {
                return (walker.getBasicModels().isEmpty()) || circle1 != null;
            } else {

                return (((Circle) event.getTarget()).getCenterX() == walker.getBasicModels().get(walker.getBasicModels().size() - 1).getNextNode().getCenterX()
                        && ((Circle) event.getTarget()).getCenterY() == walker.getBasicModels().get(walker.getBasicModels().size() - 1).getNextNode().getCenterY());
            }
        } catch (Exception ex) {
            return false;
        }
    }
    
    private void deletionMode() {
//        rb_DelMode.getProperties().addListener((observable) -> {
//            label_NbModel.setText(Integer.toString(newValue.intValue()));
//            nbModel = newValue.intValue();
//        });
    }
}
