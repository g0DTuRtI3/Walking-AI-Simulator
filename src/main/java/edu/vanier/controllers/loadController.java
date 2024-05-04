package edu.vanier.controllers;

import edu.vanier.database.SqliteDB;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class loadController {

    private final SqliteDB database = new SqliteDB();
    private final Stage primaryStage;

    @FXML
    private ListView<String> modelList;

    @FXML
    void initialize() {
        modelList.getItems().addAll(database.readModelName());

        modelList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String selectedModel = modelList.getSelectionModel().getSelectedItem();
            try {
                System.out.println("Load controller name :" + selectedModel);
                returnToEditor(selectedModel);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    public loadController(Stage stage) {
        primaryStage = stage;
    }

    public void returnToEditor(String selectedModel) throws IOException {
        FXMLLoader mainAppLoader = new FXMLLoader(getClass().getResource("/fxml/Editor_layout.fxml"));
        EditorController controller = new EditorController(primaryStage, selectedModel);
        mainAppLoader.setController(controller);
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
