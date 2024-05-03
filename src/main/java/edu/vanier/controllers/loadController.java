package edu.vanier.controllers;

import edu.vanier.database.SqliteDB;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class loadController {

    private SqliteDB database = new SqliteDB();
    private Stage primaryStage;

    @FXML
    private ListView<String> modelList;

    @FXML
    void initialize() {
        modelList.getItems().addAll(database.readModelName());

        modelList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedModel = modelList.getSelectionModel().getSelectedItem();
                try {
                    System.out.println("Load controller name :" + selectedModel);
                    returnToEditor(selectedModel);
                } catch (IOException ex) {
                    Logger.getLogger(loadController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public loadController(Stage stage) {
        primaryStage = stage;
    }

//    public void returnToEditor(String modelName) throws IOException {
//        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
//        mainAppLoader.setController(new EditorController(primaryStage, modelName));
//        Pane root = mainAppLoader.load();
//
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.setMaximized(false);
//        primaryStage.setMaximized(true);
//        // We just need to bring the main window to front.
//        primaryStage.setAlwaysOnTop(true);
//        primaryStage.setTitle("Editor");
//        primaryStage.show();
//    }
    
    public void returnToEditor(String selectedModel) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        EditorController controller = new EditorController(primaryStage);
        mainAppLoader.setController(controller);
        controller.load(selectedModel);
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
}
