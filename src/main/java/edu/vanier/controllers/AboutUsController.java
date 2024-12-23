package edu.vanier.controllers;


import java.awt.Desktop;

import java.io.IOException;
import java.net.URI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AboutUsController {

    Stage primaryStage;

    @FXML
    private Button backButton;
    @FXML
    private Hyperlink githubLink;

    private static String gitHubLink = "https://github.com/g0DTuRtI3/Walking-AI-Simulator";

    public AboutUsController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * This method brings the user back to the Main Menu
     * 
     * @param event The ActionEvent from the GUI
     * @throws IOException 
     */
    @FXML
    void backButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/MainApp_layout.fxml"));
        mainAppLoader.setController(new MainAppController(primaryStage));
        Pane root = mainAppLoader.load();

        //-- 2) Create and set the scene to the stage.
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setMaximized(true);
        // We just need to bring the main window to front.
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setTitle("Walking AI Simulator");
        primaryStage.show();

    }

    /**
     * This method opens the github of this project
     * 
     * @param event The ActionEvent from the GUI
     */
    @FXML
    void linkOnClick(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(URI.create(gitHubLink));
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Link is not functional");
                            alert.showAndWait();
        }
    }

}
