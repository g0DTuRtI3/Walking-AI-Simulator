package edu.vanier.ui;

import edu.vanier.controllers.EditorController;
import edu.vanier.controllers.MainAppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * The JavaFX GUI framework (version: 20.0.2) is linked to this project in the
 * build.gradle file.
 * @link: https://openjfx.io/javadoc/20/
 * @see: /Build Scripts/build.gradle
 * @author Sleiman Rabah.
 */
public class MainApp extends Application {

    private final static Logger logger = LoggerFactory.getLogger(MainApp.class);
    

    @Override
    public void start(Stage primaryStage) {

        try {
            logger.info("Bootstrapping the application...");
            //-- 1) Load the scene graph from the specified FXML file and 
            // associate it with its FXML controller.
            FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
            mainAppLoader.setController(new MainAppController(primaryStage));
            Pane root = mainAppLoader.load();

            //-- 2) Create and set the scene to the stage.
            Scene scene = new Scene(root);
            System.out.println(scene.getStylesheets());
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            // We just need to bring the main window to front.
            primaryStage.setAlwaysOnTop(true);
            primaryStage.setTitle("Walking AI Simulator");
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

//        //For the Final build

//        try {
//            logger.info("Bootstrapping the application...");
//            //-- 1) Load the scene graph from the specified FXML file and 
//            // associate it with its FXML controller.
//            FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
//            mainAppLoader.setController(new MainAppController(primaryStage));
//            Pane root = mainAppLoader.load();
//
//            //-- 2) Create and set the scene to the stage.
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add("-fx-background-image: url(\"src/main/resources/images/menuBackground.jpg\");");
//            scene.getStylesheets().add(cssEditorPath);
//            System.out.println(scene.getStylesheets());
//            primaryStage.setScene(scene);
//            primaryStage.setMaximized(true);
//            primaryStage.setResizable(true);
//            // We just need to bring the main window to front.
//            primaryStage.setAlwaysOnTop(true);
//            primaryStage.setTitle("Walking AI Simulator");
//            primaryStage.show();
//            primaryStage.setAlwaysOnTop(false);
//        } catch (IOException ex) {
//            logger.error(ex.getMessage(), ex);
//        }


        try {
            logger.info("Bootstrapping the application...");
            //-- 1) Load the scene graph from the specified FXML file and 
            // associate it with its FXML controller.
            FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
            mainAppLoader.setController(new EditorController(primaryStage));
            Pane root = mainAppLoader.load();

            //-- 2) Create and set the scene to the stage.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            // We just need to bring the main window to front.
            primaryStage.setAlwaysOnTop(true);
            primaryStage.setTitle("Walking AI Simulator");
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
