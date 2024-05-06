package edu.vanier.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainAppController {

    private final String cssEditorPath = "editorStyleSheet.css";

    
    private static MediaPlayer player;

    
    static MediaPlayer defaultPlayer;
    static MediaPlayer naturePlayer;
    static MediaPlayer spacePlayer;


    
    private Stage primaryStage;

    @FXML
    private Button btn_AboutUs;

    @FXML
    private BorderPane root;

    @FXML
    private Button btn_Setting;

    @FXML
    private Button btn_Simulation;


    /**
     * Called from Main app.
     * 
     * @param primaryStage 
     */

    @FXML
    private ImageView title;

    private ArrayList<Circle> circles = new ArrayList<>();

    private ArrayList<Line> links = new ArrayList<>();

    private AnimationTimer circleAnimation = new AnimationTimer() {
        @Override
        public void handle(long now) {
            for (Circle c : circles) {
                c.setCenterX(c.getCenterX() + (-1 + 2 * Math.random()));
                c.setCenterY(c.getCenterY() + (-1 + 2 * Math.random()));
            }
            for (int j = 1; j <= links.size() - 1; j++) {
                links.get(j).setStartX(circles.get(j - 1).getCenterX());
                links.get(j).setStartY(circles.get(j - 1).getCenterY());
                links.get(j).setEndX(circles.get(j).getCenterX());
                links.get(j).setEndY(circles.get(j).getCenterY());
            }
        }
    };


    public MainAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }

    @FXML
    void initialize() {

//        if (player == null) {
//            Media media = new Media(getClass().getResource("/music/defaultMusic.mp3").toExternalForm());
//            player = new MediaPlayer(media);
//            player.setAutoPlay(true);
//            player.setCycleCount(MediaPlayer.INDEFINITE);
//        }

        for (int i = 0; i < 5; i++) {

            int randomNum = (int) (Math.random() * 7) + 2;
            int currentNumOfCircles = circles.size();

            System.out.println();
            for (int j = 0; j < randomNum; j++) {
                Circle c = new Circle(Math.random() * root.getPrefWidth() * 2, Math.random() * root.getPrefHeight() * 2, 25);
                c.setFill(Color.RED);
                c.setOpacity(0.8);
                circles.add(c);
            }

            for (int j = currentNumOfCircles; j < currentNumOfCircles + randomNum - 1; j++) {
                Line l = new Line(circles.get(j).getCenterX(), circles.get(j).getCenterY(), circles.get(j + 1).getCenterX(), circles.get(j + 1).getCenterY());
                l.setStrokeWidth(10);
                l.setOpacity(0.8);
                l.setStroke(Color.DARKBLUE);

                links.add(l);
            }

        }
        ScaleTransition titleScaleTransition = new ScaleTransition(Duration.millis(900), title);
        titleScaleTransition.setByX(0.2);
        titleScaleTransition.setByY(0.2);
        titleScaleTransition.setFromX(1);
        titleScaleTransition.setFromY(1);
        titleScaleTransition.setCycleCount((int) (Duration.INDEFINITE).toSeconds());
        titleScaleTransition.setAutoReverse(true);

        RotateTransition titleRotateTransition = new RotateTransition(Duration.millis(900 / 2), title);
        titleRotateTransition.setByAngle(0);
        titleRotateTransition.setToAngle(10);
        titleRotateTransition.setCycleCount((int) (Duration.INDEFINITE).toSeconds());
        titleRotateTransition.setAutoReverse(true);
        PathTransition titlePathTransition = new PathTransition(Duration.millis(900), new Line(title.getX() + 350, title.getY() + 250, title.getX() + 420, title.getY() + 300), title);
        titlePathTransition.setCycleCount((int) (Duration.INDEFINITE).toSeconds());
        titlePathTransition.setAutoReverse(true);

        
        

        ParallelTransition titleTransition = new ParallelTransition(title, titleRotateTransition, titleScaleTransition, titlePathTransition);
        titleTransition.setAutoReverse(true);
        titleTransition.play();

        root.getChildren().addAll(0, circles);
        System.out.println(circles.size());
        System.out.println(links.size());
        root.getChildren().addAll(0, links);
        circleAnimation.start();

        if (defaultPlayer == null) {

            Media defaultMedia = new Media(getClass().getResource("/music/defaultMusic.mp3").toExternalForm());
            Media natureMedia = new Media(getClass().getResource("/music/natureMusic.mp3").toExternalForm());
            Media spaceMedia = new Media(getClass().getResource("/music/spaceMusic.mp3").toExternalForm());
            defaultPlayer = new MediaPlayer(defaultMedia);
            naturePlayer = new MediaPlayer(natureMedia);
            spacePlayer = new MediaPlayer(spaceMedia);
            naturePlayer.setCycleCount(MediaPlayer.INDEFINITE);
            spacePlayer.setCycleCount(MediaPlayer.INDEFINITE);
            defaultPlayer.setAutoPlay(true);
            defaultPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }


    }

    @FXML
    void aboutUsOnAction(ActionEvent event) throws IOException {
        FXMLLoader aboutUsLoader = new FXMLLoader(getClass().getResource("/fxml/AboutUs_Layout.fxml"));
        aboutUsLoader.setController(new AboutUsController(primaryStage));
        Pane root = aboutUsLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("AboutUs");
        primaryStage.show();
    }

    @FXML
    void settingOnAction(ActionEvent event) throws IOException {
        FXMLLoader SettingsLoader = new FXMLLoader(getClass().getResource("/fxml/Settings_layout.fxml"));
        SettingsLoader.setController(new SettingsController(primaryStage));
        Pane root = SettingsLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }

    // Loads the Editor scene
    @FXML
    void startButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        mainAppLoader.setController(new EditorController(primaryStage));
        Pane root = mainAppLoader.load();

        Scene scene = new Scene(root);
        System.out.println(getClass());

        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Model Editor");
        primaryStage.show();
    }

    static public MediaPlayer getDefaultPlayer() {
        return defaultPlayer;
    }
}
